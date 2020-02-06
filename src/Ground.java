import processing.core.PApplet;
import processing.core.PImage;

public class Ground {
    final PApplet parent;
    final PImage texture;
    final Vec3 iCap;
    final Vec3 jCap;
    final Vec3 center;
    final Vec3 ul;
    final Vec3 ll;
    final Vec3 lr;
    final Vec3 ur;

    Ground(final PApplet parent, final Vec3 center, final Vec3 iCap, final Vec3 jCap, int width, int height, final PImage texture) {
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

