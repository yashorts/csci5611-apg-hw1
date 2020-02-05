import processing.core.PApplet;
import processing.core.PShape;
import processing.event.KeyEvent;
import queasycam.QueasyCam;

public class Fire extends PApplet {
    final int WIDTH = 1000;
    final int HEIGHT = 700;
    QueasyCam cam;
    Ground ground;
    PShape tree;
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
                2048, 2048,
                loadImage("grass.jpg"));
        // tree
        tree = loadShape("BirchTree_Autumn_1.obj");
        tree.rotate(PI, 0, 0, 1);
        tree.scale(60);
        // flame thrower
        flameThrower = new FlameThrower(this,
                Vec3.of(300, 0, 150), Vec3.of(0, 0, -1),
                100, 200, 20100, "Sniper rifle.obj");
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
        // ground and tree
        pushMatrix();
        translate(300, 100, -40);
        ground.render();
        shape(tree);
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

    public void keyPressed(KeyEvent event){
        if (event.getKey() == '+') {
            flameThrower.fireParticleSystem.incrementGenRate(10);
        }
        if (event.getKey() == '-') {
            flameThrower.fireParticleSystem.decrementGenRate(10);
        }
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
