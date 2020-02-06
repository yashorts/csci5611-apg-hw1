import processing.core.PApplet;

public class CollisionSphere {
    PApplet parent;
    Vec3 center;
    Vec3 color;
    int radius;
    boolean lockedToCamera;

    public CollisionSphere(PApplet parent, Vec3 center, int radius) {
        this.parent = parent;
        this.center = center;
        this.radius = radius;
        this.color = Vec3.of(255);
        this.lockedToCamera = true;
    }

    public void move(Vec3 newCenter) {
        if (lockedToCamera) {
            center = newCenter;
        }
    }

    public void render() {
        parent.pushMatrix();
        parent.noStroke();
        parent.fill(color.x, color.y, color.z);
        parent.translate(center.x, center.y, center.z);
        parent.sphere(radius);
        parent.popMatrix();
    }

    public void hit() {
        if (color.y > 0) {
            color.y -= 0.04;
        }
        if (color.z > 0) {
            color.z -= 0.04;
        }
    }

    public void miss() {
        if (color.y < 255) {
            color.y += 1;
        }
        if (color.z < 255) {
            color.z += 1;
        }
    }
}
