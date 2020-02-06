import processing.core.PApplet;

public class CollisionSphere {
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
