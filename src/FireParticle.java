import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

enum Stage {
    INIT, JET, JET_TO_BALL_OR_SMOKE, BALL, SMOKE, SPARK, DEAD
}

enum Shape {
    POINT, QUAD, SPRITE
}

public class FireParticle {
    final PApplet parent;
    Vec3 position;
    List<Vec3> history = new ArrayList<>();
    Vec3 velocity;
    Vec3 initialShootDir;
    Vec3 initialVelocity;
    Vec3 acceleration;
    Vec3 color;
    float totalLifeSpan;
    int remainingLifespan;
    Stage stage;
    Shape shape;
    final PImage fireTexture;
    final PImage smokeTexture;

    FireParticle(PApplet parent, Vec3 position, Vec3 velocity, Vec3 acceleration, int remainingLifespan, PImage fireTexture, PImage smokeTexture) {
        this.parent = parent;
        this.position = position;
        this.velocity = velocity;
        this.initialVelocity = velocity;
        this.initialShootDir = velocity.unit();
        this.acceleration = acceleration;
        this.stage = Stage.INIT;
        this.shape = Shape.POINT;
        this.remainingLifespan = remainingLifespan;
        this.totalLifeSpan = remainingLifespan;
        this.fireTexture = fireTexture;
        this.smokeTexture = smokeTexture;
        this.color = Vec3.of(0, 255, 0);
    }

    public void physics(float dt) {
        float prevPositionZ = 0;
        if (FireSimulation.continuousCollisionWall.mode == WallCollisionMode.CONTINUOUS_COLLISION) {
            prevPositionZ = position.z;
        }

        switch (stage) {
            case INIT:
                if (parent.random(1) < 0.005) {
                    shape = Shape.QUAD;
                }
                if (parent.random(1) < 0.001) {
                    velocity = Vec3.uniformRandomInUnitSphere().scale(10);
                    remainingLifespan = (int) (totalLifeSpan * 0.5f);
                    stage = Stage.SPARK;
                    break;
                }
                stage = Stage.JET;
                color = gradientColor();
                break;
            case JET:
                position = position.plus(velocity.scale(dt)).plus(Vec3.uniformRandomInUnitSphere().scale(0.1f));
                velocity = velocity.plus(acceleration.scale(dt));
                if (parent.random(1) < 0.05) {
                    acceleration = Vec3.uniformRandomInUnitSphere().scale(5f).plus(initialShootDir.scale(10));
                }
                if (parent.random(1) < 0.001) {
                    acceleration = acceleration.plus(initialShootDir.scale(50));
                }
                color = gradientColor();
                // very small portion of initial particles turning into smoke
                if (parent.random(1) < 0.00005) {
                    changeStageToSmoke();
                }
                // jet stage ends after some lifespan
                if (remainingLifespan / totalLifeSpan <= 0.6) {
                    stage = Stage.JET_TO_BALL_OR_SMOKE;
                }
                break;
            case JET_TO_BALL_OR_SMOKE:
                position = position.plus(velocity.scale(dt)).plus(Vec3.uniformRandomInUnitSphere().scale(0.1f));
                velocity = velocity.plus(acceleration.scale(dt));
                color = gradientColor();
                if (parent.random(1) < 0.0005) {
                    velocity.y += parent.random(10);
                    velocity.z *= 1.2;
                    acceleration.y = 1;
                    stage = Stage.SPARK;
                    break;
                }
                if (parent.random(1) < (0.3 - remainingLifespan / totalLifeSpan)) {
                    changeStageToSmoke();
                } else {
//                    float theta = parent.random(2 * parent.PI);
//                    float radius = 0.5f * (float) Math.sqrt(parent.random(1));
//                    Vector3D coneRandomness = Vector3D.of(radius * Math.cos(theta), radius * Math.sin(theta), 0).minus(Vector3D.of(0, 0, 1)).scale(20);
//                    velocity = velocity.plus(coneRandomness);
                    stage = Stage.BALL;
                    if (parent.random(1) < 0.1) {
                        shape = Shape.QUAD;
                    }
                }
                break;
            case BALL:
                position = position.plus(velocity.scale(dt)).plus(Vec3.uniformRandomInUnitSphere().scale(0.5f));
                velocity = velocity.plus(acceleration.scale(dt));
                acceleration = acceleration.plus(Vec3.of(parent.random(-5, 5), -0.5, 0));
                if (parent.random(1) < 0.05) {
                    color = Vec3.of(0);
                } else {
                    color = gradientColor();
                }
                // small portion of ball particles turning into smoke
                if (parent.random(1) < 0.02 && remainingLifespan / totalLifeSpan <= 0.2) {
                    changeStageToSmoke();
                    break;
                }
                if (parent.random(1) < 0.0001 && remainingLifespan / totalLifeSpan <= 0.3) {
                    velocity.y += parent.random(10);
                    velocity.z *= 1.2;
                    acceleration.y = 1;
                    stage = Stage.SPARK;
                    break;
                }
                break;
            case SPARK:
                position = position.plus(velocity.scale(dt)).plus(Vec3.uniformRandomInUnitSphere());
                history.add(position);
                velocity = velocity.plus(acceleration.scale(dt));
                break;
            case SMOKE:
                position = position.plus(velocity.scale(dt)).plus(Vec3.uniformRandomInUnitSphere());
                if (remainingLifespan / totalLifeSpan >= 0.3 && remainingLifespan / totalLifeSpan <= 0.4) {
                    velocity.y = -200 * parent.random(1f / (1.2f - remainingLifespan / totalLifeSpan));
                } else {
                    velocity.y = -100 * parent.random(1f / (1.3f - remainingLifespan / totalLifeSpan));
                }
                velocity.x += acceleration.x * dt;
                velocity.z += acceleration.z * dt;
                color = Vec3.of(parent.random(0, 50 + 55 * (1 - remainingLifespan / totalLifeSpan)));
                break;
            case DEAD:
                return;
            default:
                return;
        }
        remainingLifespan -= 1;
        if (remainingLifespan <= 0) {
            stage = Stage.DEAD;
        }

        // collision with the sphere
        Vec3 normal = position.minus(FireSimulation.collisionSphere.center);
        float distFromCenter = normal.abs();
        Vec3 normalizedNormal = normal.unit();
        if (distFromCenter < FireSimulation.collisionSphere.radius) {
            position = position.plus(normal.unit().scale(FireSimulation.collisionSphere.radius - distFromCenter + 2));
            velocity = velocity.minus(normalizedNormal.scale(2 * velocity.dot(normalizedNormal)));
            FireSimulation.collisionSphere.hit();
            if (parent.random(1) < 0.1) {
                remainingLifespan = (int) (totalLifeSpan * parent.random(0.1f, 0.2f));
                stage = Stage.SPARK;
            }
        }

        // collision with the wall
        if (FireSimulation.continuousCollisionWall.mode == WallCollisionMode.DISCRETE_COLLISION) {
            if (position.z < FireSimulation.continuousCollisionWall.center.z &&
                    position.z > FireSimulation.continuousCollisionWall.center.z - FireSimulation.continuousCollisionWall.thickness) {
                position.z = FireSimulation.continuousCollisionWall.center.z + 2;
                velocity.z = -velocity.z;
            }
        } else if (FireSimulation.continuousCollisionWall.mode == WallCollisionMode.CONTINUOUS_COLLISION) {
            if (prevPositionZ >= FireSimulation.continuousCollisionWall.center.z && position.z < FireSimulation.continuousCollisionWall.center.z) {
                position.z = FireSimulation.continuousCollisionWall.center.z + 2;
                velocity.z = -velocity.z;
            }
        }
    }

    public void render() {
        if (stage == Stage.SPARK) {
            renderSpark(0.75f * (1.1f - remainingLifespan / totalLifeSpan), fireTexture, 200);
        }

        if (shape == Shape.QUAD) {
            switch (stage) {
                case JET:
                    renderQuad(0.4f * (1.5f - remainingLifespan / totalLifeSpan) * Math.min(acceleration.abs(), 1), fireTexture, 255);
                case BALL:
                    renderQuad(1f * (1.2f - remainingLifespan / totalLifeSpan) * Math.min(acceleration.abs(), 1), fireTexture, 255);
                    break;
                case SMOKE:
                    renderQuad(1.4f * (1.5f - remainingLifespan / totalLifeSpan), smokeTexture, 200);
                    break;
                default:
                    renderQuad(0.5f * (1.5f - remainingLifespan / totalLifeSpan) * Math.min(acceleration.abs(), 1), fireTexture, 255);
                    break;
            }
        } else if (shape == Shape.SPRITE) {
            switch (stage) {
                case SMOKE:
                    renderSprite(2 * (1.1f - remainingLifespan / totalLifeSpan), smokeTexture, 180);
                    break;
                default:
                    break;
            }
        } else {
            renderPoint();
        }
    }

    private Vec3 gradientColor() {
        return Vec3.of(255, 255 * (remainingLifespan / totalLifeSpan), 255 * Math.max(2 * remainingLifespan / totalLifeSpan - 1, 0));
    }

    private void changeStageToSmoke() {
        // smoke particles come out of jet and slow down due to high air resistance
        velocity = velocity.scale(parent.random(0.2f, 1)).plus(initialShootDir.scale(2.5f * remainingLifespan / totalLifeSpan));
        acceleration = Vec3.of(100 * parent.random(-1, 1), 0, parent.random(-1, 1));
        velocity.x = -acceleration.x;
        // their lifespan is increased to show their effects
        remainingLifespan = 100;
        totalLifeSpan = 100;
        stage = Stage.SMOKE;
        float sample = parent.random(1);
        if (sample < 0.5) {
            shape = Shape.QUAD;
        } else if (sample < 0.6) {
            shape = Shape.SPRITE;
        }
    }

    private void renderSprite(float sideLen, PImage texture, float alpha) {
        parent.pushMatrix();
        parent.translate(position.x, position.y, position.z);
        parent.rotate(remainingLifespan / totalLifeSpan * parent.PI * 5, 0, 1, 0);
        parent.noStroke();
        parent.tint(255, alpha);
        parent.texture(texture);

        parent.beginShape(PConstants.QUADS);
        parent.vertex(-sideLen, -sideLen, 0, 0, 0);
        parent.vertex(sideLen, -sideLen, 0, texture.width, 0);
        parent.vertex(sideLen, sideLen, 0, texture.width, texture.height);
        parent.vertex(-sideLen, sideLen, 0, 0, texture.height);
        parent.endShape();


        parent.popMatrix();
    }

    private void renderQuad(float sideLen, PImage texture, float alpha) {
        parent.pushMatrix();
        parent.translate(position.x, position.y, position.z);
        parent.rotate(remainingLifespan / totalLifeSpan * parent.PI * 5, 0, 1, 0);

        parent.noStroke();
        if (parent.random(1) < 0.1) {
            parent.tint(255, alpha);
            parent.beginShape(PConstants.QUADS);
            parent.texture(texture);
            parent.vertex(-sideLen, -sideLen, 0, 0, 0);
            parent.vertex(sideLen, -sideLen, 0, texture.width, 0);
            parent.vertex(sideLen, sideLen, 0, texture.width, texture.height);
            parent.vertex(-sideLen, sideLen, 0, 0, texture.height);
        } else if (parent.random(1) < 0.8) {
            parent.beginShape(PConstants.TRIANGLE);
            parent.fill(color.x, color.y, color.z, alpha);
            parent.vertex(-sideLen, -sideLen, 0);
            parent.vertex(sideLen, -sideLen, 0);
            parent.vertex(sideLen, sideLen, 0);
        } else {
            parent.beginShape(PConstants.QUADS);
            parent.fill(color.x, color.y, color.z, alpha);
            parent.vertex(-sideLen, -sideLen, 0);
            parent.vertex(sideLen, -sideLen, 0);
            parent.vertex(sideLen, sideLen, 0);
            parent.vertex(-sideLen, sideLen, 0);
        }
        parent.endShape();

        parent.popMatrix();
    }

    private void renderSpark(float sideLen, PImage texture, float alpha) {
        for (int i = 0; i < history.size(); ++i) {
            Vec3 pos = history.get(i);
            parent.pushMatrix();
            parent.translate(pos.x, pos.y, pos.z);
            parent.rotate(remainingLifespan / totalLifeSpan * parent.PI * 5, 0, 1, 0);
            if (i < history.size() * 0.3f) {
                color = Vec3.of(255, 255, 255);
            } else if (i < history.size() * 0.8f) {
                color = Vec3.of(255, 255, 0);
            } else {
                color = Vec3.of(255, 128, 0);
            }

            parent.noStroke();
            parent.beginShape(PConstants.QUADS);
            parent.fill(color.x, color.y, color.z, alpha);
            parent.vertex(-sideLen, -sideLen, 0);
            parent.vertex(sideLen, -sideLen, 0);
            parent.vertex(sideLen, sideLen, 0);
            parent.vertex(-sideLen, sideLen, 0);
            parent.endShape();
            parent.popMatrix();
        }
    }

    private void renderPoint() {
        parent.stroke(color.x, color.y, color.z);
        parent.point(position.x, position.y, position.z);
    }
}
