class FireParticleSystem {
  int numParticles;
  ArrayList<FireParticle> fireParticles;

  FireParticleSystem(int numParticles) {
    this.numParticles = numParticles;
    fireParticles = new ArrayList<FireParticle>();
    for (int i = 0; i < numParticles; ++i) {
      fireParticles.add(new FireParticle(
        new V3d(200 + 100 * random(1), 200 + 10 * random(1), 0),
        new V3d(10 + 10 * random(1), 10 * random(1), 0),
        new V3d(0, 10, 0)
      ));
    }
  }

  void update() {
    for (FireParticle p: fireParticles) {
      p.update(0.05);
    }
  }

  void render() {
    for (FireParticle p: fireParticles) {
      p.render();
    }
  }

}

