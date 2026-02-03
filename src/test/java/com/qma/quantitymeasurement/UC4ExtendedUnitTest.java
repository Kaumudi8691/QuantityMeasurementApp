package com.qma.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class UC4ExtendedUnitTest {

    @Test
    void test_YardToYard_SameValue() {
        assertEquals(new Length(1.0, LengthUnit.YARD),
                     new Length(1.0, LengthUnit.YARD));
    }

    @Test
    void test_YardToFeet_Equivalent() {
        assertEquals(new Length(1.0, LengthUnit.YARD),
                     new Length(3.0, LengthUnit.FEET));
    }

    @Test
    void test_YardToInch_Equivalent() {
        assertEquals(new Length(1.0, LengthUnit.YARD),
                     new Length(36.0, LengthUnit.INCH));
    }

    @Test
    void test_CentimeterToInch_Equivalent() {
        assertEquals(new Length(1.0, LengthUnit.CENTIMETER),
                     new Length(0.393701, LengthUnit.INCH));
    }

    @Test
    void test_AllUnits_ComplexScenario() {
        assertEquals(new Length(2.0, LengthUnit.YARD),
                     new Length(6.0, LengthUnit.FEET));

        assertEquals(new Length(6.0, LengthUnit.FEET),
                     new Length(72.0, LengthUnit.INCH));
    }
}