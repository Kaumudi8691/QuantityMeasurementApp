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

    private double valueInFeet() {
        return unit.toFeet(value);
    }

    
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


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Length other)) return false;
        return Double.compare(this.valueInFeet(), other.valueInFeet()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Double.valueOf(valueInFeet()));
    }
}