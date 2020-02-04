import processing.core.PApplet;
import processing.core.PImage;

enum Stage {
    JET, JET_TO_BALL_OR_SMOG, BALL, SMOG, SMOKE, DEAD
}

public class FireParticle {
    final PApplet parent;
    Vec3 position;
    Vec3 velocity;
    Vec3 acceleration;
    Vec3 color;
    float totalLifeSpan;
    int remainingLifespan;
    Stage stage;
    final PImage texture;

    FireParticle(PApplet parent, Vec3 position, Vec3 velocity, Vec3 acceleration, int remainingLifespan, PImage texture) {
        this.parent = parent;
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.stage = Stage.JET;
        this.remainingLifespan = remainingLifespan;
        this.totalLifeSpan = remainingLifespan;
        this.texture = texture;
    }

    public void physics(float dt) {
        switch (stage) {
            case JET:
                position = position.plus(velocity.scale(dt)).plus(Vec3.uniformRandomInUnitSphere().scale(0.1f));
                velocity = velocity.plus(acceleration.scale(dt));
                color = gradientColor();
                // very small portion of initial particles turning into smog
                if (parent.random(1) < 0.0001) {
                    // smog particles come out of jet and slow down due to high air resistance
                    velocity = velocity.scale(parent.random(1));
                    // their lifespan is increased to show their effects
                    remainingLifespan += 200;
                    totalLifeSpan += 200;
                    stage = Stage.SMOG;
                }
                // jet stage ends after some lifespan
                if (remainingLifespan / totalLifeSpan <= 0.6) {
                    stage = Stage.JET_TO_BALL_OR_SMOG;
                }
                break;
            case JET_TO_BALL_OR_SMOG:
                position = position.plus(velocity.scale(dt)).plus(Vec3.uniformRandomInUnitSphere().scale(0.1f));
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
                position = position.plus(velocity.scale(dt)).plus(Vec3.uniformRandomInUnitSphere().scale(0.5f));
                velocity = velocity.plus(acceleration.scale(dt));
                acceleration = acceleration.plus(Vec3.of(parent.random(-5, 5), -0.5, 0));
                color = gradientColor();
                // small portion of ball particles turning into smog
                if (parent.random(1) < 0.002) {
                    // smog particles come out of jet and slow down due to high air resistance
                    velocity = velocity.scale(parent.random(1));
                    // their lifespan is increased to show their effects
                    remainingLifespan += 200;
                    totalLifeSpan += 200;
                    stage = Stage.SMOG;
                }
                break;
            case SMOG:
                position = position.plus(velocity.scale(dt)).plus(Vec3.uniformRandomInUnitSphere().scale(0.5f));
                velocity = velocity.plus(acceleration.scale(dt));
                acceleration = acceleration.plus(Vec3.of(parent.random(-5, 5), -1, 0));
                color = Vec3.of(parent.random(100, 200));
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
                parent.box(0.75f * (1 - remainingLifespan / totalLifeSpan) * Math.min(acceleration.abs(), 1));
                parent.popMatrix();
            } else {
                parent.stroke(color.x, color.y, color.z);
                parent.point(position.x, position.y, position.z);
            }
        } else if (stage == Stage.BALL) {
            if (sample < 0.1) {
                parent.fill(color.x, color.y, color.z);
                parent.stroke(color.x, color.y, color.z);
                parent.pushMatrix();
                parent.translate(position.x, position.y, position.z);
                parent.box(1f * (1 - remainingLifespan / totalLifeSpan) * Math.min(acceleration.abs(), 1));
                parent.popMatrix();
            } else {
                parent.stroke(color.x, color.y, color.z);
                parent.point(position.x, position.y, position.z);
            }
        } else {
            if (sample < 0.005) {
                parent.fill(color.x, color.y, color.z, 0.8f);
                parent.stroke(color.x, color.y, color.z, 0.8f);
                parent.pushMatrix();
                parent.translate(position.x, position.y, position.z);

                parent.rotate(remainingLifespan / totalLifeSpan * parent.PI * 20, 0, 1, 0);
                parent.beginShape();
                parent.texture(texture);
                float sideLen = 0.5f * (1.5f - remainingLifespan / totalLifeSpan) * Math.min(acceleration.abs(), 1);
                parent.vertex(-sideLen, -sideLen, 0, 0, 0);
                parent.vertex(sideLen, -sideLen, 0, texture.width, 0);
                parent.vertex(sideLen, sideLen, 0, texture.width, texture.height);
                parent.vertex(-sideLen, sideLen, 0, 0, texture.height);
                parent.endShape();

                parent.popMatrix();
            } else {
                parent.stroke(color.x, color.y, color.z);
                parent.point(position.x, position.y, position.z);
            }
        }
    }

    private Vec3 gradientColor() {
        return Vec3.of(255, 255 * (remainingLifespan / totalLifeSpan), 255 * Math.max(2 * remainingLifespan / totalLifeSpan - 1, 0));
    }
}
