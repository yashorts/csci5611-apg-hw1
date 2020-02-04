import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;


public class FireParticleSystem {
    final PApplet parent;
    final int numParticles;
    Map<Integer, FireParticle> particles = new HashMap<>();
    List<Integer> deadParticleIndices = new ArrayList<>();

    FireParticleSystem(PApplet parent, Vector3D eye, Vector3D shootDir, float generationRate, int numParticles) {
        this.parent = parent;
        this.numParticles = numParticles;
        for (int i = 0; i < numParticles; ++i) {
            Vector3D velocity = shootDir.scale(100);
            Vector3D coneRandomness = new Vector3D(0, parent.random(-1, 1), 0).scale(20);
            particles.put(i, new FireParticle(
                    parent,
                    eye,
                    velocity.plus(coneRandomness),
                    new Vector3D(),
                    50
            ));
        }
    }

    public void physics() {
        deadParticleIndices.clear();
        for (Map.Entry<Integer, FireParticle> p : particles.entrySet()) {
            if (!p.getValue().isAlive) {
                deadParticleIndices.add(p.getKey());
                continue;
            }
            p.getValue().physics(0.05f);
        }
        for (int deadParticlesIndex : deadParticleIndices) {
            particles.remove(deadParticlesIndex);
        }
    }

    public void render() {
        for (Map.Entry<Integer, FireParticle> p : particles.entrySet()) {
            p.getValue().render();
        }
    }

}
