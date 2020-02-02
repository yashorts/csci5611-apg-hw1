FireParticleSystem system;

void setup() {
  system = new FireParticleSystem(10000);
  size(1000, 1000, P3D);
  textSize(24);
  noStroke();
  surface.setTitle("Processing");
}

/* void moveCameraFromInput() { */
/*   if (keyPressed && keyCode == UP) { */
/*     camera(500, 500, 500, 500, 500, 0, 0, 1, 0); */
/*   } */
/* } */

void draw() {
  /* moveCameraFromInput(); */

  int frameStart = millis();

  // physics
  system.update();

  int physicsEnd = millis();

  // rendering
  background(0);
  system.render();

  int frameEnd = millis();

  // text overlay
  pushMatrix();
  translate(0, 0, 0);
  text("FPS: " + round(frameRate), 10, 30);
  text("Physics: " + round(physicsEnd - frameStart) + "ms", 10, 50);
  text("Rendering: " + round(frameEnd - physicsEnd) + "ms", 10, 70);
  popMatrix();

}

/* static class CameraState { */
/*   static V3D eye = new V3D(500, 500, 500); */
/*   static V3D center = new V3D(500, 500, 0); */
/*   static V3D up = new V3D(0, 1, 0); */
/* } */

