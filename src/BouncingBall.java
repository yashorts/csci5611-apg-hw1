import processing.core.PApplet;
import processing.core.PImage;

public class BouncingBall extends PApplet {

// Created for CSCI 5611
// Here is a simple processing program that demonstrates the central math used in the check-in to create a bouncing ball.
// The ball is integrated with basic Eulerian integration.
// The ball is subject to a simple PDE of constant downward ay (by default, down is the positive y direction).
// If you are new to processing, you can find an excellent tutorial that will quickly
// introduce the key features here: https://processing.org/tutorials/p3d/

    String projectTitle = "Bouncing Ball";

    // Animation Principle: Store object & world state in external variables that are used by both the drawing code and simulation code.
    class Ball {
        float sx;
        float vx;
        float ax;

        float sy;
        float vy;
        float ay;

        float radius;
        float squareBoundary;

        Ball(float squareBoundary) {
            this.squareBoundary = squareBoundary;
            sy = squareBoundary / 2;
            vy = 80;
            ay = 9.8f;

            sx = squareBoundary / 2;
            vx = 50;
            ax = 0;
            radius = 40;
        }

        // Animation Principle: Separate Physical Update
        public void update(float dt) {

            // Eulerian Numerical Integration
            // Question: Why update sy before vy? Does it matter?
            sx = sx + vx * dt;
            vx = vx + ax * dt;

            sy = sy + vy * dt;
            vy = vy + ay * dt;

            if (sx + radius > squareBoundary) {
                // Robust collision check
                sx = squareBoundary - radius;
                // Coefficient of restitution (don't bounce back all the way)
                vx *= -.95f;
            }

            if (sx - radius < 0) {
                // Robust collision check
                sx = radius;
                // Coefficient of restitution (don't bounce back all the way)
                vx *= -.95f;
            }

            // Collision Code (update vy if we hit the squareBoundary)
            if (sy + radius > squareBoundary) {
                // Robust collision check
                sy = squareBoundary - radius;
                // Coefficient of restitution (don't bounce back all the way)
                vy *= -.95f;
            }

            if (sy - radius < 0) {
                // Robust collision check
                sy = radius;
                // Coefficient of restitution (don't bounce back all the way)
                vy *= -.95f;
            }

            /* radius = max(10, 80 * sy / squareBoundary); */
        }

        public float normalizedPositionX() {
            return sx / sqrt(sx * sx + sy * sy);
        }

        public float normalizedPositionY() {
            return sy / sqrt(sx * sx + sy * sy);
        }

    }

    Ball ball = new Ball(1000);

    // Creates a 1000 x 1000 window for 3D graphics
    public void setup() {

        noStroke(); // Question: What does this do?
        img = loadImage("backdrop.jpg");
    }

    PImage img;

    // Animation Principle: Separate Draw Code
    public void drawScene() {
        /* background(img); */
        background(255, 255, 255);
        fill(255 * ball.sy / 1000, 255 * (1 - ball.sy / 1000), 255 * (1 - ball.sy / 1000));
        lights();
        translate(ball.sx, ball.sy);
        sphere(ball.radius);
    }

    // Main function which is called every timestep.
// Here we compute the new state and draw the scene.
// Additionally, we also compute some timing performance numbers.
    public void draw() {
        float startFrame = millis(); // Time how long various components are taking

        // Compute the physics update
        ball.update(0.15f); // Question: Should this be a fixed number?
        float endPhysics = millis();

        // Draw the scene
        drawScene();
        float endFrame = millis();

        String runtimeReport = "Frame: " + str(endFrame - startFrame) + "ms," +
                " Physics: " + str(endPhysics - startFrame) + "ms," +
                " FPS: " + str(round(frameRate));
        surface.setTitle(projectTitle + " - " + runtimeReport);
    }

    public void settings() {
        size(1000, 1000, P3D);
    }

    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[]{"BouncingBall"};
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }
}
