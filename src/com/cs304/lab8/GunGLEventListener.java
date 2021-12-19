package com.cs304.lab8;

import java.awt.event.KeyEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.swing.JOptionPane;

public class  GunGLEventListener implements GLEventListener {

  private static final int MIN_X = 0;
  private static final int MAX_X = 1700;
  private static final int MIN_Y = 0;
  private static final int MAX_Y = 900;
                                    /*What is DI and DJ ??*/
  private static final int DI = 50; // no. of rows in the matrix
  private static final int DJ = 50; // no. of columns in the matrix

  private static final int BULLET_MARGIN = 2;
  private static final int GUN_MARGIN = 6;

  private static final int MAX_ENEMIES = 2; // number of enemies generated each time the display function is called

  // 0=empty & 1=gun & 2= enemy
  private int[][] enemy = new int[DI][DJ];
  // 0= empty & 1=bullet
  private int[][] bullet = new int[DI][DJ];
  private int gunX;
  private int gunY;

  @Override
  public void init(GLAutoDrawable glAutoDrawable) {
    GL gl = glAutoDrawable.getGL();

    gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

    gl.glMatrixMode(GL.GL_PROJECTION);
    gl.glLoadIdentity();

    gl.glOrtho(MIN_X, MAX_X, MIN_Y, MAX_Y, -1.0, 1.0); //draw from this position


    /* Ask Doc.!, what have we done here ? */
    // x and y positions of the gun
    gunX = 2;
    gunY = DJ / 2; // in the middle
  }

  @Override
  public void display(GLAutoDrawable glAutoDrawable) {
    GL gl = glAutoDrawable.getGL();

    gl.glClear(GL.GL_COLOR_BUFFER_BIT);

    moveEnemies();
    moveBullets();
    generateEnemies();
    resolveCollision();

    drawGun(gl);
    drawEnemies(gl);
    drawBullets(gl);

  }

  private void drawBullets(GL gl) {
    for (int i = 0; i < DI; i++) {
      for (int j = 0; j < DJ; j++) {
        if (bullet[i][j] == 1) {
          drawBullet(gl, i, j);
        }
      }
    }
  }

  private void drawBullet(GL gl, int x, int y) {
    gl.glPointSize(5.0f);
    gl.glColor3f(1.0f, 0.0f, 0.0f);
    gl.glBegin(GL.GL_POINTS);
    gl.glVertex2i(convertX(x), convertY(y));
    gl.glEnd();
  }
  private void drawEnemies(GL gl) {
    // 0=empty & 1=gun & (2= enemy)
    for (int i = 0; i < DI; i++) {
      for (int j = 0; j < DJ; j++) {
        if (enemy[i][j] == 2) {
          drawEnemy(gl, i, j);
        }
      }
    }
  }
  // Draws enemy
  private void drawEnemy(GL gl, int x, int y) {
    gl.glPointSize(30.0f);
    gl.glColor3f(0.0f, 1.0f, 0.0f);
    gl.glBegin(GL.GL_POINTS);
    gl.glVertex2i(convertX(x), convertY(y));
    gl.glEnd();
  }

  private void drawGun(GL gl) {
    gl.glPointSize(20.0f);
    gl.glColor3f(0.0f, 0.0f, 1.0f);
    gl.glBegin(GL.GL_POINTS);
    gl.glVertex2i(convertX(gunX), convertY(gunY)); // we drew the gun at this position using "gunX" and "gunY" but with modificaion
    gl.glEnd();
  }

  private void resolveCollision() {
    resolveBulletCollision();
    resolveGunCollision();
  }

          /*Ask doctor*/ /*I dont understand the margin idea*/
  private void resolveBulletCollision() {
    for (int i = 0; i < DI; i++) {
      for (int j = 0; j < DJ; j++) {
        for (int k = Math.max(0, j - BULLET_MARGIN); k < Math.min(DJ, j + BULLET_MARGIN + 1); k++) {
          for(int h = Math.max(0, i - BULLET_MARGIN); h < Math.min(DI, i + BULLET_MARGIN + 1); h++) {
            if (bullet[h][k] == 1 && enemy[i][j] == 2) {
              bullet[h][k] = 0;
              enemy[i][j] = 0;
              break; //Ask doctor, why did we break?
            }
          }
        }
      }
    }
  }

  private void resolveGunCollision() {
    for (int i = 0; i < DI; i++) {
      for (int j = 0; j < DJ; j++) {
        for (int k = Math.max(0, j - GUN_MARGIN); k < Math.min(DJ, j + GUN_MARGIN + 1); k++) {
          if (enemy[i][k] == 2 && i == gunX && k == gunY) {
            System.out.println("GameOver");
            JOptionPane.showMessageDialog(null, "GameOver.", "GameOver",
                JOptionPane.WARNING_MESSAGE);
            System.exit(0);
          }
        }
      }
    }
  }

  private void generateEnemies() {
    int cnt = MAX_ENEMIES; //MAX_ENEMIES = 2
    while (cnt-- > 0) {

      int x = 48; //-->Beginning position of enemies, last column is (DI) : 50
      int y = (int) (Math.random() * DJ);
      enemy[x][y] = 2; // at x and y position there is an (enemy = 2)
    }
  }

  private void moveBullets() {
    //     if i = DI -1 ---> index out of bounds
    for (int i = DI - 2; i >= 0; i--) {
      for (int j = 0; j < DJ; j++) {
        if (bullet[i][j] == 1) {
          bullet[i][j] = 0;
          bullet[i + 1][j] = 1; //index out of bounds if i = DI -1
        }
      }
    }

    for (int j = 0; j < DJ; j++) {
      if (bullet[DI - 1][j] == 1) {
        bullet[DI - 1][j] = 0;
      }
    }
  }

  private void moveEnemies() {
    for (int i = 1; i < DI; i++) {
      for (int j = 0; j < DJ; j++) {
        if (enemy[i][j] == 2) {
          enemy[i][j] = 0; // remove enemy from this position
          enemy[i - 1][j] = 2; // move enemy to left
        }
      }
    }

    for (int j = 0; j < DJ; j++) {
      // if enemy (2) reaches to the most left(i.e. i=0)
      if (enemy[0][j] == 2) {
        // remove the enemy
        enemy[0][j] = 0;
      }
    }
  }


  /*why did we convert X ???*/
  private int convertX(int x) {
    return x * MAX_X / DI;
  }

  private int convertY(int y) {
    return y * MAX_Y / DJ;
  }

  @Override
  public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {
  }

  @Override
  public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {
  }

  public void updateGunPosition(int code) {

    enemy[gunX][gunY] = 0;

    if (code == KeyEvent.VK_UP) {
      gunY++;
    } else if (code == KeyEvent.VK_DOWN) {
      gunY--;
    } else if (code == KeyEvent.VK_RIGHT) {
      gunX++;
    } else if (code == KeyEvent.VK_LEFT) {
      gunX--;
    }

  //if gun tries to exceed most left -> return it back
    if (gunX < 0) {
      gunX++;
    }
//if gun tries to exceed most right(DI) -> return it back
    if (gunX == DI) {
      gunX--;
    }
//if gun tries to exceed above  -> return it back
    if (gunY < 0) {
      gunY++;
    }
//if gun tries to exceed below(DJ)  -> return it back

    if (gunY == DJ) {
      gunY--;
    }

    enemy[gunX][gunY] = 1;

  }


  public void fireBullet() {
    //avoid index out of bounds
    // we did the case of DJ only since the bullet goes only to the right
    if (gunY + 1 != DJ) {
      bullet[gunX][gunY + 1] = 1;
    }
  }
}
