class Vector3D {
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

  boolean equals(Vector3D b) {
    return this.x == b.x && this.y == b.y && this.z == b.z;
  }

  Vector3D plus(Vector3D b) {
    return new Vector3D(this.x + b.x, this.y + b.y, this.z + b.z);
  }

  Vector3D minus(Vector3D b) {
    return new Vector3D(this.x - b.x, this.y - b.y, this.z - b.z);
  }

  Vector3D scale(float t) {
    return new Vector3D(this.x * t, this.y * t, this.z * t);
  }

  float dot(Vector3D b) {
    return this.x * b.x + this.y * b.y + this.z * b.z;
  }

  Vector3D cross(Vector3D b) {
    return new Vector3D(this.y * b.z - this.z * b.y, this.z * b.x - this.x * b.z, this.x * b.y - this.y * b.x);
  }

  float abs() {
    return sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
  }

  float absSquare() {
    return this.x * this.x + this.y * this.y + this.z * this.z;
  }

  Vector3D unit() {
    float abs = this.abs();
    if (abs < 1e-6) { return new Vector3D(this); }
    else { return new Vector3D(this).scale(1 / abs); }
  }

  String toString() {
    return "Vector3D (" + x + ", " + y + ", " + z + ")";
  }

};
