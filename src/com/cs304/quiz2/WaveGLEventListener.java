package com.cs304.quiz2;

import com.cs304.lab9.Example1.AnimGLEventListener3;

import java.util.ArrayList;
import java.util.List;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public class WaveGLEventListener implements GLEventListener {

  private static final int X_MIN = 0;
  private static final int X_MAX = 700;
  private static final int Y_MIN = 0;
  private static final int Y_MAX = 500;
  private static final double ONE_DEGREE = (Math.PI / 180);
  private static final double THREE_SIXTY = 2 * Math.PI;
  private static final double RADIUS = 5;
  private List<PointColor> pointColorList = new ArrayList<>();
  private int idx = 0;



  @Override
  public void init(GLAutoDrawable glAutoDrawable) {
    GL gl = glAutoDrawable.getGL();

    gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

    gl.glMatrixMode(GL.GL_PROJECTION);
    gl.glLoadIdentity();

    gl.glOrtho(X_MIN, X_MAX, Y_MIN, Y_MAX, -1.0, 1.0); // sets the the window of the JFrame and make it compatible with jCanvas
    //    from 0 to 700 and from 0 to 500

    // random numbers from 0 - 1
    float red = (float) Math.random();
    float green = (float) Math.random();
    float blue = (float) Math.random();

    //iterates from 0 to 700 i.e. 70 times --> size of "pointColorList" is 70
    for (int x = X_MIN; x < X_MAX; x += 10) {
     // put the same color in the list
      pointColorList.add(new PointColor(red, green, blue)/*random color*/);

      // every seven iterations change the color, so that there are 7 consequent circles with same color
      if (x % 70 == 0) {
        red = (float) Math.random();
        green = (float) Math.random();
        blue = (float) Math.random();
      }

    }
  }

  @Override
  public void display(GLAutoDrawable glAutoDrawable) {
//    idx++;
    // when idx= 70 -> 70 % 70 =0
//    idx %= pointColorList.size(); //to prevent index out of bounds when --> pointColorList.get(idx++));
    if(idx == 0) {
      idx = pointColorList.size() - 1;
    }else {
      idx--;
    }

    GL gl = glAutoDrawable.getGL();

    gl.glClear(GL.GL_COLOR_BUFFER_BIT);

    gl.glBegin(GL.GL_POINTS);
    /*Ask doctor why when we set the x= X_MAX to X_MIN it changed its direction*/
//    for (int x = X_MAX; x >= X_MIN; x -= 10) {
    for (int x = X_MAX; x >= X_MIN; x -= 10) {

      drawPointAndCircle(gl, x, (Y_MAX - Y_MIN) / 2 + (Math.sin(x / 60.0) * 100.0),
              pointColorList.get(idx++));

      idx %= pointColorList.size();
    }
    /*
    * for (int x = X_MAX; x >= X_MIN; x -= 10) {

      drawPointAndCircle(gl, x, (Y_MAX - Y_MIN) / 2 + (Math.sin(x / 60.0) * 100.0),
          pointColorList.get(idx++));

      idx %= pointColorList.size();
    }
    * */



    gl.glEnd();
  }

  @Override
  public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

  }

  @Override
  public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

  }

  private void drawPointAndCircle(GL gl, double x, double y, PointColor pointColor) {

    //Ask Doctor, why did we put gl.glBegin(GL.GL_POINTS) and     gl.glEnd(); ???
    gl.glBegin(GL.GL_POINTS);

    gl.glColor3f(pointColor.red, pointColor.green, pointColor.blue);

    drawPoint(gl, x, y);

    drawCircle(gl, x, y);

    gl.glEnd();
  }
  //at (x,y) draw a point
  private void drawPoint(GL gl, double x, double y) {

    gl.glVertex2d(x, y);
  }

  //at (x,y) draw a circle
  private void drawCircle(GL gl, double x, double y) {
    for (double a = 0; a < THREE_SIXTY; a += ONE_DEGREE) {
      /*Ask Doctor, why did we add x and y to the circle's coordinates*/
      //ask him to draw and explain it on paint
      double xx = x + RADIUS * (Math.cos(a)); // we add x to make cir
      double yy = y + RADIUS * (Math.sin(a));
      gl.glVertex2d(xx, yy);
    }
  }
}
