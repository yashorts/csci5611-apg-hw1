import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FireParticleSystem {
    final int maxParticles;
    final PApplet parent;
    final Vector3D origin;
    final Vector3D aim;
    final int generationRate;
    final int lifespan;
    Map<Long, FireParticle> particles = new HashMap<>();
    private List<Long> deadParticleIndices = new ArrayList<>();
    private Long newParticleId = 0L;

    FireParticleSystem(PApplet parent, Vector3D origin, Vector3D aim, int generationRate, int lifespan, int maxParticles) {
        this.maxParticles = maxParticles;
        this.parent = parent;
        this.generationRate = generationRate;
        this.origin = origin;
        this.aim = aim;
        this.lifespan = lifespan;
    }

    public void physics(float dt) {
        // add new particles
        for (int i = 0; i < generationRate; ++i) {
            if (particles.size() >= maxParticles) {
                break;
            }
            float theta = parent.random(2 * parent.PI);
            float radius = 1f * (float) Math.sqrt(parent.random(1));
            Vector3D discRandomness = Vector3D.of(radius * Math.cos(theta), radius * Math.sin(theta), 0);
            Vector3D generalVelocity = aim.scale(50);
            Vector3D acc = Vector3D.of(0, 0, 0);
            particles.put(newParticleId, new FireParticle(
                    parent,
                    origin.plus(discRandomness),
                    generalVelocity,
                    acc.plus(discRandomness.scale(2)),
                    lifespan
            ));
            newParticleId++;
        }
        // update states and remove dead particles
        deadParticleIndices.clear();
        for (Map.Entry<Long, FireParticle> p : particles.entrySet()) {
            if (p.getValue().stage == Stage.DEAD) {
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
