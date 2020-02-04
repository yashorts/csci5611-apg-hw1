import processing.core.PApplet;

import java.util.ArrayList;


public class FireParticleSystem {
    final PApplet parent;
    final int numParticles;
    final ArrayList<FireParticle> fireParticles;

    FireParticleSystem(PApplet parent, Vector3D eye, Vector3D shootDir, float generationRate, int numParticles) {
        this.parent = parent;
        this.numParticles = numParticles;
        fireParticles = new ArrayList<>();
        for (int i = 0; i < numParticles; ++i) {
            fireParticles.add(new FireParticle(
                    parent,
                    eye,
                    shootDir.scale(10),
                    new Vector3D(),
                    1000
            ));
        }
    }

    public void physics() {
        for (FireParticle p : fireParticles) {
            p.physics(0.05f);
        }
    }

    public void render() {
        for (FireParticle p : fireParticles) {
            if (p.isAlive) {
                p.render();
            }
        }
    }

}
