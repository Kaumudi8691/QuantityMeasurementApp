
package com.qma.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class UC9WeightTest {

    private static final double EPS = 1e-6;

    // ---------- Equality ----------

    @Test
    void equality_sameUnit_sameValue_true() {
        assertEquals(new Weight(1.0, WeightUnit.KILOGRAM),
                     new Weight(1.0, WeightUnit.KILOGRAM));
    }

    @Test
    void equality_sameUnit_differentValue_false() {
        assertNotEquals(new Weight(1.0, WeightUnit.KILOGRAM),
                        new Weight(2.0, WeightUnit.KILOGRAM));
    }

    @Test
    void equality_kg_to_gram_equivalent_true() {
        assertEquals(new Weight(1.0, WeightUnit.KILOGRAM),
                     new Weight(1000.0, WeightUnit.GRAM));
    }

    @Test
    void equality_kg_to_pound_equivalent_trueWithinEpsilon() {
        // 1 kg = 2.20462262185 lb (using 0.45359237 kg/lb)
        assertEquals(new Weight(1.0, WeightUnit.KILOGRAM),
                     new Weight(2.20462262, WeightUnit.POUND));
    }

    @Test
    void equality_gram_to_pound_equivalent_trueWithinEpsilon() {
        // 453.59237 g = 1 lb
        assertEquals(new Weight(453.59237, WeightUnit.GRAM),
                     new Weight(1.0, WeightUnit.POUND));
    }

    @Test
    void equality_nullComparison_false() {
        assertFalse(new Weight(1.0, WeightUnit.KILOGRAM).equals(null));
    }

    // ---------- Conversion ----------

    @Test
    void conversion_kg_to_gram() {
        assertEquals(1000.0, Weight.convert(1.0, WeightUnit.KILOGRAM, WeightUnit.GRAM), EPS);
    }

    @Test
    void conversion_gram_to_lb() {
        // 500 g -> 0.5 kg -> 1.10231 lb
        double lb = Weight.convert(500.0, WeightUnit.GRAM, WeightUnit.POUND);
        assertEquals(1.10231, lb, 1e-5);
    }

    @Test
    void conversion_lb_to_kg() {
        // 2.20462262 lb -> ~1 kg
        double kg = Weight.convert(2.20462262, WeightUnit.POUND, WeightUnit.KILOGRAM);
        assertEquals(1.0, kg, 1e-6);
    }

    @Test
    void conversion_sameUnit_returnsSameValue() {
        assertEquals(5.0, Weight.convert(5.0, WeightUnit.KILOGRAM, WeightUnit.KILOGRAM), EPS);
    }

    @Test
    void conversion_zero() {
        assertEquals(0.0, Weight.convert(0.0, WeightUnit.KILOGRAM, WeightUnit.GRAM), EPS);
    }

    // ---------- Addition (UC6/UC7 pattern) ----------

    @Test
    void add_implicitResultInThisUnit() {
        Weight a = new Weight(1.0, WeightUnit.KILOGRAM);
        Weight b = new Weight(1000.0, WeightUnit.GRAM);
        Weight sum = a.add(b); // result in KILOGRAM
        assertEquals(2.0, sum.convertTo(WeightUnit.KILOGRAM), EPS);
    }

    @Test
    void add_implicit_resultInFirstOperandUnit_grams() {
        Weight a = new Weight(500.0, WeightUnit.GRAM);
        Weight b = new Weight(0.5, WeightUnit.KILOGRAM);
        Weight sum = a.add(b); // result in GRAM
        assertEquals(1000.0, sum.convertTo(WeightUnit.GRAM), EPS);
    }

    @Test
    void add_explicitTarget_pounds() {
        Weight a = new Weight(2.0, WeightUnit.KILOGRAM);    // 2 kg
        Weight b = new Weight(4.0, WeightUnit.POUND);       // ~1.81436948 kg
        Weight sum = a.add(b, WeightUnit.POUND);
        // Expected in lb: (2 + 4*0.45359237) kg -> divide by 0.45359237
        double expectedLb = WeightUnit.KILOGRAM.convert(2.0 + 4.0*0.45359237, WeightUnit.POUND);
        assertEquals(expectedLb, sum.convertTo(WeightUnit.POUND), 1e-6);
    }

    @Test
    void add_commutativity_withTarget() {
        Weight x = new Weight(1.0, WeightUnit.KILOGRAM);
        Weight y = new Weight(500.0, WeightUnit.GRAM);

        Weight r1 = x.add(y, WeightUnit.GRAM);
        Weight r2 = y.add(x, WeightUnit.GRAM);

        assertEquals(r1.convertTo(WeightUnit.GRAM), r2.convertTo(WeightUnit.GRAM), EPS);
    }

    // ---------- Edge cases & guards ----------

    @Test
    void negative_values_supported() {
        assertEquals(new Weight(-1.0, WeightUnit.KILOGRAM),
                     new Weight(-1000.0, WeightUnit.GRAM));
    }

    @Test
    void constructor_nullUnit_throws() {
        assertThrows(IllegalArgumentException.class, () -> new Weight(1.0, null));
    }

    @Test
    void add_nullOperand_throws() {
        Weight a = new Weight(1.0, WeightUnit.KILOGRAM);
        assertThrows(IllegalArgumentException.class, () -> a.add(null));
    }

    @Test
    void add_nullTarget_throws() {
        Weight a = new Weight(1.0, WeightUnit.KILOGRAM);
        Weight b = new Weight(1.0, WeightUnit.GRAM);
        assertThrows(IllegalArgumentException.class, () -> a.add(b, null));
    }

    @Test
    void roundTrip_conversion_allPairs() {
        for (WeightUnit u1 : WeightUnit.values()) {
            for (WeightUnit u2 : WeightUnit.values()) {
                double v = 3.14159; // arbitrary
                double toU2 = Weight.convert(v, u1, u2);
                double back = Weight.convert(toU2, u2, u1);
                assertEquals(v, back, 1e-6, "Round-trip failed: " + u1 + "->" + u2);
            }
        }
    }
}