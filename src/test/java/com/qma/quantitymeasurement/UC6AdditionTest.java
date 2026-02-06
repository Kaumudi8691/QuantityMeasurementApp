package com.qma.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class UC6AdditionTest {

    private static final double EPSILON = 0.0001;

    @Test
    void shouldAddSameUnitFeetPlusFeet() {
        Length a = new Length(1.0, LengthUnit.FEET);
        Length b = new Length(2.0, LengthUnit.FEET);

        Length result = a.add(b);

        assertEquals(new Length(3.0, LengthUnit.FEET), result);
    }

    @Test
    void shouldAddCrossUnitFeetPlusInchReturnFeet() {
        Length a = new Length(1.0, LengthUnit.FEET);
        Length b = new Length(12.0, LengthUnit.INCH);

        Length result = a.add(b);

        assertEquals(new Length(2.0, LengthUnit.FEET), result);
    }

    @Test
    void shouldAddCrossUnitInchPlusFeetReturnInch() {
        Length a = new Length(12.0, LengthUnit.INCH);
        Length b = new Length(1.0, LengthUnit.FEET);

        Length result = a.add(b);

        assertEquals(new Length(24.0, LengthUnit.INCH), result);
    }

    @Test
    void shouldAddYardPlusFeetReturnYard() {
        Length a = new Length(1.0, LengthUnit.YARD);
        Length b = new Length(3.0, LengthUnit.FEET);

        Length result = a.add(b);

        assertEquals(new Length(2.0, LengthUnit.YARD), result);
    }

    @Test
    void shouldReturnSameValueWhenAddingZero() {
        Length a = new Length(5.0, LengthUnit.FEET);
        Length b = new Length(0.0, LengthUnit.INCH);

        Length result = a.add(b);

        assertEquals(new Length(5.0, LengthUnit.FEET), result);
    }

    @Test
    void shouldThrowExceptionWhenOtherIsNull() {
        Length a = new Length(1.0, LengthUnit.FEET);
        assertThrows(IllegalArgumentException.class, () -> a.add(null));
    }

    @Test
    void shouldSupportDecimalAdditionUsingConvertTo() {
        Length a = new Length(2.54, LengthUnit.CENTIMETER);
        Length b = new Length(1.0, LengthUnit.INCH);

        Length result = a.add(b);

        // compare using convertTo due to decimal precision
        assertEquals(5.08, result.convertTo(LengthUnit.CENTIMETER), EPSILON);
    }
}