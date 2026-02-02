package com.qma.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

public class FeetTest {

    @Test
    public void givenTwoFeetValues_WhenEqual_ShouldReturnTrue() {
       
Feet f1 = new Feet(1.0);
Feet f2 = new Feet(1.0);

        assertEquals(f1, f2);
    }
    
    
    @Test
    public void givenTwoFeetValues_WhenNotEqual_ShouldReturnFalse() {
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(2.0);
        assertNotEquals(f1, f2);
    }

}




    
