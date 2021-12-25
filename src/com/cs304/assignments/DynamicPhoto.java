package com.cs304.assignments;

import com.cs304.lab9.AnimListener;
import com.cs304.lab9.Example1.AnimGLEventListener3;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;

import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;

public class DynamicPhoto extends JFrame {
    public static void main(String[] args) {
new DynamicPhoto();
    }
    public DynamicPhoto(){

        GLCanvas glcanvas;
        Animator animator;

        AnimListener listener = new PhotoEventListener();
        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        glcanvas.addKeyListener(listener);
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        animator = new FPSAnimator(15);
        animator.add(glcanvas);
        animator.start();

        setTitle("Dynamic Photo Test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 900);
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
        glcanvas.requestFocus();
    }

}
