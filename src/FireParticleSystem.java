import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FireParticleSystem {
    final int MAX_PARTICLES;
    final PApplet parent;
    final int generationRate;
    final Vector3D source;
    final Vector3D shootDir;
    Map<Long, FireParticle> particles = new HashMap<>();
    List<Long> deadParticleIndices = new ArrayList<>();
    Long newParticleId = 0L;
    final int lifespan;

    FireParticleSystem(PApplet parent, Vector3D source, Vector3D shootDir, int generationRate, int lifespan, int MAX_PARTICLES) {
        this.parent = parent;
        this.MAX_PARTICLES = MAX_PARTICLES;
        this.generationRate = generationRate;
        this.source = source;
        this.shootDir = shootDir;
        this.lifespan = lifespan;
    }

    public void physics(float dt) {
        // add new particles
        for (int i = 0; i < generationRate; ++i) {
            if (particles.size() >= MAX_PARTICLES) {
                break;
            }
            Vector3D generalVelocity = shootDir.scale(100);
            Vector3D coneRandomness = new Vector3D(0, parent.random(-1, 1), 0).scale(20);
            Vector3D vel = generalVelocity.plus(coneRandomness);
            Vector3D acc = new Vector3D(0, -20, 0);
            particles.put(newParticleId, new FireParticle(
                    parent,
                    source,
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
