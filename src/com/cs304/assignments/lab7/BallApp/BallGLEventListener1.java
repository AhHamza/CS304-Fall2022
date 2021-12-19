package com.cs304.assignments.lab7.BallApp;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public class BallGLEventListener1 implements GLEventListener {// left -> 6, 1, 4

    private final double X_MIN = -350.0;
    private final double X_MAX = 350.0;
    private final double Y_MIN = -350.0;
    private final double Y_MAX = 350.0;
    private final int NUMBER_OF_DIRECTIONS = 4;
    private final int MAX_STEPS = 30;
    private final int MAX_BALLS = 10;
    private double ballXPosition;
    private double ballYPosition;
    private int direction;
    private final double ONE_DEGREE = (Math.PI / 180);
    private final double THREE_SIXTY = 2 * Math.PI;
    private double ballRadius;
    // 0=up, 1=up-right, 2=up-left, 3=down, 4=down-right, 5=down-left, 6=right, 7=left
    private int steps;


    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glOrtho(X_MIN, X_MAX, Y_MIN, Y_MAX, -1.0, 1.0);

        ballXPosition = 0;
        ballYPosition = 0;
        ballRadius = 30;

        direction = (int) (Math.random() * NUMBER_OF_DIRECTIONS);
        steps = 1 + (int) (Math.random() * MAX_STEPS);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        System.err.println("**********************");
        GL gl = glAutoDrawable.getGL();
//        updateDirection();
        updateBallPosition();
        drawBall(gl);
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }

    /**
     * Draw a ball in the center position (ballXPosition, ballYPosition) with radius=ballRadius.
     *
     * @param gl the object of class GL
     */
    private void drawBall(GL gl) {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glColor3f(0.5f, 0.0f, 0.5f);
        gl.glBegin(GL.GL_POLYGON);
        //Draws the ball at (x= ballXPosition , y= ballYPosition)
        for (double a = 0; a < THREE_SIXTY; a += ONE_DEGREE) {
            double x = ballXPosition + ballRadius * (Math.cos(a));
            double y = ballYPosition + ballRadius * (Math.sin(a));
            gl.glVertex2d(x, y);
        }
        gl.glEnd();
    }

    /**
     * Update the direction of the ball if the steps number equals to zero and reset the value of the
     * steps.
     */
    private void updateDirection() {
        steps--;
        if (steps != 0) {
            return;
        }
        steps = 1 + (int) (Math.random() * MAX_STEPS);
        int newDirection = direction;
        while (newDirection == direction) {
            newDirection = (int) (Math.random() * NUMBER_OF_DIRECTIONS);
        }
        direction = newDirection;
    }

    /**
     * Update the position of the ball depending on the direction.
     */
    private void updateBallPosition() {
        // 0=up, 1=up-right, 2=up-left, 3=down, 4=down-right, 5=down-left, 6=right, 7=left
        if (direction == 1) { // up
            ballYPosition++;
        } else if (direction == 2) { // down
            ballYPosition--;
        } else if (direction == 3) { // right
            ballXPosition++;
        } else { // left
            ballXPosition--;
        }


        //up-left -> down-left, down, down-right
        //up -> down-left, down, down-right
        //up-right -> down-left, down, down-right

        //down-left -> up-left, up, up-right
        //down -> up-left, up, up-right
        //down-right -> up-left, up, up-right




        if (ballXPosition < X_MIN) {
            ballXPosition = X_MIN + 1;
        }

        if (ballXPosition > X_MAX) {
            ballXPosition = X_MAX + 1;
        }

        if (ballYPosition < Y_MIN) {
            ballYPosition = Y_MIN + 3;
        }

        if (ballYPosition > Y_MAX) {
            ballYPosition = Y_MAX + 3;
        }
    }
}

