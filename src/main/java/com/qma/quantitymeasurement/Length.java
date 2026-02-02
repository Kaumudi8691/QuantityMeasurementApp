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