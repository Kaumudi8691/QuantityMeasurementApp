package com.qma.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

public class UC10QuantityTest {

    private static final double EPS = 1e-6;

    @Test
    void length_equality() {
        Quantity<LengthUnit> a = new Quantity<>(12.0, LengthUnit.INCH);
        Quantity<LengthUnit> b = new Quantity<>(1.0, LengthUnit.FEET);
        assertEquals(a, b);
    }

    @Test
    void weight_conversion() {
        double kg = Quantity.convert(2.20462262, WeightUnit.POUND, WeightUnit.KILOGRAM);
        assertEquals(1.0, kg, EPS);
    }

    @Test
    void cross_category_not_equal() {
        Quantity<LengthUnit> L = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<WeightUnit> W = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        assertNotEquals(L, W);
    }
}