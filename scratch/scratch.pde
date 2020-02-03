float heroX = 0.0; float heroY = 0.0;
float heroRot = 0.0;
float heroRotIncr = 0.05;
float heroMoveSpeed = 3.0;
float heroSize = 25.0;

// The two back points of the hero's triangle are
// 140 degrees away from the forward facing point. (An
// equilateral triangle's three angles are 360.0 / 3
// = 120.0 degrees apart.)
float heroArrow = radians(140.0);

// Store pressed keys in an array so that simultaneous
// key presses allow diagonal movement. The keyPressed
// function does not register if the sketch doesn't have
// focus (see 'focused' in the reference).
boolean[] pressed = new boolean[256];

void setup() {
  size(386, 256);

  // The default 2D camera doesn't look at the
  // origin (0, 0) in the center like graphing
  // calculators do. Instead (0, 0) is the top-left
  // corner. Furthermore, the y-axis points down.
  heroX = width * 0.5;
  heroY = height * 0.5;
  background(255.0);
  noStroke();
}

void draw() {

  // Check for rotation inputs.
  if (pressed[65]) { /* A */
    heroRot -= heroRotIncr;
  }

  if (pressed[68]) { /* D */
    heroRot += heroRotIncr;
  }

  float cosRot = cos(heroRot);
  float sinRot = sin(heroRot);

  // The default coordinate system in Processing is
  // Cartesian. The formula to convert from polar
  // to Cartesian coordinates is
  // ( x = translationx + cos(angle) * radius,
  // y = translationy + sin(angle) * radius ).

  // Check for movement inputs.
  if (pressed[87]) { /* W */
    heroX = heroX + cosRot * heroMoveSpeed;
    heroY = heroY + sinRot * heroMoveSpeed;
  }

  if (pressed[83]) { /* S */
    heroX = heroX - cosRot * heroMoveSpeed;
    heroY = heroY - sinRot * heroMoveSpeed;
  }

  // Draw translucent background.
  fill(255.0, 255.0, 255.0, 10.0);
  rect(0, 0, width, height);

  // Draw avatar.
  fill(0.0, 127.0, 255.0);
  triangle(

      // Front point.
      heroX + cosRot * heroSize,
      heroY + sinRot * heroSize,

      // We have to add two rotations:
      // the angle to get from the front point
      // plus the overall avatar angle.
      heroX + cos(heroRot + heroArrow) * heroSize,
      heroY + sin(heroRot + heroArrow) * heroSize,

      heroX + cos(heroRot - heroArrow) * heroSize,
      heroY + sin(heroRot - heroArrow) * heroSize);
}

void keyPressed() {
  pressed[keyCode] = true;
}

void keyReleased() {
  pressed[keyCode] = false;
}
