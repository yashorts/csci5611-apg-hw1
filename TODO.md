# Check in
- [x] checkin

# Central
- [ ] (30) Water simulation
    - [ ] more squished particles
    - [ ] color changes
    - [ ] change water direction using
    - [ ] interact with objects
    - [ ] context
    - [ ] speed
- [x] Decide simulation contexts (water hose & flamethrower)
- [ ] (30) Fire simulation
    - [x] particle with lifespan, stages, generation rate
    - [x] some highly accelerated particles in jet, some randomized jerks in jet
    - [x] small amounts of smog comes from jet
    - [x] smog motion - random jerks and random displacements, increased lifespan of smog particles, increasing size of particles with lifespan
    - [x] gradual stage change to smog
    - [x] spread of fireball, rotating sprites in fireball, black quads in fireball
    - [x] color change with lifespan for all particles
    - [x] point particles, spherical init position and acc,  slight upward acc
    - [x] billowing motion
    - [ ] intelligent creation of particles
    - [ ] polish motion
 
# Other Required Features 
- [x] (5) 3D user-controlled camera (must allow rotation and translation) 
- [ ] (5) Particle-obstacle interactions besides the floor (in at least one simulation) 
    - [ ] collision detection, fired tree, smoke from tree, tree disappear
 
# Rendering 
- [ ] (5) Tails on particles and sparks
- [x] (5) Translucent particles
- [ ] (5) Textured sprites for particles
 
# Performance Benchmarking (cumulative) 
## Measure the speed of the faster of the two required simulations. Feel free test on a fast  computer such as the ones in the basement graphics lab (KH 1-254).  
- [x] (5) Benchmark-1*: 1,000 particles simulated and rendered at over 20 FPS 
- [x] (5) Benchmark-2: 5,000 particles simulated and rendered at over 30 FPS 
- [x] (5) Benchmark-3: 20,000 particles simulated and rendered at over 30 FPS 
- [ ] (10) Benchmark-4: 100,000 particles simulated and rendered at over 30 FPS 
 
# Additional Features
- [x] (5) Write your own vector library (dot & cross prod, addition, multiplication, etc.) 
- [ ] (10) Continuous user interaction with the system (mouse-based for full credit) 
- [ ] (10) Continuous Collision Detection (must show a visible difference) 
- [ ] (10) Simulation-driven audio (only partial points for short, simple sounds) 
- [ ] (10) Multiple interacting particle systems (submit a separate video) 
- [ ] (10) Thread-parallel implementation (must document performance gain)
    - [ ] foreachordered vs foreach
- [ ] (10) SIMD implementation (must document performance gain) 
 
# More things to simulate 
- [ ] (5) Falling Snow (with Perlin noise) 
- [ ] (10) Snow accumulating on models 
- [ ] (35) Galaxy Simulation (particles must attract each other and look nice) 
- [ ] (50) Real-time implementation of the Genesis Device effect  
- [ ] (100) SPH Fluid Simulation 
