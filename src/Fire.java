import processing.core.PApplet;
import processing.core.PShape;
import queasycam.QueasyCam;

class FlameThrower {
    PApplet parent;
    PShape object;
    FireParticleSystem fireParticleSystem;

    public FlameThrower(PApplet parent, Vec3 origin, Vec3 aim, int generationRate, int lifeSpan, int maxParticles, String objFile) {
        this.parent = parent;
        this.object = parent.loadShape(objFile);
        object.scale(10);
        object.rotate(parent.PI, 0, 0, 1);
        object.setStroke(parent.color(255));
        object.setFill(parent.color(0, 0, 128));

        fireParticleSystem = new FireParticleSystem(parent, origin, aim, generationRate, lifeSpan, maxParticles);
    }

    public void physics(float dt) {
        fireParticleSystem.physics(dt);
    }

    public void render() {
        parent.pushMatrix();
        parent.translate(fireParticleSystem.origin.x, fireParticleSystem.origin.y + 10, fireParticleSystem.origin.z + 30);
        parent.fill(0, 0, 255);
        parent.shape(object);
        parent.popMatrix();
        fireParticleSystem.render();
    }

    public void moveOrigin(Vec3 dOrigin) {
        fireParticleSystem.origin = fireParticleSystem.origin.plus(dOrigin);
    }

    public void setOrigin(Vec3 newOrigin) {
        fireParticleSystem.origin = newOrigin;
    }
}

public class Fire extends PApplet {
    final int WIDTH = 1500;
    final int HEIGHT = 1000;
    QueasyCam cam;
    Ground ground;
    PShape tree;
    FlameThrower flameThrower;

    @Override
    public void settings() {
        size(WIDTH, HEIGHT, P3D);
    }

    @Override
    public void setup() {
        surface.setTitle("Processing");
        noStroke();
        // camera
        cam = new QueasyCam(this);
        cam.sensitivity = 1f;
        cam.speed = 2f;
        // ground
        ground = new Ground(this,
                Vec3.of(0, 0, 0), Vec3.of(0, 0, 1), Vec3.of(1, 0, 0),
                1024, 1024,
                loadImage("grass.jpg"));
        // tree
        tree = loadShape("BirchTree_Autumn_1.obj");
        tree.rotate(PI, 0, 0, 1);
        tree.scale(60);
        // flame thrower
        flameThrower = new FlameThrower(this,
                Vec3.of(300, 0, 150), Vec3.of(0, 0, -1),
                100, 200, 35000, "LongPistol.obj");
    }

    @Override
    public void draw() {
        if (keyPressed && keyCode == RIGHT) {
            flameThrower.moveOrigin(Vec3.of(0, 0, 1));
        }
        if (keyPressed && keyCode == LEFT) {
            flameThrower.moveOrigin(Vec3.of(0, 0, -1));
        }
        if (keyPressed && keyCode == UP) {
            flameThrower.moveOrigin(Vec3.of(1, 0, 0));
        }
        if (keyPressed && keyCode == DOWN) {
            flameThrower.moveOrigin(Vec3.of(-1, 0, 0));
        }
//        flameThrower.setOrigin(Vector3D.of(cam.position.x, cam.position.y, cam.position.z));

        // background
        background(220);
        // ground and tree
        pushMatrix();
        translate(300, 100, 0);
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
        );
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
