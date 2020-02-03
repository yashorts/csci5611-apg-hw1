static class CameraState {
  static float ex = 500;
  static float ey = 500;
  static float ez = 500;

  static float cx = 500;
  static float cy = 500;
  static float cz = 0;

  static float ux = 0;
  static float uy = 1;
  static float uz = 0;
}

FireParticleSystem system;

void setup() {
  system = new FireParticleSystem(10000);
  camera(
    CameraState.ex, CameraState.ey, CameraState.ez,
    CameraState.cx, CameraState.cy, CameraState.cz,
    CameraState.ux, CameraState.uy, CameraState.uz
    );
  size(1000, 1000, P3D);
  textSize(24);
  noStroke();
  surface.setTitle("Processing");
}

void moveCameraFromInput() {
  if (keyPressed && keyCode == UP) {
    CameraState.ez -= 5;
  } else if (keyPressed && keyCode == DOWN) {
    CameraState.ez += 5;
  }
  camera(
    CameraState.ex, CameraState.ey, CameraState.ez,
    CameraState.cx, CameraState.cy, CameraState.cz,
    CameraState.ux, CameraState.uy, CameraState.uz
    );
}

void draw() {
  moveCameraFromInput();

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

