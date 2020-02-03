class CameraState {
  Vector3D eye;
  Vector3D center;
  Vector3D up;

  CameraState(Vector3D eye, Vector3D center, Vector3D up) {
    this.eye = eye;
    this.center = center;
    this.up = up;
    apply();
  }

  void apply() {
    camera(
        eye.x, eye.y, eye.z,
        center.x, center.y, center.z,
        up.x, up.y, up.z
        );
  }
}

FireParticleSystem ps;
CameraState cs;
PShape s;

void setup() {
  size(1000, 700, P3D);
  ps = new FireParticleSystem(1);
  cs = new CameraState(
      new Vector3D(1000 / 2.0, 700 / 2.0, (700 / 2.0) / tan(PI * 30.0 / 180.0)),
      new Vector3D(1000 / 2.0, 700 / 2.0, 0),
      new Vector3D(0, 1, 0)
      );
  noStroke();
  surface.setTitle("Processing");
  s = loadShape("BirchTree_1.obj");
  /* s.rotate(3.14, 0.0, 0.0, 1.0); */
  s.rotate(3.14, 0.0, 1.0, 0.0);
}

void moveCameraFromInput() {
  Vector3D viewDir = cs.center.minus(cs.eye).unit();
  Vector3D sideDir = new Vector3D(viewDir.z, 0, -viewDir.x);
  if (keyPressed && keyCode == UP) {
    cs.eye = cs.eye.plus(viewDir.scale(4));
  } else if (keyPressed && keyCode == DOWN) {
    cs.eye = cs.eye.plus(viewDir.scale(-4));
  } else if (keyPressed && keyCode == LEFT) {
    cs.eye = cs.eye.plus(sideDir.scale(-4));
    s.rotate(0.1, 0.0, 1.0, 0.0);
  } else if (keyPressed && keyCode == RIGHT) {
    cs.eye = cs.eye.plus(sideDir.scale(4));
    s.rotate(-0.1, 0.0, 1.0, 0.0);
  }

  cs.apply();
}

void draw() {
  // camera
  moveCameraFromInput();
  int frameStart = millis();
  // physics
  ps.update();
  int physicsEnd = millis();
  // rendering
  background(255);
  lights();
  shape(s, width * 0.5, height * 0.6, 200, 200);
  fill(255, 0, 0);
  ps.render();
  int frameEnd = millis();
  // text overlay
  surface.setTitle("Processing"
      + " FPS: " + round(frameRate)
      + " Phy: " + round(physicsEnd - frameStart) + "ms"
      + " Ren: " + round(frameEnd - physicsEnd) + "ms");
}

