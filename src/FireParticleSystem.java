import processing.core.PApplet;
import processing.core.PImage;

import java.util.*;


public class FireParticleSystem {
    // window
    final PApplet parent;
    // pose
    final Vector3D origin;
    final Vector3D aim;
    // flow
    final int generationRate;
    final int lifespan;
    final int maxParticles;
    Map<Long, FireParticle> particles = new HashMap<>();
    private List<Long> deadParticleIndices = new ArrayList<>();
    private Long newParticleId = 0L;
    // texture
    static List<String> textureFiles = new ArrayList<>();
    PImage texture;

    static {
        textureFiles.add("fire1.jpg");
        textureFiles.add("fire2.jpg");
        textureFiles.add("fire3.jpg");
    }

    FireParticleSystem(PApplet parent, Vector3D origin, Vector3D aim, int generationRate, int lifespan, int maxParticles) {
        this.parent = parent;
        this.origin = origin;
        this.aim = aim;
        this.generationRate = generationRate;
        this.lifespan = lifespan;
        this.maxParticles = maxParticles;
        Collections.shuffle(textureFiles);
        this.texture = parent.loadImage(textureFiles.get(0));
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
                    lifespan,
                    texture
            ));
            newParticleId++;
        }
        // remove dead particles, update states of live particles
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
