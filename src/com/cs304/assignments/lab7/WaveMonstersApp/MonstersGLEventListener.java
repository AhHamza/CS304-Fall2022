package com.cs304.assignments.lab7.WaveMonstersApp;

import com.cs304.lab9.AnimListener;
import com.cs304.lab9.Texture.TextureReader;
import com.cs304.quiz2.PointColor;
import com.cs304.quiz2.WaveGLEventListener;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.BitSet;


public class MonstersGLEventListener extends AnimListener {
    int maxWidth = 700;
    int maxHeight = 700;
//    int x = maxWidth / 2, y = maxHeight / 2;
    private static final int X_MIN = 0;
    private static final int X_MAX = 700;
    private static final int Y_MIN = 0;
    private static final int Y_MAX = 500;
    private static  double rotate = 0;
    private static int textureIndex = 0;

    private static final double ONE_DEGREE = (Math.PI / 180);
    private static final double THREE_SIXTY = 2 * Math.PI;
    private static final double RADIUS = 5;
//    String textureNames[] = {"1.png","", "2.png","", "3.png","", "4.png","", "5.png","", "6.png","", "7.png","", "8.png","", "Back.png"};
    String textureNames[] = {"1.png", "2.png", "3.png", "Back.png"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];

    /*
     5 means gun in array pos
     x and y coordinate for gun
     */
    public void init(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black

        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);

        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture(assetsFolderName + "//" + textureNames[i], true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);

//                mipmapsFromPNG(gl, new GLU(), texture[i]);
                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA, // Internal Texel Format,
                        texture[i].getWidth(), texture[i].getHeight(),
                        GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE,
                        texture[i].getPixels() // Imagedata
                );
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }

    public void display(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();

        DrawBackground(gl);
//        textureIndex %=  textureNames.length - 1;

        for (int x = X_MIN; x <= X_MAX; x += 15) {
            //we want to elevate the graph up by (Y_MAX - Y_MIN) / 2
            DrawSprite(gl, x, (Y_MAX - Y_MIN) / 2 + (Math.sin(x / 60.0) * 100.0), textureIndex++, 1, rotate++);
            textureIndex %=  textureNames.length - 1;
        }
/**
 *  for (int x = X_MAX; x >= X_MIN; x -= 10) {
 *
 *       drawPointAndCircle(gl, x, (Y_MAX - Y_MIN) / 2 + (Math.sin(x / 60.0) * 100.0),
 *           pointColorList.get(idx++));
 *
 *       idx %= pointColorList.size();
 *     }
 */

//        handleKeyPress();


//        DrawGraph(gl);
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    public void DrawSprite(GL gl, double x, double y, int index, float scale, double rotate) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);    // Turn Blending On

        gl.glPushMatrix();
        //each time i press the keys the x and y are changed and then i take them to translate the soldier
        gl.glTranslated(x / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 0); //Ask doctor, when i translated the mosters disappeared
        gl.glScaled(0.1 * scale, 0.1 * scale, 1);
        gl.glRotated(rotate, 0, 0, 1);

        //System.out.println(x +" " + y);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }




    public void DrawBackground(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[textureNames.length-1]);    // Turn Blending On

        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    /*
     * KeyListener
     */



    /******************************/
    //not important
    public BitSet keyBits = new BitSet(256);

    @Override
    public void keyPressed(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.set(keyCode);
    }

    @Override
    public void keyReleased(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.clear(keyCode);
    }

    @Override
    public void keyTyped(final KeyEvent event) {
        // don't care
    }

    public boolean isKeyPressed(final int keyCode) {
        return keyBits.get(keyCode);
    }
}
