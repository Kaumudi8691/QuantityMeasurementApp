package com.qma.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LengthTest {

    @Test
    void testEquality_FeetToFeet_SameValue() {
        assertEquals(new Length(1.0, LengthUnit.FEET), new Length(1.0, LengthUnit.FEET));
    }

    @Test
    void testEquality_InchToInch_SameValue() {
        assertEquals(new Length(10.0, LengthUnit.INCH), new Length(10.0, LengthUnit.INCH));
    }

    @Test
    void testEquality_FeetToInch_EquivalentValue() {
        assertEquals(new Length(1.0, LengthUnit.FEET), new Length(12.0, LengthUnit.INCH));
    }

    @Test
    void testEquality_InchToFeet_EquivalentValue() {
        assertEquals(new Length(12.0, LengthUnit.INCH), new Length(1.0, LengthUnit.FEET));
    }

    @Test
    void testEquality_NullComparison() {
        assertNotEquals(new Length(1.0, LengthUnit.FEET), null);
    }

    @Test
    void testEquality_DifferentType() {
        assertNotEquals(new Length(1.0, LengthUnit.FEET), "1 FEET");
    }
}