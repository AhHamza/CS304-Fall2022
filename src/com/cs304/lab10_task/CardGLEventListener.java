package com.cs304.lab10_task;

import com.cs304.lab9.Texture.TextureReader;
import java.io.IOException;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class CardGLEventListener implements GLEventListener {

  private final static int MAX_WIDTH = 900;
  private final static int MAX_HEIGHT = 900;
  private final static int SPACE_IN_BETWEEN = 80;
  private final static int NAME_LINES = 200;
  private final static int CODE_LINES = 200;
  private final static int X_START = 20;

  private final static String assetsFolderName = "Assets//card";
  private final static String[] textureNames = {"0.png", "1.png", "2.png", "3.png", "4.png",
      "5.png", "6.png", "7.png", "8.png", "9.png",
      "a.png", "b.png", "c.png", "d.png", "e.png", "f.png", "g.png", "h.png", "i.png", "j.png",
      "k.png", "l.png", "m.png", "n.png", "o.png", "p.png", "q.png", "r.png", "s.png", "t.png",
      "u.png", "v.png", "w.png", "x.png", "y.png", "z.png", "back.png"};
  private final TextureReader.Texture[] texture = new TextureReader.Texture[textureNames.length];
  private final int[] textures = new int[textureNames.length];

  private int yNamePosition = (MAX_HEIGHT / NAME_LINES) * 100;
  private int yCodePosition = MAX_HEIGHT / CODE_LINES;

  @Override
  public void init(GLAutoDrawable glAutoDrawable) {
    GL gl = glAutoDrawable.getGL();
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
        System.err.println(e.getMessage());
      }
    }
  }

  @Override
  public void display(GLAutoDrawable glAutoDrawable) {

    GL gl = glAutoDrawable.getGL();

    incrementYNamePosition();
    incrementYCodePosition();

    drawBackground(gl);

    drawWord(gl, "abdelghany", yNamePosition);

    drawWord(gl, "01541236578", (yNamePosition + 100) % MAX_HEIGHT);

    drawWord(gl, "120845", yCodePosition);

    drawWord(gl, "cairo", (yCodePosition + 100) % MAX_HEIGHT);
  }

  @Override
  public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

  }

  @Override
  public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

  }

  public void drawWord(GL gl, String str, int yPosition) {

    str = str.toLowerCase();
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) != ' ') {
        drawSprite(gl, X_START + i * SPACE_IN_BETWEEN, yPosition, getCharacterIndex(str.charAt(i)), 1);
      }
    }
  }

  public void drawBackground(GL gl) {
    gl.glEnable(GL.GL_BLEND);
    gl.glBindTexture(GL.GL_TEXTURE_2D, textures[textures.length - 1]);  // Turn Blending On

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

  public void drawSprite(GL gl, double x, double y, int index, float scale) {
    gl.glEnable(GL.GL_BLEND);
    gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);  // Turn Blending On

    gl.glPushMatrix();
    gl.glTranslated(x / (MAX_WIDTH / 2.0) - 0.9, y / (MAX_HEIGHT / 2.0) - 0.9, 0);
    gl.glScaled(0.1 * scale, 0.1 * scale, 1);
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

  private void incrementYNamePosition() {
    yNamePosition += MAX_HEIGHT / NAME_LINES;
    if (yNamePosition >= MAX_HEIGHT) {
      yNamePosition = MAX_HEIGHT / NAME_LINES;
    }
  }

  private void incrementYCodePosition() {
    yCodePosition += MAX_HEIGHT / CODE_LINES;
    if (yCodePosition >= MAX_HEIGHT) {
      yCodePosition = MAX_HEIGHT / CODE_LINES;
    }
  }

  private int getCharacterIndex(char ch) {
    if (Character.isDigit(ch)) {
      return getNumberIndex(ch);
    } else {
      return getLetterIndex(ch);
    }
  }

  private int getLetterIndex(char ch) {
    return 10 + (ch - 'a');
  }

  private int getNumberIndex(char ch) {
    return ch - '0';
  }
}
