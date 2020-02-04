import processing.core.PApplet;
import processing.core.PShape;

public class FlameThrower {
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

