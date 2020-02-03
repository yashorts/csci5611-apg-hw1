import processing.core.PApplet;

public class FireParticle {
    final PApplet parent;
    Vector3D position;
    Vector3D velocity;
    Vector3D acceleration;
    boolean isAlive;

    FireParticle(PApplet parent, Vector3D position, Vector3D velocity, Vector3D acceleration) {
        this.parent = parent;
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.isAlive = true;
    }

    public void update(float dt) {
        position = position.plus(velocity.scale(dt));
        velocity = velocity.plus(acceleration.scale(dt));

        if (position.x > 1000) {
            isAlive = false;
        }

        if (position.x < 0) {
            isAlive = false;
        }

        if (position.y > 1000) {
            position.y = 1000;
            velocity.y = -velocity.y * 0.4f;
        }

        if (position.y < 0) {
            isAlive = false;
        }

    }

    public void render() {
        if (isAlive) {
            parent.pushMatrix();
            parent.translate(position.x, position.y, position.z);
            parent.box(4);
            parent.popMatrix();
        }
    }

}
