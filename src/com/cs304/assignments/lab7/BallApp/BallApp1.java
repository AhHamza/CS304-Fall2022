package com.cs304.assignments.lab7.BallApp;


import com.sun.opengl.util.Animator;

import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;

public class BallApp1 extends JFrame {

    public Animator animator;
    private GLCanvas glcanvas;
    private  BallGLEventListener1 listener = new BallGLEventListener1();

    public static void main(String[] args) {
        new BallApp1().animator.start();
    }

    public BallApp1() {
        super("Ball Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        animator = new Animator(glcanvas);

        add(glcanvas, BorderLayout.CENTER);
        setSize(700, 700);
        setLocationRelativeTo(this);
        setVisible(true);
    }
}