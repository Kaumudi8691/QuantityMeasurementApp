package com.qma.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class UC7AdditionWithTargetUnitTest {

    private static final double EPS = 0.001;

    @Test
    void testAddition_ExplicitTargetUnit_Feet() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCH);

        Length result = l1.add(l2, LengthUnit.FEET);

        assertEquals(2.0, result.convertTo(LengthUnit.FEET), EPS);
        assertEquals(2.0, result.convertTo(resultUnit(result)), EPS); // value in its own unit
    }

    @Test
    void testAddition_ExplicitTargetUnit_Inch() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCH);

        Length result = l1.add(l2, LengthUnit.INCH);

        assertEquals(24.0, result.convertTo(LengthUnit.INCH), EPS);
    }

    @Test
    void testAddition_ExplicitTargetUnit_Yard() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCH);

        Length result = l1.add(l2, LengthUnit.YARD);

        // 2 feet = 0.6667 yard
        assertEquals(0.6667, result.convertTo(LengthUnit.YARD), EPS);
    }

    @Test
    void testAddition_ExplicitTargetUnit_Centimeter() {
        Length l1 = new Length(1.0, LengthUnit.INCH);
        Length l2 = new Length(1.0, LengthUnit.INCH);

        Length result = l1.add(l2, LengthUnit.CENTIMETER);

        // 2 inch = 5.08 cm
        assertEquals(5.08, result.convertTo(LengthUnit.CENTIMETER), EPS);
    }

    @Test
    void testAddition_ExplicitTargetUnit_SameAsFirstOperand() {
        Length l1 = new Length(2.0, LengthUnit.YARD);
        Length l2 = new Length(3.0, LengthUnit.FEET);

        Length result = l1.add(l2, LengthUnit.YARD);

        // 2 yard (=6 ft) + 3 ft (=1 yard) => 3 yard
        assertEquals(3.0, result.convertTo(LengthUnit.YARD), EPS);
    }

    @Test
    void testAddition_ExplicitTargetUnit_SameAsSecondOperand() {
        Length l1 = new Length(2.0, LengthUnit.YARD);
        Length l2 = new Length(3.0, LengthUnit.FEET);

        Length result = l1.add(l2, LengthUnit.FEET);

        // 2 yard (=6 ft) + 3 ft => 9 ft
        assertEquals(9.0, result.convertTo(LengthUnit.FEET), EPS);
    }

    @Test
    void testAddition_ExplicitTargetUnit_Commutativity() {
        Length a = new Length(1.0, LengthUnit.FEET);
        Length b = new Length(12.0, LengthUnit.INCH);

        Length r1 = a.add(b, LengthUnit.YARD);
        Length r2 = b.add(a, LengthUnit.YARD);

        assertEquals(r1.convertTo(LengthUnit.YARD), r2.convertTo(LengthUnit.YARD), EPS);
    }

    @Test
    void testAddition_ExplicitTargetUnit_WithZero() {
        Length l1 = new Length(5.0, LengthUnit.FEET);
        Length l2 = new Length(0.0, LengthUnit.INCH);

        Length result = l1.add(l2, LengthUnit.YARD);

        // 5 ft = 1.6667 yard
        assertEquals(1.6667, result.convertTo(LengthUnit.YARD), EPS);
    }

    @Test
    void testAddition_ExplicitTargetUnit_NegativeValues() {
        Length l1 = new Length(5.0, LengthUnit.FEET);
        Length l2 = new Length(-2.0, LengthUnit.FEET);

        Length result = l1.add(l2, LengthUnit.INCH);

        // (5 - 2) ft = 3 ft = 36 inch
        assertEquals(36.0, result.convertTo(LengthUnit.INCH), EPS);
    }

    @Test
    void testAddition_ExplicitTargetUnit_NullTargetUnit() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCH);

        assertThrows(IllegalArgumentException.class, () -> l1.add(l2, null));
    }

    @Test
    void testAddition_ExplicitTargetUnit_LargeToSmallScale() {
        Length l1 = new Length(1000.0, LengthUnit.FEET);
        Length l2 = new Length(500.0, LengthUnit.FEET);

        Length result = l1.add(l2, LengthUnit.INCH);

        // 1500 ft = 18000 inch
        assertEquals(18000.0, result.convertTo(LengthUnit.INCH), EPS);
    }

    @Test
    void testAddition_ExplicitTargetUnit_SmallToLargeScale() {
        Length l1 = new Length(12.0, LengthUnit.INCH);
        Length l2 = new Length(12.0, LengthUnit.INCH);

        Length result = l1.add(l2, LengthUnit.YARD);

        // 24 inch = 2 ft = 0.6667 yard
        assertEquals(0.6667, result.convertTo(LengthUnit.YARD), EPS);
    }

    @Test
    void testAddition_ExplicitTargetUnit_AllUnitCombinations() {
        LengthUnit[] units = LengthUnit.values();

        for (LengthUnit u1 : units) {
            for (LengthUnit u2 : units) {
                for (LengthUnit target : units) {

                    Length a = new Length(2.0, u1);
                    Length b = new Length(3.0, u2);

                    // expected = (a in feet + b in feet) converted to target
                    double expectedFeet = u1.toFeet(2.0) + u2.toFeet(3.0);
                    double expectedTarget = Length.convert(expectedFeet, LengthUnit.FEET, target);

                    Length result = a.add(b, target);

                    // Compare in target unit
                    assertEquals(expectedTarget, result.convertTo(target), EPS,
                            "Failed for: " + u1 + " + " + u2 + " -> " + target);
                }
            }
        }
    }

    /**
     * Helper: since Length has no getter for unit/value in your current class,
     * we verify results by converting to target units using convertTo().
     *
     * If later you add getters, you can remove this helper.
     */
    private LengthUnit resultUnit(Length length) {
        // Not accessible in your class; kept for readability in first test.
        // We rely on convertTo(targetUnit) for assertions, so this isn't needed.
        return LengthUnit.FEET;
    }
}