package com.qma.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UC5ConversionTest {

    @Test
    void testConversion_FeetToInches() {
        Length l = new Length(1.0, LengthUnit.FEET);
        assertEquals(12.0, l.convert(LengthUnit.INCH));
    }

    @Test
    void testConversion_InchesToFeet() {
        Length l = new Length(24.0, LengthUnit.INCH);
        assertEquals(2.0, l.convert(LengthUnit.FEET));
    }

    @Test
    void testConversion_YardsToInches() {
        Length l = new Length(1.0, LengthUnit.YARD);
        assertEquals(36.0, l.convert(LengthUnit.INCH));
    }

    @Test
    void testConversion_InchesToYards() {
        Length l = new Length(72.0, LengthUnit.INCH);
        assertEquals(2.0, l.convert(LengthUnit.YARD));
    }

    @Test
    void testConversion_CentimetersToInches() {
        Length l = new Length(2.54, LengthUnit.CENTIMETER);
        assertEquals(1.0, l.convert(LengthUnit.INCH), 0.0001);
    }

    @Test
    void testConversion_FeatToYard() {
        Length l = new Length(6.0, LengthUnit.FEET);
        assertEquals(2.0, l.convert(LengthUnit.YARD));
    }

    @Test
    void testConversion_RoundTrip() {
        Length l = new Length(5.0, LengthUnit.FEET);
        double inches = l.convert(LengthUnit.INCH);
        double backToFeet = new Length(inches, LengthUnit.INCH).convert(LengthUnit.FEET);
        assertEquals(5.0, backToFeet, 0.0001);
    }

    @Test
    void testConversion_ZeroValue() {
        Length l = new Length(0.0, LengthUnit.FEET);
        assertEquals(0.0, l.convert(LengthUnit.INCH));
    }

    @Test
    void testConversion_NegativeValue() {
        Length l = new Length(-1.0, LengthUnit.FEET);
        assertEquals(-12.0, l.convert(LengthUnit.INCH));
    }

    @Test
    void testConversion_InvalidUnit_Throws() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Length(1.0, null);
        });
    }

    @Test
    void testConversion_InvalidValue_Throws() {
        assertThrows(IllegalArgumentException.class, () -> {
            Length l = new Length(Double.NaN, LengthUnit.FEET);
            l.convert(LengthUnit.INCH);
        });
    }
}
