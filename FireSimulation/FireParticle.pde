class FireParticle {
  V3d position;
  V3d velocity;
  V3d acceleration;
  boolean isAlive;

  FireParticle () {
    position = new V3d(200 + 100 * random(1), 200 + 10 * random(1), 0);
    velocity = new V3d(10 + 10 * random(1), 10 * random(1), 0);
    acceleration = new V3d(0, 10, 0);
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
