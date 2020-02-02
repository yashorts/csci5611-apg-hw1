class FireParticle {
  V3d position;
  V3d velocity;
  V3d acceleration;
  boolean isAlive;

  FireParticle (V3d pos, V3d vel, V3d acc) {
    position = pos;
    velocity = vel;
    acceleration = acc;
    isAlive = true;
  }

  void update(float dt){
    position = position.plus(velocity.scale(dt));
    velocity = velocity.plus(acceleration.scale(dt));

    if (position.x > 1000){
      isAlive = false;
    }

    if (position.x < 0){
      isAlive = false;
    }

    if (position.y > 1000){
      position.y = 1000;
      velocity.y = -velocity.y;
    }

    if (position.y < 0){
      isAlive = false;
    }

  }

  void render() {
    if (isAlive) {
      pushMatrix();
      translate(position.x, position.y, position.z);
      box(4);
      popMatrix();
    }
  }

}

