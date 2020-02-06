import processing.core.PApplet;
import processing.core.PShape;

public class StaticGroundObject {
    PApplet parent;
    PShape shape;
    Vec3 position;

    public StaticGroundObject(PApplet parent, PShape shape, Vec3 position) {
        this.parent = parent;
        this.shape = shape;
        this.position = position;
        shape.rotate(parent.PI, 0, 0, 1);
        shape.scale(60);
    }

    public void render() {
        parent.pushMatrix();
        parent.translate(position.x, position.y, position.z);
        parent.shape(shape);
        parent.popMatrix();
    }
}
