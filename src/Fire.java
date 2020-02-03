import processing.core.PApplet;
import processing.core.PShape;

import queasycam.*;

public class Fire extends PApplet {
    final int WIDTH = 1000;
    final int HEIGHT = 700;
    QueasyCam cam;
    FireParticleSystem ps;
    PShape tree;

    @Override
    public void settings() {
        size(WIDTH, HEIGHT, P3D);
    }

    @Override
    public void setup() {
        ps = new FireParticleSystem(this, 1);
        noStroke();
        cam = new QueasyCam(this);
        surface.setTitle("Processing");
        tree = loadShape("BirchTree_1.obj");
        tree.rotate(PI, 0.0f, 0.0f, 1.0f);
    }

    @Override
    public void draw() {
        background(255);
        lights();

        int frameStart = millis();
        // physics
        ps.update();
        int physicsEnd = millis();
        // rendering
        background(255);
        lights();
        translate(100, 20, 0);
        scale(20);
        shape(tree);
        translate(0, 0, 10);
        shape(tree);
        translate(10, 0, 0);
        shape(tree);
//        fill(255, 0, 0);
//        ps.render();
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
