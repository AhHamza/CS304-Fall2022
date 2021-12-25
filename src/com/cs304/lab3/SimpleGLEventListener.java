package com.cs304.lab3;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public class SimpleGLEventListener implements GLEventListener {

  @Override
  public void init(GLAutoDrawable glAutoDrawable) {

    GL gl = glAutoDrawable.getGL();

    gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

    gl.glMatrixMode(GL.GL_PROJECTION);
    gl.glLoadIdentity();

    gl.glOrtho(0.0, 600.0, 0.0, 300.0, -1.0, 1.0); //same as JFrame size  i.e.  setSize(600, 300);

  }

  @Override
  public void display(GLAutoDrawable glAutoDrawable) {

    GL gl = glAutoDrawable.getGL();

    gl.glClear(GL.GL_COLOR_BUFFER_BIT);

//    gl.glPointSize(13.0f);
//
//    gl.glColor3f(1.0f, 0.0f, 0.0f);
//
//    gl.glBegin(GL.GL_POINTS);
//    gl.glVertex2i(300, 150);
//    gl.glEnd();
//
//    gl.glPointSize(33.0f);
//
//    gl.glColor3f(0.0f, 1.0f, 0.0f);
//
//    gl.glBegin(GL.GL_POINTS);
//    gl.glVertex2i(200, 150);
//    gl.glEnd();
//
//    gl.glPointSize(53.0f);
//
//    gl.glColor3f(0.0f, 0.0f, 1.0f);
//
//    gl.glBegin(GL.GL_POINTS);
//    gl.glVertex2i(400, 150);
//    gl.glEnd();

//    gl.glColor3f(1.0f, 0.0f, 0.0f);
//
//    gl.glBegin(GL.GL_POLYGON);
//    gl.glVertex2i(300, 250);
//    gl.glVertex2i(150, 50);
//    gl.glVertex2i(450, 50);
//    gl.glEnd();

//    gl.glColor3f(1.0f, 0.0f, 0.0f);
//
//    gl.glBegin(GL.GL_LINES);
//
//    gl.glVertex2i(300, 250);
//    gl.glVertex2i(150, 50);
//
//    gl.glVertex2i(150, 50);
//    gl.glVertex2i(450, 50);
//
//    gl.glVertex2i(450, 50);
//    gl.glVertex2i(300, 250);
//    gl.glEnd();

//    gl.glColor3f(1.0f, 0.0f, 0.0f);
//
//    gl.glBegin(GL.GL_LINES);
//
//    gl.glVertex2i(100, 50);
//    gl.glVertex2i(500, 50);
//
//    gl.glVertex2i(500, 50);
//    gl.glVertex2i(500, 250);
//
//    gl.glVertex2i(500, 250);
//    gl.glVertex2i(100, 250);
//
//    gl.glVertex2i(100, 250);
//    gl.glVertex2i(100, 50);
//    gl.glEnd();

    gl.glColor3f(1.0f, 0.0f, 0.0f);

    double radius = 90;
    double ONE_DEGREE = (Math.PI / 1000);
    double THREE_SIXTY = 2 * Math.PI;

    gl.glBegin(GL.GL_LINES);
    for (double a = 0; a < THREE_SIXTY; a += ONE_DEGREE) {
      int x = (int)(radius * (Math.cos(a))) + 150;
      int y = (int)(radius * (Math.sin(a))) + 150;
      gl.glVertex2d(x, y);
    }
    gl.glEnd();

  }

  @Override
  public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

  }

  @Override
  public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

  }
}
