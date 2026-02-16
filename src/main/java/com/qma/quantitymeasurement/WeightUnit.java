
package com.qma.quantitymeasurement;

public enum WeightUnit {
    KILOGRAM(1.0),           // base
    GRAM(0.001),             // 1 g = 0.001 kg
    POUND(0.45359237);       // 1 lb = 0.45359237 kg

    private final double toKilogramFactor;

    WeightUnit(double toKilogramFactor) {
        this.toKilogramFactor = toKilogramFactor;
    }

    /** Convert this unit -> base (kilogram) */
    public double toBaseUnit(double value) {
        return value * toKilogramFactor;
    }

    /** Convert base (kilogram) -> this unit */
    public double fromBaseUnit(double kilogramValue) {
        return kilogramValue / toKilogramFactor;
    }

    /** Convert value in THIS unit -> target unit via base kg */
    public double convert(double value, WeightUnit target) {
        if (target == null) throw new IllegalArgumentException("Target unit cannot be null");
        double base = this.toBaseUnit(value);
        return target.fromBaseUnit(base);
    }
}