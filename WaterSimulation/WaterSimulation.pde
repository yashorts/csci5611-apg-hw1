String projectTitle = "water simulation";

WaterParticle waterParticle = new WaterParticle();

void setup() {
  size(1000, 1000, P3D);
  textSize(24);
  noStroke();
}

void draw() {
  float startFrame = millis();

  // physics
  waterParticle.update(0.15);

  float endPhysics = millis();

  // rendering
  // background
  background(0);
  // text overlay
  translate(0, 0);
  text("FPS: " + round(frameRate), 10, 30);
  // objects
  waterParticle.render();

  float endFrame = millis();
  /* text("Physics: " + str(endPhysics - startFrame) + "ms", 10, 60); */
  /* text("Render: " + str(endFrame - startFrame) + "ms", 10, 60); */
}

