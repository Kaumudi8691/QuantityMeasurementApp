package com.qma.quantitymeasurement;

import java.util.Objects;

public final class Weight {
    private final double value;
    private final WeightUnit unit;

    // For stable equality on doubles (same policy as Length)
    private static final double EPSILON = 1e-6;

    public Weight(double value, WeightUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be a finite number");
        }
        this.unit = unit;
        this.value = value;
    }

    /** Normalize to base (kilogram) via enum */
    private double valueInKilogram() {
        return unit.toBaseUnit(value);
    }

    // ---------- UC5-like conversion helpers (delegate to enum) ----------

    /**
     * Convert a raw value from source unit to target unit.
     * Uses enum-owned conversion (UC8-style responsibility).
     */
    public static double convert(double value, WeightUnit source, WeightUnit target) {
        if (source == null) {
            throw new IllegalArgumentException("Source unit cannot be null");
        }
        if (target == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be a finite number");
        }
        if (source == target) return value;
        return source.convert(value, target);
    }

    /** Convert this Weight's value to another unit. */
    public double convertTo(WeightUnit target) {
        return convert(this.value, this.unit, target);
    }

    // ---------- UC6: add (result in this.unit) ----------

    public Weight add(Weight other) {
        if (other == null) {
            throw new IllegalArgumentException("Other weight cannot be null");
        }
        double sumKg = this.valueInKilogram() + other.valueInKilogram();
        double sumInThis = this.unit.fromBaseUnit(sumKg);
        return new Weight(sumInThis, this.unit);
    }

    // ---------- UC7: add with explicit target unit ----------

    public Weight add(Weight other, WeightUnit target) {
        if (other == null) {
            throw new IllegalArgumentException("Other weight cannot be null");
        }
        if (target == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }
        double sumKg = this.valueInKilogram() + other.valueInKilogram();
        double sumInTarget = target.fromBaseUnit(sumKg);
        return new Weight(sumInTarget, target);
    }

    // ---------- Equality & HashCode (UC1/UC4 style) ----------

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Weight other)) return false;
        return Math.abs(this.valueInKilogram() - other.valueInKilogram()) <= EPSILON;
    }

    @Override
    public int hashCode() {
        long rounded = Math.round(this.valueInKilogram() / EPSILON);
        return Objects.hash(rounded);
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }

    // Optional getters (keep if useful)
    public double getValue() { return value; }
    public WeightUnit getUnit() { return unit; }
}