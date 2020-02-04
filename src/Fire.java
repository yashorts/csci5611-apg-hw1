import processing.core.PApplet;
import processing.core.PShape;
import queasycam.QueasyCam;

public class Fire extends PApplet {
    final int WIDTH = 1500;
    final int HEIGHT = 1000;
    QueasyCam cam;
    Ground ground;
    PShape tree;
    FireParticleSystem ps;

    @Override
    public void settings() {
        size(WIDTH, HEIGHT, P3D);
    }

    @Override
    public void setup() {
        surface.setTitle("Processing");
        noStroke();
        ps = new FireParticleSystem(this, 10000);
        cam = new QueasyCam(this);
        cam.sensitivity = 0.5f;
        cam.speed = 1f;
        ground = new Ground(this,
                new Vector3D(0, 0, 0), new Vector3D(0, 0, 1), new Vector3D(1, 0, 0),
                1024, 786,
                loadImage("grass2.jpg"));
        tree = loadShape("BirchTree_1.obj");
        tree.rotate(PI, 0, 0, 1);
        tree.scale(50);
    }

    @Override
    public void draw() {
        background(255);
        translate(300, 100, 0);
        ground.render();
        shape(tree);

        int frameStart = millis();
        // physics
        int physicsEnd = millis();
        // rendering
        int frameEnd = millis();
        // text overlay
        surface.setTitle("Processing"
                + " FPS: " + round(frameRate)
                + " Phy: " + round(physicsEnd - frameStart) + "ms"
                + " Ren: " + round(frameEnd - physicsEnd) + "ms");
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
