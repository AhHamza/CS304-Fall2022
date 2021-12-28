package com.cs304.lab10_task;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;
import java.awt.BorderLayout;
import javax.media.opengl.GLCanvas;
import javax.swing.JFrame;

public class CardApp extends JFrame {

  private GLCanvas glcanvas;
  private CardGLEventListener listener = new CardGLEventListener();
  private Animator animator;

  public static void main(String[] args) {
    new CardApp().animator.start();
  }

  public CardApp() {
    super("Card Application");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    glcanvas = new GLCanvas();
    glcanvas.addGLEventListener(listener);
    animator = new FPSAnimator(10);
    animator.add(glcanvas);

    add(glcanvas, BorderLayout.CENTER);
    setSize(900, 900);
    setLocationRelativeTo(this);
    setVisible(true);
  }

}
