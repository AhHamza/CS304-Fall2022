package com.cs304.assignments.lab7.WaveMonstersApp;

import com.cs304.lab9.AnimListener;
import com.cs304.lab9.Example1.AnimGLEventListener3;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;

import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;

public class MonstersApp extends JFrame {
    GLCanvas glcanvas;
    Animator animator;

    public static void main(String[] args) {
new MonstersApp();
    }
public MonstersApp(){
    AnimListener listener = new MonstersGLEventListener();
    glcanvas = new GLCanvas();
    glcanvas.addGLEventListener(listener);
    glcanvas.addKeyListener(listener);
    getContentPane().add(glcanvas, BorderLayout.CENTER);
    animator = new FPSAnimator(5);
    animator.add(glcanvas);
    animator.start();

    setTitle("Anim Test");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(700, 700);
    setLocationRelativeTo(null);
    setVisible(true);
    setFocusable(true);
    glcanvas.requestFocus();}

}
