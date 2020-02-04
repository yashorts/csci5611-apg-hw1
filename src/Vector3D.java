public class Vector3D {
    float x, y, z;

    Vector3D() {
        x = y = z = 0;
    }

    Vector3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    Vector3D(Vector3D c) {
        this.x = c.x;
        this.y = c.y;
        this.z = c.z;
    }

    public Vector3D(double x, double y, double z) {
        this.x = (float) x;
        this.y = (float) y;
        this.z = (float) z;
    }

    public boolean equals(Vector3D b) {
        return this.x == b.x && this.y == b.y && this.z == b.z;
    }

    public Vector3D plus(Vector3D b) {
        return new Vector3D(this.x + b.x, this.y + b.y, this.z + b.z);
    }

    public Vector3D minus(Vector3D b) {
        return new Vector3D(this.x - b.x, this.y - b.y, this.z - b.z);
    }

    public Vector3D scale(float t) {
        return new Vector3D(this.x * t, this.y * t, this.z * t);
    }

    public float dot(Vector3D b) {
        return this.x * b.x + this.y * b.y + this.z * b.z;
    }

    public Vector3D cross(Vector3D b) {
        return new Vector3D(this.y * b.z - this.z * b.y, this.z * b.x - this.x * b.z, this.x * b.y - this.y * b.x);
    }

    public float abs() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public float absSquare() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    public Vector3D unit() {
        float abs = this.abs();
        if (abs < 1e-6f) {
            return new Vector3D(this);
        } else {
            return new Vector3D(this).scale(1 / abs);
        }
    }

    public String toString() {
        return "V3D (" + x + ", " + y + ", " + z + ")";
    }
}
