class V3D {
  float x, y, z;

  V3D() {
    x = y = z = 0;
  }

  V3D(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  V3D(V3D c) {
    this.x = c.x;
    this.y = c.y;
    this.z = c.z;
  }

  boolean equals(V3D b) {
    return this.x == b.x && this.y == b.y && this.z == b.z;
  }

  V3D plus(V3D b) {
    return new V3D(this.x + b.x, this.y + b.y, this.z + b.z);
  }

  V3D minus(V3D b) {
    return new V3D(this.x - b.x, this.y - b.y, this.z - b.z);
  }

  V3D scale(float t) {
    return new V3D(this.x * t, this.y * t, this.z * t);
  }

  float dot(V3D b) {
    return this.x * b.x + this.y * b.y + this.z * b.z;
  }

  V3D cross(V3D b) {
    return new V3D(this.y * b.z - this.z * b.y, this.z * b.x - this.x * b.z, this.x * b.y - this.y * b.x);
  }

  float abs() {
    return sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
  }

  float absSquare() {
    return this.x * this.x + this.y * this.y + this.z * this.z;
  }

  V3D unit() {
    float abs = this.abs();
    if (abs < 1e-6) { return new V3D(this); }
    else { return new V3D(this).scale(1 / abs); }
  }

  String toString() {
    return "V3D (" + x + ", " + y + ", " + z + ")";
  }

};
