import processing.core.PApplet;
import processing.core.PImage;

public class Ground {
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
        parent.fill(0, 128, 0);
//        parent.texture(texture);
        parent.vertex(ul.x, ul.y, ul.z, 0, 0);
        parent.vertex(ll.x, ll.y, ll.z, 0, texture.height);
        parent.vertex(lr.x, lr.y, lr.z, texture.width, texture.height);
        parent.vertex(ur.x, ur.y, ur.z, texture.width, 0);
        parent.endShape();
    }
}

