import processing.core.PApplet;
import processing.core.PShape;

import java.util.ArrayList;

public class Fire extends PApplet {

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

        public void apply() {
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

    @Override
    public void setup() {
        ps = new FireParticleSystem(1);
        cs = new CameraState(
                new Vector3D(1000 / 2.0f, 700 / 2.0f, (700 / 2.0f) / tan(PI * 30.0f / 180.0f)),
                new Vector3D(1000 / 2.0f, 700 / 2.0f, 0),
                new Vector3D(0, 1, 0)
        );
        noStroke();
        surface.setTitle("Processing");
        s = loadShape("BirchTree_1.obj");
        /* s.rotate(3.14, 0.0, 0.0, 1.0); */
        s.rotate(3.14f, 0.0f, 1.0f, 0.0f);
    }

    public void moveCameraFromInput() {
        Vector3D viewDir = cs.center.minus(cs.eye).unit();
        Vector3D sideDir = new Vector3D(viewDir.z, 0, -viewDir.x);
        if (keyPressed && keyCode == UP) {
            cs.eye = cs.eye.plus(viewDir.scale(4));
        } else if (keyPressed && keyCode == DOWN) {
            cs.eye = cs.eye.plus(viewDir.scale(-4));
        } else if (keyPressed && keyCode == LEFT) {
            cs.eye = cs.eye.plus(sideDir.scale(-4));
            s.rotate(0.1f, 0.0f, 1.0f, 0.0f);
        } else if (keyPressed && keyCode == RIGHT) {
            cs.eye = cs.eye.plus(sideDir.scale(4));
            s.rotate(-0.1f, 0.0f, 1.0f, 0.0f);
        }

        cs.apply();
    }

    @Override
    public void draw() {
        // camera
        moveCameraFromInput();
        int frameStart = millis();
        // physics
        ps.update();
        int physicsEnd = millis();
        // rendering
        background(255);
        lights();
        shape(s, width * 0.5f, height * 0.6f, 200, 200);
        fill(255, 0, 0);
        ps.render();
        int frameEnd = millis();
        // text overlay
        surface.setTitle("Processing"
                + " FPS: " + round(frameRate)
                + " Phy: " + round(physicsEnd - frameStart) + "ms"
                + " Ren: " + round(frameEnd - physicsEnd) + "ms");
    }

    class FireParticle {
        Vector3D position;
        Vector3D velocity;
        Vector3D acceleration;
        boolean isAlive;

        FireParticle(Vector3D pos, Vector3D vel, Vector3D acc) {
            position = pos;
            velocity = vel;
            acceleration = acc;
            isAlive = true;
        }

        public void update(float dt) {
            position = position.plus(velocity.scale(dt));
            velocity = velocity.plus(acceleration.scale(dt));

            if (position.x > 1000) {
                isAlive = false;
            }

            if (position.x < 0) {
                isAlive = false;
            }

            if (position.y > 1000) {
                position.y = 1000;
                velocity.y = -velocity.y * 0.4f;
            }

            if (position.y < 0) {
                isAlive = false;
            }

        }

        public void render() {
            if (isAlive) {
                pushMatrix();
                translate(position.x, position.y, position.z);
                box(4);
                popMatrix();
            }
        }

    }

    class FireParticleSystem {
        int numParticles;
        ArrayList<FireParticle> fireParticles;

        FireParticleSystem(int numParticles) {
            this.numParticles = numParticles;
            fireParticles = new ArrayList<FireParticle>();
            for (int i = 0; i < numParticles; ++i) {
                fireParticles.add(new FireParticle(
                        new Vector3D(200 + 100 * random(1), 200 + 10 * random(1), 0),
                        new Vector3D(10 + 10 * random(1), 10 * random(1), 0),
                        new Vector3D(0, 10, 0)
                ));
            }
        }

        public void update() {
            for (FireParticle p : fireParticles) {
                p.update(0.05f);
            }
        }

        public void render() {
            for (FireParticle p : fireParticles) {
                p.render();
            }
        }

    }

    public void settings() {
        size(1000, 700, P3D);
    }

    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[]{"Fire"};
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }
}
