package GunsProject;

import com.cs304.GunsProject.GunsAndMonstersListener;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;

import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GunsAndMonsters extends JFrame implements KeyListener {
    private Animator animator1;
    private GLCanvas glcanvas;
    private GunsAndMonstersListener listener = new GunsAndMonstersListener(); //we made this object so that we can call functions from GunGLEventListener
    public static void main(String[] args) {

        new GunsAndMonsters().animator1.start(); // activates the animator
    }

    public GunsAndMonsters() {
        super("Gun Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        animator1 = new FPSAnimator(8); // sets the time to when the function " display() " is called each time, each time we decrease the argument of the function the display() is called late
        animator1.add(glcanvas); // calls the display() repeatedly
        glcanvas.addKeyListener(this); //keyListener listens to "glcanvas"

        add(glcanvas, BorderLayout.CENTER);
        setSize(1700, 900);
        setLocationRelativeTo(this);
        setVisible(true);
        /************/
        // if we didn't put th
        // ese two functions, when we try to press a key so that the listener of the canvas listens it won't listen,
        // instead it will listen to the IDE(console) only*/
        setFocusable(true);
        glcanvas.requestFocus(); //the canvas requests focus
        /************/
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
//    private GunGLEventListener listener = new GunGLEventListener();
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            System.out.println("UP");
            listener.updateGunPosition(KeyEvent.VK_UP); //if up is pressed, send it to the GunListener
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            System.out.println("DOWN");
            listener.updateGunPosition(KeyEvent.VK_DOWN);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            System.out.println("RIGHT");
            listener.updateGunPosition(KeyEvent.VK_RIGHT);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            System.out.println("LEFT");
            listener.updateGunPosition(KeyEvent.VK_LEFT);
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            System.out.println("SPACE");
            listener.fireBullet();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
