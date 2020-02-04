import processing.core.PApplet;
import processing.core.PImage;

import processing.core.PShape;
import queasycam.*;

class Ground {
    final PApplet parent;
    final PImage texture;
    final Vector3D iCap;
    final Vector3D jCap;
    final Vector3D center;
    final Vector3D ul;
    final Vector3D ll;
    final Vector3D lr;
    final Vector3D ur;

    Ground(final PApplet parent, final Vector3D center, final Vector3D iCap, final Vector3D jCap, int width, int height, final PImage texture) {
        this.parent = parent;
        this.iCap = iCap;
        this.jCap = jCap;
        this.texture = texture;
        this.center = center;
        ul = center.plus(iCap.scale(width / 2f)).minus(jCap.scale(height / 2f));
        ll = center.minus(iCap.scale(width / 2f)).minus(jCap.scale(height / 2f));
        lr = center.minus(iCap.scale(width / 2f)).plus(jCap.scale(height / 2f));
        ur = center.plus(iCap.scale(width / 2f)).plus(jCap.scale(height / 2f));
    }

    void render() {
        parent.beginShape();
        parent.texture(texture);
        parent.vertex(ul.x, ul.y, ul.z, 0, 0);
        parent.vertex(ll.x, ll.y, ll.z, 0, texture.height);
        parent.vertex(lr.x, lr.y, lr.z, texture.width, texture.height);
        parent.vertex(ur.x, ur.y, ur.z, texture.width, 0);
        parent.endShape();
    }
}

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
                new Vector3D(100, 0, 0), new Vector3D(0, 0, 1), new Vector3D(1, 0, 0),
                1024, 786,
                loadImage("grass2.jpg"));
        tree = loadShape("BirchTree_1.obj");
        tree.rotate(PI, 0, 0, 1);
        tree.scale(50);
    }

    @Override
    public void draw() {
        background(255);
        translate(200, 150, 0);
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
