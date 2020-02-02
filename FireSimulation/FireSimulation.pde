String projectTitle = "fire simulation";

ArrayList<FireParticle> fireParticles;

void setup() {
  fireParticles = new ArrayList<FireParticle>();
  for (int i = 0; i < 10000; ++i) {
    fireParticles.add(new FireParticle());
  }
  size(1000, 1000, P3D);
  textSize(24);
  noStroke();
}

void draw() {
  float startFrame = millis();

  // physics
  for (FireParticle p: fireParticles) {
    p.update(0.05);
  }

  float endPhysics = millis();

  // rendering
  // background
  background(0);
  // text overlay
  translate(0, 0, 0);
  text("FPS: " + round(frameRate), 10, 30);
  // objects
  for (FireParticle p: fireParticles) {
    p.render();
  }

  float endFrame = millis();
  /* text("Physics: " + str(endPhysics - startFrame) + "ms", 10, 60); */
  /* text("Render: " + str(endFrame - startFrame) + "ms", 10, 60); */
}

