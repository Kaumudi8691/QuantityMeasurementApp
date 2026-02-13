package com.qma.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class UC8RefactorTest {

    private static final double EPS = 1e-6;

    // --- Enum owns conversion (to/from base) ---

    @Test
    void testToBaseUnit_FeetToFeet() {
        assertEquals(5.0, LengthUnit.FEET.toBaseUnit(5.0), EPS);
    }

    @Test
    void testToBaseUnit_InchesToFeet() {
        assertEquals(1.0, LengthUnit.INCH.toBaseUnit(12.0), EPS);
    }

    @Test
    void testToBaseUnit_YardsToFeet() {
        assertEquals(3.0, LengthUnit.YARD.toBaseUnit(1.0), EPS);
    }

    @Test
    void testToBaseUnit_CentimetersToFeet() {
        assertEquals(1.0, LengthUnit.CENTIMETER.toBaseUnit(30.48), 1e-9);
    }

    @Test
    void testFromBaseUnit_FeetToFeet() {
        assertEquals(2.0, LengthUnit.FEET.fromBaseUnit(2.0), EPS);
    }

    @Test
    void testFromBaseUnit_FeetToInches() {
        assertEquals(12.0, LengthUnit.INCH.fromBaseUnit(1.0), EPS);
    }

    @Test
    void testFromBaseUnit_FeetToYards() {
        assertEquals(1.0, LengthUnit.YARD.fromBaseUnit(3.0), EPS);
    }

    @Test
    void testFromBaseUnit_FeetToCentimeters() {
        assertEquals(30.48, LengthUnit.CENTIMETER.fromBaseUnit(1.0), 1e-9);
    }

    // --- Enum unit-to-unit convert ---

    @Test
    void testEnumConvert_InchToFeet() {
        assertEquals(1.0, LengthUnit.INCH.convert(12.0, LengthUnit.FEET), EPS);
    }

    @Test
    void testEnumConvert_FeetToInch() {
        assertEquals(24.0, LengthUnit.FEET.convert(2.0, LengthUnit.INCH), EPS);
    }

    @Test
    void testEnumConvert_YardToInch() {
        assertEquals(36.0, LengthUnit.YARD.convert(1.0, LengthUnit.INCH), EPS);
    }

    @Test
    void testEnumConvert_CmToInch_WithinEpsilon() {
        assertEquals(1.0, LengthUnit.CENTIMETER.convert(2.54, LengthUnit.INCH), 1e-9);
    }

    // --- Backward compatibility: Length still works the same ---

    @Test
    void testLengthConvertTo_DelegatesToEnum() {
        Length l = new Length(1.0, LengthUnit.FEET);
        assertEquals(12.0, l.convertTo(LengthUnit.INCH), EPS);
    }

    @Test
    void testLengthAdd_DelegatesToEnum() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCH);
        Length result = l1.add(l2); // should be 2 FEET
        assertEquals(2.0, result.convertTo(LengthUnit.FEET), EPS);
    }

    @Test
    void testLengthAddWithTarget_DelegatesToEnum() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCH);
        Length result = l1.add(l2, LengthUnit.YARD); // 2ft -> 0.6667 yard
        assertEquals(2.0, result.convertTo(LengthUnit.FEET), EPS);
        assertEquals(2.0, Length.convert(0.6666667, LengthUnit.YARD, LengthUnit.FEET), 1e-3);
    }

    @Test
    void testLengthEquals_Refactored_StillWorks() {
        assertEquals(new Length(12.0, LengthUnit.INCH),
                     new Length(1.0, LengthUnit.FEET));
    }
}