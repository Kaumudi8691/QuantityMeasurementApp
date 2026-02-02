// package com.qma.quantitymeasurement;

// public class Inches {
//     private final double value;

//     public Inches(double value) {
//         this.value = value;
//     }

//     @Override
//     public boolean equals(Object obj) {
//         if (this == obj) return true;
//         if (obj == null || this.getClass() != obj.getClass()) return false;

//         Inches other = (Inches) obj;
//         return Double.compare(this.value, other.value) == 0;
//     }

//     @Override
//     public int hashCode() {
//         return Double.hashCode(value);
//     }
// }

package com.qma.quantitymeasurement;

public class Inches {
    private final Length length;

    public Inches(double value) {
        this.length = new Length(value, LengthUnit.INCH);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Inches other)) return false;
        return this.length.equals(other.length);
    }

    @Override
    public int hashCode() {
        return length.hashCode();
    }
}