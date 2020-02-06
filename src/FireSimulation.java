import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;
import processing.core.PVector;
import processing.event.KeyEvent;
import queasycam.QueasyCam;

import java.util.ArrayList;
import java.util.List;

public class FireSimulation extends PApplet {
    private static final int MAX_PARTICLES = 20100;
    private static final int GENERATION_RATE = 100;
    private static final int LIFE_SPAN = 200;
    private static final int COLLISION_SPHERE_RADIUS = 10;
    private static final int CONTINUOUS_COLLISION_WALL_Z = -300;
    private static final float CONTINUOUS_COLLISION_WALL_THICKNESS = 0.5f;
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 700;
    QueasyCam cam;
    Ground ground;
    List<StaticGroundObject> staticGroundObjects = new ArrayList<>();
    FlameThrower flameThrower;
    static CollisionSphere collisionSphere;
    static ContinuousCollisionWall continuousCollisionWall;
    Minim minim;
    static AudioPlayer flameSoundPlayer;
    private static final float MAX_SOUND_DB = 15;
    private static final float MIN_SOUND_DB = -40;

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
                GENERATION_RATE, LIFE_SPAN, MAX_PARTICLES,
                "14074_WWII_Soldier_with_Flamethrower_v1_l1.obj");
        // collision sphere
        collisionSphere = new CollisionSphere(this, Vec3.of(-1000, -1000, 1000), COLLISION_SPHERE_RADIUS);
        // continuous colliusion wall
        continuousCollisionWall = new ContinuousCollisionWall(this,
                Vec3.of(0, -200, CONTINUOUS_COLLISION_WALL_Z), CONTINUOUS_COLLISION_WALL_THICKNESS,
                Vec3.of(0, -1, 0), Vec3.of(1, 0, 0),
                400, 1024,
                loadImage("wall.jpg"));
        // sound
        minim = new Minim(this);
        flameSoundPlayer = minim.loadFile("flame.mp3", 2048);
        flameSoundPlayer.loop();
        flameSoundPlayer.shiftGain(flameSoundPlayer.getGain(), flameThrower.fireParticleSystem.normalizedRate() * (MAX_SOUND_DB - MIN_SOUND_DB) + MIN_SOUND_DB, 100);
    }

    public void draw() {
        // flame thrower movement
        if (keyPressed && keyCode == DOWN) {
            flameThrower.moveOrigin(Vec3.of(0, 0, 1));
        }
        if (keyPressed && keyCode == UP) {
            flameThrower.moveOrigin(Vec3.of(0, 0, -1));
        }
//        if (keyPressed && keyCode == RIGHT) {
//            flameThrower.moveOrigin(Vec3.of(0.5, 0, 0));
//        }
//        if (keyPressed && keyCode == LEFT) {
//            flameThrower.moveOrigin(Vec3.of(-0.5, 0, 0));
//        }
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
        // continuous collision wall
        continuousCollisionWall.render();
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
        FireSimulation.collisionSphere.miss();
    }

    public void keyPressed(KeyEvent event) {
        // flame control
        if (event.getKey() == '+') {
            float normalizedRate = flameThrower.fireParticleSystem.incrementGenRate(10);
            flameSoundPlayer.shiftGain(flameSoundPlayer.getGain(), normalizedRate * (MAX_SOUND_DB - MIN_SOUND_DB) + MIN_SOUND_DB, 100);
        }
        if (event.getKey() == '-') {
            float normalizedRate = flameThrower.fireParticleSystem.decrementGenRate(10);
            flameSoundPlayer.shiftGain(flameSoundPlayer.getGain(), normalizedRate * (MAX_SOUND_DB - MIN_SOUND_DB) + MIN_SOUND_DB, 100);
        }
        // sphere control
        if (event.getKey() == 'l') {
            collisionSphere.lockedToCamera = !collisionSphere.lockedToCamera;
        }
        // wall control
        if (event.getKey() == 'i') {
            continuousCollisionWall.mode = WallCollisionMode.NO_COLLISION;
        }
        if (event.getKey() == 'o') {
            continuousCollisionWall.mode = WallCollisionMode.DISCRETE_COLLISION;
        }
        if (event.getKey() == 'p') {
            continuousCollisionWall.mode = WallCollisionMode.CONTINUOUS_COLLISION;
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
