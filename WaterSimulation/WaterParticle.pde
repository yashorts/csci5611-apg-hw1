class WaterParticle {
  V3d position;
  V3d velocity;
  V3d acceleration;
  float radius;

  WaterParticle () {
    position = new V3d(200, 200, 0);
    velocity = new V3d(10, 0, 0);
    acceleration = new V3d(0, 10, 0);
    radius = 10;
  }

  void update(float dt){

    position = position.plus(velocity.scale(dt));
    velocity = velocity.plus(acceleration.scale(dt));
    println(velocity);

    if (position.x + radius > 1000){
      position.x = 1000 - radius;
      velocity.x *= -.95;
    }

    if (position.x - radius < 0){
      position.x = radius;
      velocity.x *= -.95;
    }

    if (position.y + radius > 1000){
      position.y = 1000 - radius;
      velocity.y *= -.5;
    }

    if (position.y - radius < 0){
      position.y = radius;
      velocity.y *= -.5;
    }

  }

  void render() {
    fill(255);
    translate(position.x, position.y);
    sphere(radius);
  }

}
