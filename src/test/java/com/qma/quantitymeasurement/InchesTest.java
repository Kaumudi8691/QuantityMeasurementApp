package com.qma.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

public class InchesTest {

    @Test
    public void givenTwoInchValues_WhenSameValue_ShouldBeEqual() {
        Inches i1 = new Inches(1.0);
        Inches i2 = new Inches(1.0);
        assertEquals(i1, i2);
    }

    @Test
    public void givenTwoInchValues_WhenDifferentValue_ShouldNotBeEqual() {
        Inches i1 = new Inches(1.0);
        Inches i2 = new Inches(2.0);
        assertNotEquals(i1, i2);
    }

    @Test
    public void givenNull_WhenCompared_ShouldNotBeEqual() {
        Inches i1 = new Inches(1.0);
        assertNotEquals(i1, null);
    }

    @Test
    public void givenDifferentType_WhenCompared_ShouldNotBeEqual() {
        Inches i1 = new Inches(1.0);
        assertNotEquals(i1, 1.0); // different type
    }

    @Test
    public void givenSameReference_WhenCompared_ShouldBeEqual() {
        Inches i1 = new Inches(1.0);
        assertEquals(i1, i1);
    }
}