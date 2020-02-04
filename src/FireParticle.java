import processing.core.PApplet;

public class FireParticle {
    final PApplet parent;
    Vector3D position;
    Vector3D velocity;
    Vector3D acceleration;
    int lifespan;
    final float initialLifeSpan;
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
        position = position.plus(velocity.scale(dt));
        velocity = velocity.plus(acceleration.scale(dt));
        lifespan -= 1;
        if (lifespan < 0) {
            isAlive = false;
        }
    }

    public void render() {
        parent.pushMatrix();
        parent.fill(255, 255 * (lifespan / initialLifeSpan), 255 * (lifespan / initialLifeSpan));
        parent.translate(position.x, position.y, position.z);
        parent.box(1);
        parent.popMatrix();
    }

}
