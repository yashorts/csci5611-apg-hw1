import processing.core.PApplet;
import processing.core.PVector;
import processing.event.KeyEvent;
import queasycam.QueasyCam;

import java.util.ArrayList;
import java.util.List;

class CollisionSphere {
    PApplet parent;
    Vec3 center;
    int radius;

    public CollisionSphere(PApplet parent, Vec3 center, int radius) {
        this.parent = parent;
        this.center = center;
        this.radius = radius;
    }

    public void move(Vec3 newCenter) {
        center = newCenter;
    }

    public void render() {
        parent.pushMatrix();
        parent.noStroke();
        parent.fill(255);
        parent.translate(center.x, center.y, center.z);
        parent.sphere(radius);
        parent.popMatrix();
    }
}

public class FireSimulation extends PApplet {
    final int WIDTH = 1000;
    final int HEIGHT = 700;
    QueasyCam cam;
    Ground ground;
    List<StaticGroundObject> staticGroundObjects = new ArrayList<>();
    FlameThrower flameThrower;
    CollisionSphere collisionSphere;

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
            staticGroundObjects.add(new StaticGroundObject(this, loadShape("Rock_" + (int) random(1, 8) + ".obj"), Vec3.of(random(-500, 500), 100, random(-500, 500))));
        }
        // flame thrower
        flameThrower = new FlameThrower(this,
                Vec3.of(200, 0, 50),
                Vec3.of(0, 0, -1),
                100, 200, 20100,
                "14074_WWII_Soldier_with_Flamethrower_v1_l1.obj");
        // collision sphere
        PVector aim = cam.getAim(200);
        collisionSphere = new CollisionSphere(this, Vec3.of(aim.x, aim.y, aim.z), 5);
    }

    public void draw() {
        // flame thrower movement
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
        // background
        background(85, 156, 185);
        // ground
        ground.render();
        // rocks and trees
        for (StaticGroundObject staticGroundObject : staticGroundObjects) {
            staticGroundObject.render();
        }
        // collision sphere
        PVector aim = cam.getAim(200);
        collisionSphere.move(Vec3.of(aim.x, aim.y, aim.z));
        collisionSphere.render();
        // flamethrower
        int frameStart = millis();
        flameThrower.physics(0.015f);
        int physicsEnd = millis();
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
