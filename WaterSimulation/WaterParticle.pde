class WaterParticle {
  float sx;
  float vx;
  float ax;

  float sy;
  float vy;
  float ay;

  float radius;
  float squareBoundary;

  WaterParticle (float squareBoundary) {
    this.squareBoundary = squareBoundary;
    sy = squareBoundary / 2;
    vy = 80;
    ay = 9.8;

    sx = squareBoundary / 2;
    vx = 50;
    ax = 0;
    radius = 40;
  }

  void update(float dt){

    sx = sx + vx * dt;
    vx = vx + ax * dt;

    sy = sy + vy * dt;
    vy = vy + ay * dt;

    if (sx + radius > squareBoundary){
      sx = squareBoundary - radius;
      vx *= -.95;
    }

    if (sx - radius < 0){
      sx = radius;
      vx *= -.95;
    }

    if (sy + radius > squareBoundary){
      sy = squareBoundary - radius;
      vy *= -.95;
    }

    if (sy - radius < 0){
      sy = radius;
      vy *= -.95;
    }

  }

  float normalizedPositionX() {
    return sx / sqrt(sx * sx + sy * sy);
  }

  float normalizedPositionY() {
    return sy / sqrt(sx * sx + sy * sy);
  }

}
