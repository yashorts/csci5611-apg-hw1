import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


public class FireParticleSystem {
    final PApplet parent;
    final int numParticles;
    final ArrayList<FireParticle> fireParticles = new ArrayList<>();
    List<Integer> deadParticles = new ArrayList<>();

    FireParticleSystem(PApplet parent, Vector3D eye, Vector3D shootDir, float generationRate, int numParticles) {
        this.parent = parent;
        this.numParticles = numParticles;
        IntStream.range(0, numParticles).mapToObj(i -> new FireParticle(
                parent,
                eye,
                shootDir.scale(175),
                new Vector3D(),
                20
        )).forEachOrdered(fireParticles::add);
    }

    public void physics() {
        deadParticles.clear();
        IntStream.range(0, fireParticles.size()).forEachOrdered(i -> {
            FireParticle p = fireParticles.get(i);
            if (!p.isAlive) {
                deadParticles.add(i);
                return;
            }
            p.physics(0.05f);
        });
        for (int deadParticlesIndex : deadParticles) {
            fireParticles.remove(deadParticlesIndex);
        }
    }

    public void render() {
        for (FireParticle p : fireParticles) {
            p.render();
        }
    }

}
