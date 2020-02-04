import processing.core.PApplet;

public class FireParticle {
    final PApplet parent;
    Vector3D position;
    Vector3D velocity;
    Vector3D acceleration;
    final float initialLifeSpan;
    int lifespan;
    boolean isAlive;

    FireParticle(PApplet parent, Vector3D position, Vector3D velocity, Vector3D acceleration, int lifespan) {
        this.parent = parent;
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.isAlive = true;
        this.lifespan = lifespan;
        this.initialLifeSpan = lifespan;
    }

    public void physics(float dt) {
        position = position.plus(velocity.scale(dt)).plus(Vector3D.randomUnit().scale(0.2f));
        velocity = velocity.plus(acceleration.scale(dt));
        lifespan -= 1;
        if (lifespan < 0) {
            isAlive = false;
        }
    }

    public void render() {
        float sample = parent.random(1);
        parent.fill(255, 255 * (lifespan / initialLifeSpan), 255 * PApplet.max(2 * lifespan / initialLifeSpan - 1, 0));
        if (sample < 0.0001) {
            parent.pushMatrix();
            parent.translate(position.x, position.y, position.z);
            parent.sphere(0.5f);
            parent.popMatrix();
        } else if (sample < 0.005) {
            parent.pushMatrix();
            parent.translate(position.x, position.y, position.z);
            parent.box(0.5f);
            parent.popMatrix();
        } else {
            parent.stroke(255, 255 * (lifespan / initialLifeSpan), 255 * PApplet.max(2 * lifespan / initialLifeSpan - 1, 0));
            parent.point(position.x, position.y, position.z);
        }
    }

}
