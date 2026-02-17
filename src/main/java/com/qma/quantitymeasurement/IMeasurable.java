package com.qma.quantitymeasurement;

/**
 * Minimal unit contract adopted by all measurement unit enums.
 * Each enum defines a conversion factor to its category's base unit.
 * Example bases: feet (length), kilogram (weight), liter (volume).
 */
public interface IMeasurable {

    /** @return conversion factor to the category's base unit. */
    double getConversionFactor();

    /** Convert a value in THIS unit to base unit using its factor. */
    default double convertToBaseUnit(double value) {
        return value * getConversionFactor();
    }

    /** Convert a base-unit value to THIS unit using its factor. */
    default double convertFromBaseUnit(double baseValue) {
        return baseValue / getConversionFactor();
    }

    /** Readable unit name (e.g., "FEET", "INCH", "KILOGRAM"). */
    String getUnitName();
}
