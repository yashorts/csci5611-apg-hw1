import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FireParticleSystem {
    final PApplet parent;
    final int MAX_PARTICLES;
    final Vector3D sourcePos;
    final Vector3D shootDir;
    final int generationRate;
    final int lifespan;
    Map<Long, FireParticle> particles = new HashMap<>();
    private List<Long> deadParticleIndices = new ArrayList<>();
    private Long newParticleId = 0L;

    FireParticleSystem(PApplet parent, Vector3D sourcePos, Vector3D shootDir, int generationRate, int lifespan, int MAX_PARTICLES) {
        this.parent = parent;
        this.MAX_PARTICLES = MAX_PARTICLES;
        this.generationRate = generationRate;
        this.sourcePos = sourcePos;
        this.shootDir = shootDir;
        this.lifespan = lifespan;
    }

    public void physics(float dt) {
        // add new particles
        for (int i = 0; i < generationRate; ++i) {
            if (particles.size() >= MAX_PARTICLES) {
                break;
            }
            Vector3D generalVelocity = shootDir.scale(50);
            float theta = parent.random(2 * parent.PI);
            float radius = 1 * (float) Math.sqrt(parent.random(1));
            Vector3D coneRandomness = Vector3D.of(radius * Math.cos(theta), radius * Math.sin(theta), 0).minus(Vector3D.of(0, 0, 1)).scale(20);
            Vector3D vel = generalVelocity.plus(coneRandomness);
            Vector3D acc = Vector3D.of(0, -20, 0);
            particles.put(newParticleId, new FireParticle(
                    parent,
                    sourcePos,
                    vel,
                    acc,
                    lifespan
            ));
            newParticleId++;
        }
        // update states and remove dead particles
        deadParticleIndices.clear();
        for (Map.Entry<Long, FireParticle> p : particles.entrySet()) {
            if (!p.getValue().isAlive) {
                deadParticleIndices.add(p.getKey());
                continue;
            }
            p.getValue().physics(dt);
        }
        for (long deadParticlesIndex : deadParticleIndices) {
            particles.remove(deadParticlesIndex);
        }
    }

    public void render() {
        for (Map.Entry<Long, FireParticle> p : particles.entrySet()) {
            p.getValue().render();
        }
    }

}
