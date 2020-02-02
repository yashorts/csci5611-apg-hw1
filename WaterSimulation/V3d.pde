class V3d {
  float x, y, z;

  V3d() {
    x = y = z = 0;
  }

  V3d(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  V3d(V3d c) {
    this.x = c.x;
    this.y = c.y;
    this.z = c.z;
  }

  boolean equals(V3d b) {
    return this.x == b.x && this.y == b.y && this.z == b.z;
  }

  V3d plus(V3d b) {
    return new V3d(this.x + b.x, this.y + b.y, this.z + b.z);
  }

  V3d minus(V3d b) {
    return new V3d(this.x - b.x, this.y - b.y, this.z - b.z);
  }

  V3d scale(float t) {
    return new V3d(this.x * t, this.y * t, this.z * t);
  }

  float dot(V3d b) {
    return this.x * b.x + this.y * b.y + this.z * b.z;
  }

  V3d cross(V3d b) {
    return new V3d(this.y * b.z - this.z * b.y, this.z * b.x - this.x * b.z, this.x * b.y - this.y * b.x);
  }

  float abs() {
    return sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
  }

  float absSquare() {
    return this.x * this.x + this.y * this.y + this.z * this.z;
  }

  V3d unit() {
    float abs = this.abs();
    if (abs < 1e-6) { return new V3d(this); }
    else { return new V3d(this).scale(1 / abs); }
  }

  String toString() {
    return "V3d (" + x + ", " + y + ", " + z + ")";
  }

};
