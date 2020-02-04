import processing.core.PApplet;

enum Stage {
    JET, JET_TO_BALL_OR_SMOG, BALL, SMOG, SMOKE, DEAD
}

public class FireParticle {
    final PApplet parent;
    Vector3D position;
    Vector3D velocity;
    Vector3D acceleration;
    Vector3D color;
    float totalLifeSpan;
    int remainingLifespan;
    Stage stage;

    FireParticle(PApplet parent, Vector3D position, Vector3D velocity, Vector3D acceleration, int remainingLifespan) {
        this.parent = parent;
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.stage = Stage.JET;
        this.remainingLifespan = remainingLifespan;
        this.totalLifeSpan = remainingLifespan;
    }

    public void physics(float dt) {
        switch (stage) {
            case JET:
                position = position.plus(velocity.scale(dt)).plus(Vector3D.unitUniformRandom().scale(0.1f));
                velocity = velocity.plus(acceleration.scale(dt));
                color = gradientColor();
                // very small portion of initial particles turning into smog
                if (parent.random(1) < 0.0001) {
                    velocity = velocity.scale(parent.random(1));
                    stage = Stage.SMOG;
                }
                // jet stage ends after some lifespan
                if (remainingLifespan / totalLifeSpan <= 0.6) {
                    stage = Stage.JET_TO_BALL_OR_SMOG;
                }
                break;
            case JET_TO_BALL_OR_SMOG:
                position = position.plus(velocity.scale(dt)).plus(Vector3D.unitUniformRandom().scale(0.1f));
                velocity = velocity.plus(acceleration.scale(dt));
                color = gradientColor();
                if (parent.random(1) < (0.54 - remainingLifespan / totalLifeSpan)) {
                    // smog particles come out of jet and slow down due to high air resistance
                    velocity = velocity.scale(parent.random(1));
                    // their lifespan is increased to show their effects
                    remainingLifespan += 200;
                    totalLifeSpan += 200;
                    stage = Stage.SMOG;
                } else {
//                    float theta = parent.random(2 * parent.PI);
//                    float radius = 0.5f * (float) Math.sqrt(parent.random(1));
//                    Vector3D coneRandomness = Vector3D.of(radius * Math.cos(theta), radius * Math.sin(theta), 0).minus(Vector3D.of(0, 0, 1)).scale(20);
//                    velocity = velocity.plus(coneRandomness);
                    stage = Stage.BALL;
                }
                break;
            case BALL:
                position = position.plus(velocity.scale(dt)).plus(Vector3D.unitUniformRandom().scale(0.5f));
                velocity = velocity.plus(acceleration.scale(dt));
                acceleration = acceleration.plus(Vector3D.of(parent.random(-5, 5), -0.5, 0));
                color = gradientColor();
                // very small portion of initial particles turning into smog
                if (parent.random(1) < 0.002) {
                    velocity = velocity.scale(parent.random(1));
                    stage = Stage.SMOG;
                }
                break;
            case SMOG:
                position = position.plus(velocity.scale(dt)).plus(Vector3D.unitUniformRandom().scale(0.5f));
                velocity = velocity.plus(acceleration.scale(dt));
                acceleration = acceleration.plus(Vector3D.of(parent.random(-5, 5), -1, 0));
                color = Vector3D.of(parent.random(50));
                break;
            case SMOKE:
                break;
            case DEAD:
                return;
        }
        remainingLifespan -= 1;
        if (remainingLifespan <= 0) {
            stage = Stage.DEAD;
        }
    }

    public void render() {
        float sample = parent.random(1);
        if (stage == Stage.SMOG) {
            if (sample < 0.1) {
                parent.fill(color.x, color.y, color.z);
                parent.stroke(color.x, color.y, color.z);
                parent.pushMatrix();
                parent.translate(position.x, position.y, position.z);
                parent.box(0.75f  * (1 - remainingLifespan / totalLifeSpan) * Math.min(acceleration.abs(), 1));
                parent.popMatrix();
            } else {
                parent.stroke(color.x, color.y, color.z);
                parent.point(position.x, position.y, position.z);
            }
        } else {
            if (sample < 0.005) {
                parent.fill(color.x, color.y, color.z);
                parent.stroke(color.x, color.y, color.z);
                parent.pushMatrix();
                parent.translate(position.x, position.y, position.z);
                parent.box(0.5f);
                parent.popMatrix();
            } else {
                parent.stroke(color.x, color.y, color.z);
                parent.point(position.x, position.y, position.z);
            }
        }
    }

    private Vector3D gradientColor() {
        return Vector3D.of(255, 255 * (remainingLifespan / totalLifeSpan), 255 * Math.max(2 * remainingLifespan / totalLifeSpan - 1, 0));
    }
}
