import processing.core.PApplet;

import java.util.ArrayList;


public class FireParticleSystem {
    final PApplet parent;
    final int numParticles;
    final ArrayList<FireParticle> fireParticles;

    FireParticleSystem(PApplet parent, int numParticles) {
        this.parent = parent;
        this.numParticles = numParticles;
        fireParticles = new ArrayList<FireParticle>();
        for (int i = 0; i < numParticles; ++i) {
            fireParticles.add(new FireParticle(
                    parent,
                    new Vector3D(200 + 100 * parent.random(1), 200 + 10 * parent.random(1), 0),
                    new Vector3D(10 + 10 * parent.random(1), 10 * parent.random(1), 0),
                    new Vector3D(0, 10, 0)
            ));
        }
    }

    public void update() {
        for (FireParticle p : fireParticles) {
            p.update(0.05f);
        }
    }

    public void render() {
        for (FireParticle p : fireParticles) {
            p.render();
        }
    }

}
