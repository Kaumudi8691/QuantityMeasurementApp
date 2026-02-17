
package com.qma.quantitymeasurement;

import java.util.Objects;

/**
 * Generic quantity (value + unit U). Works for any enum implementing IMeasurable.
 */
public final class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;

    private static final double EPSILON = 1e-6;

    public Quantity(double value, U unit) {
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Value must be a finite number");
        this.value = value;
        this.unit = unit;
    }

    private double valueInBase() {
        return unit.convertToBaseUnit(value);
    }

    // -------- Conversion --------

    public static <U extends IMeasurable> double convert(double value, U source, U target) {
        if (source == null) throw new IllegalArgumentException("Source unit cannot be null");
        if (target == null) throw new IllegalArgumentException("Target unit cannot be null");
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Value must be a finite number");
        if (source == target) return value;
        return target.convertFromBaseUnit(source.convertToBaseUnit(value));
    }

    public double convertTo(U target) {
        return convert(this.value, this.unit, target);
    }

    // -------- Addition --------

    public Quantity<U> add(Quantity<U> other) {
        if (other == null) throw new IllegalArgumentException("Other quantity cannot be null");
        double sumBase = this.valueInBase() + other.valueInBase();
        double sumInThis = unit.convertFromBaseUnit(sumBase);
        return new Quantity<>(sumInThis, unit);
    }

    public Quantity<U> add(Quantity<U> other, U target) {
        if (other == null) throw new IllegalArgumentException("Other quantity cannot be null");
        if (target == null) throw new IllegalArgumentException("Target unit cannot be null");
        double sumBase = this.valueInBase() + other.valueInBase();
        double sumInTarget = target.convertFromBaseUnit(sumBase);
        return new Quantity<>(sumInTarget, target);
    }

    // -------- Equality & Hashing --------

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Quantity<?> q)) return false;

        // Prevent cross-category equality (Length vs Weight)
        if (!this.unit.getClass().equals(q.unit.getClass())) return false;

        double a = this.valueInBase();
        double b = q.unit.convertToBaseUnit(q.value);
        return Math.abs(a - b) <= EPSILON;
    }

    @Override
    public int hashCode() {
        long rounded = Math.round(this.valueInBase() / EPSILON);
        return Objects.hash(rounded, unit.getClass());
    }

    @Override
    public String toString() {
        return value + " " + unit.getUnitName();
    }

    // Optional getters
    public double getValue() { return value; }
    public U getUnit()       { return unit; }
}
