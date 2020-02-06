import processing.core.PApplet;
import processing.event.KeyEvent;
import queasycam.QueasyCam;

import java.util.ArrayList;
import java.util.List;

public class FireSimulation extends PApplet {
    final int WIDTH = 1000;
    final int HEIGHT = 700;
    QueasyCam cam;
    Ground ground;
    List<StaticGroundObject> staticGroundObjects = new ArrayList<>();
    FlameThrower flameThrower;

    public void settings() {
        size(WIDTH, HEIGHT, P3D);
    }

    public void setup() {
        surface.setTitle("Processing");
        noStroke();
        // camera
        cam = new QueasyCam(this);
        cam.sensitivity = 2f;
        cam.speed = 2f;
        // ground
        ground = new Ground(this,
                Vec3.of(0, 0, 0), Vec3.of(0, 0, 1), Vec3.of(1, 0, 0),
                1024, 1024,
                loadImage("grass.jpg"));
        // rocks and trees
        for (int i = 0; i < 50; ++i) {
            staticGroundObjects.add(new StaticGroundObject(this, loadShape("Rock_" + (int) random(1, 8) + ".obj"), Vec3.of(random(-500, 500), 0, random(-500, 500))));
        }
        for (int i = 0; i < 3; ++i) {
            staticGroundObjects.add(new StaticGroundObject(this, loadShape("BirchTree_" + (int) random(1, 6) + ".obj"), Vec3.of(random(-500, 500), 0, random(-500, 500))));
        }
        // flame thrower
        flameThrower = new FlameThrower(this,
                Vec3.of(300, 0, 150),
                Vec3.of(0, 0, -1),
                100, 200, 20100,
                "SniperRifle.obj");
    }

    public void draw() {
        if (keyPressed && keyCode == DOWN) {
            flameThrower.moveOrigin(Vec3.of(0, 0, 0.5));
        }
        if (keyPressed && keyCode == UP) {
            flameThrower.moveOrigin(Vec3.of(0, 0, -0.5));
        }
        if (keyPressed && keyCode == RIGHT) {
            flameThrower.moveOrigin(Vec3.of(0.5, 0, 0));
        }
        if (keyPressed && keyCode == LEFT) {
            flameThrower.moveOrigin(Vec3.of(-0.5, 0, 0));
        }
//        flameThrower.setOrigin(Vector3D.of(cam.position.x, cam.position.y, cam.position.z));

        // background
        background(85, 156, 185);
        // ground and rocks
        pushMatrix();
        translate(300, 100, -40);
        ground.render();

        for (StaticGroundObject staticGroundObject : staticGroundObjects) {
            staticGroundObject.render();
        }
        popMatrix();

        // sphere
        pushMatrix();
        fill(100, 20, 120);
        translate(cam.position.x + 100, cam.position.y, cam.position.z);
        sphere(5);
        popMatrix();

        int frameStart = millis();
        // flamethrower physics
        flameThrower.physics(0.015f);
        int physicsEnd = millis();
        // flamethrower rendering
        flameThrower.render();
        int frameEnd = millis();
        // text overlay
        surface.setTitle("Processing"
                + " FPS: " + round(frameRate)
                + " Phy: " + round(physicsEnd - frameStart) + "ms"
                + " Ren: " + round(frameEnd - physicsEnd) + "ms"
                + " #par: " + flameThrower.fireParticleSystem.particles.size()
                + " #genrate: " + flameThrower.fireParticleSystem.generationRate
        );
    }

    public void keyPressed(KeyEvent event) {
        if (event.getKey() == '+') {
            flameThrower.fireParticleSystem.incrementGenRate(10);
        }
        if (event.getKey() == '-') {
            flameThrower.fireParticleSystem.decrementGenRate(10);
        }
    }

    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[]{"FireSimulation"};
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }
}
