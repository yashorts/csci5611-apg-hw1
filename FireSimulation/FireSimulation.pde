ArrayList<FireParticle> fireParticles;

void setup() {
  fireParticles = new ArrayList<FireParticle>();
  for (int i = 0; i < 10000; ++i) {
    fireParticles.add(new FireParticle(
      new V3d(200 + 100 * random(1), 200 + 10 * random(1), 0),
      new V3d(10 + 10 * random(1), 10 * random(1), 0),
      new V3d(0, 10, 0)
    ));
  }
  size(1000, 1000, P3D);
  textSize(24);
  noStroke();
  surface.setTitle("Processing");
}

void draw() {
  int frameStart = millis();

  // physics
  for (FireParticle p: fireParticles) {
    p.update(0.05);
  }

  int physicsEnd = millis();

  // rendering
  background(0);
  for (FireParticle p: fireParticles) {
    p.render();
  }

  int frameEnd = millis();

  // text overlay
  pushMatrix();
  translate(0, 0, 0);
  text("FPS: " + round(frameRate), 10, 30);
  text("Physics: " + round(physicsEnd - frameStart) + "ms", 10, 50);
  text("Rendering: " + round(frameEnd - physicsEnd) + "ms", 10, 70);
  popMatrix();

}

