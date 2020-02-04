public class Vector3D {
    float x, y, z;

    public static Vector3D randomUnit() {
        return new Vector3D((float) Math.random(), (float) Math.random(), (float) Math.random()).unit();
    }

    public static Vector3D of(float x, float y, float z) {
        return new Vector3D(x, y, z);
    }

    public static Vector3D of(double x, double y, double z) {
        return new Vector3D((float) x, (float) y, (float) z);
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

    private Vector3D() {
        x = y = z = 0;
    }

    private Vector3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    private Vector3D(float c) {
        this.x = c;
        this.y = c;
        this.z = c;
    }

    private Vector3D(Vector3D c) {
        this.x = c.x;
        this.y = c.y;
        this.z = c.z;
    }
}
