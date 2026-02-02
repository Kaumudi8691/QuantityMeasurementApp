// package com.qma.quantitymeasurement;

// public class Feet {
//     private final double value;

//     public Feet(double value) {
//         this.value = value;
//     }

//     @Override
//     public boolean equals(Object obj) {
//         if (this == obj) return true;
//         if (obj == null || this.getClass() != obj.getClass()) return false;

// Feet other = (Feet) obj;
//         return Double.compare(this.value, other.value) == 0;
//     }

//     @Override
//     public int hashCode() {
//         return Double.hashCode(value);
        
//     }
// }
package com.qma.quantitymeasurement;

public class Feet {
    private final Length length;

    public Feet(double value) {
        this.length = new Length(value, LengthUnit.FEET);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Feet other)) return false;
        return this.length.equals(other.length);
    }

    @Override
    public int hashCode() {
        return length.hashCode();
    }
}