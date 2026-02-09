package com.qma.quantitymeasurement;

// public enum LengthUnit {
//     FEET(1.0),          // base unit
//     INCH(1.0 / 12.0);   // 1 inch = 1/12 feet

//     private final double toFeetFactor;

//     LengthUnit(double toFeetFactor) {
//         this.toFeetFactor = toFeetFactor;
//     }

//     public double toFeet(double value) {
//         return value * toFeetFactor;
//     }
// }

public enum LengthUnit {

    FEET(1.0),
    INCH(1.0 / 12.0),
    YARD(3.0),
    //CENTIMETER(0.393701 / 12.0); // 1 cm = 0.393701 inch => in feet = 0.393701/12
    CENTIMETER(1.0 / 30.48);  // exact: 1 ft = 30.48 cm

    private final double toFeetFactor;

    LengthUnit(double toFeetFactor) {
        this.toFeetFactor = toFeetFactor;
    }

    public double toFeet(double value) {
        return value * toFeetFactor;
    }
    
// Convert feet (base unit) -> current unit
    public double fromFeet(double feetValue) {
        return feetValue / toFeetFactor;
    }

}