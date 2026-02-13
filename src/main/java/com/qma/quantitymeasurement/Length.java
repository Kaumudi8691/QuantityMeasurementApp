package com.qma.quantitymeasurement;

import java.util.Objects;

public class Length {
    private final double value;
    private final LengthUnit unit;

    public Length(double value, LengthUnit unit) {
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Value must be a finite number");
        }
        this.value = value;
        this.unit = unit;
    }

    
// UC8: delegate to enum (feet is base)
    private double valueInFeet() {
        return unit.toBaseUnit(value);
    }


    // private double valueInFeet() {
    //     return unit.toFeet(value);
    // }

    
 /**
     * UC5: Convert a raw value from source unit to target unit.
     * Example: convert(1.0, FEET, INCH) -> 12.0
     */
    public static double convert(double value, LengthUnit sourceUnit, LengthUnit targetUnit) {
        if (sourceUnit == null || targetUnit == null) {
            throw new IllegalArgumentException("Source or target unit cannot be null");
        }
        if (Double.isNaN(value) || Double.isInfinite(value)) {

            
throw new IllegalArgumentException("Value must be a finite number");
        }

        // Same-unit conversion returns same value unchanged
        if (sourceUnit == targetUnit) {
            return value;
        }

        // Convert source -> FEET (base)
        double valueInFeet = sourceUnit.toFeet(value);

        
// Convert FEET -> target
        // We need "how many feet in 1 target unit", which is: targetUnit.toFeet(1.0)
        double oneTargetInFeet = targetUnit.toFeet(1.0);

        // Avoid divide-by-zero (should never happen with correct enum factors)
        if (oneTargetInFeet == 0.0) {
            throw new IllegalArgumentException("Invalid conversion factor for target unit: " + targetUnit);
        }

        
 double result = valueInFeet / oneTargetInFeet;

        // Optional safety: if result became NaN/Infinity because of extreme inputs
        if (Double.isNaN(result) || Double.isInfinite(result)) {
            throw new ArithmeticException("Conversion overflow/underflow for value: " + value);
        }

        return result;
    }

    
 /**
     * UC5: Convert this Length object's value to another unit.
     * Example: new Length(1.0, FEET).convertTo(INCH) -> 12.0
     */
    public double convertTo(LengthUnit targetUnit) {
        return convert(this.value, this.unit, targetUnit);
    }


    /**
 * UC5: Instance conversion helper to match test usage: l.convert(INCH)
 */
public double convert(LengthUnit targetUnit) {
    return convert(this.value, this.unit, targetUnit);
}


/**
 * UC6: Add another Length to this Length.
 * Result is returned in the unit of the first operand (this.unit).
 *
 * Examples:
 *  new Length(1.0, FEET).add(new Length(2.0, FEET)) -> Length(3.0, FEET)
 *  new Length(1.0, FEET).add(new Length(12.0, INCH)) -> Length(2.0, FEET)
 *  new Length(12.0, INCH).add(new Length(1.0, FEET)) -> Length(24.0, INCH)
 */
public Length add(Length other) {
    if (other == null) {
        throw new IllegalArgumentException("Other length cannot be null");
    }

    // Convert both values to base unit (FEET), then add
    double sumInFeet = this.valueInFeet() + other.valueInFeet();

    // Convert the sum from FEET back to the unit of the first operand
    double sumInThisUnit = convert(sumInFeet, LengthUnit.FEET, this.unit);

    // Return new immutable Length object
    return new Length(sumInThisUnit, this.unit);
}



    /* * UC7: Add another Length to this Length with explicit target unit.
     * Result is returned in the specified targetUnit (not dependent on operand units).
     *
     * Examples:
     *  new Length(1.0, FEET).add(new Length(12.0, INCH), FEET) -> Length(2.0, FEET)
     *  new Length(1.0, FEET).add(new Length(12.0, INCH), INCH) -> Length(24.0, INCH)
     *  new Length(1.0, FEET).add(new Length(12.0, INCH), YARD) -> Length(0.6667, YARD)
     */
    public Length add(Length other, LengthUnit targetUnit) {
        if (other == null) {
            throw new IllegalArgumentException("Other length cannot be null");
        }

if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        // Convert both values to base unit (FEET), then add
        double sumInFeet = this.valueInFeet() + other.valueInFeet();

        // Convert sum from FEET to the explicitly specified target unit
        double sumInTargetUnit = convert(sumInFeet, LengthUnit.FEET, targetUnit);

        // Return new immutable Length object in target unit
        return new Length(sumInTargetUnit, targetUnit);
    }

    //UC4 equality across units    
    private static final double EPSILON = 1e-6;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Length other)) return false;
        //return Double.compare(this.valueInFeet(), other.valueInFeet()) == 0;
        return Math.abs(this.valueInFeet() - other.valueInFeet()) <= EPSILON;
    }

    @Override
    public int hashCode() {     
 // Make hashCode consistent with epsilon-based equals
    long rounded = Math.round(this.valueInFeet() / EPSILON);
    return Objects.hash(rounded);

        //return Objects.hash(Double.valueOf(valueInFeet()));
    }
}