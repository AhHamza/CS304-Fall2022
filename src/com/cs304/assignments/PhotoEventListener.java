package com.cs304.assignments;

import com.cs304.lab9.AnimListener;
import com.cs304.lab9.Texture.TextureReader;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class PhotoEventListener extends AnimListener {
    private final double X_MIN = -350.0;
    private final double X_MAX = 350.0;
    private final double Y_MIN = -350.0;
    private final double Y_MAX = 350.0;
    int animationIndex = 0;
    int maxWidth = 100;
    int maxHeight = 100;
    int x = maxWidth/2, yName = maxHeight/2, yCode= maxHeight/3, yNum = maxHeight/4;

    String textureNames[] = {"Name.png","number.png","code.png","ahmed's photo.png"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];

    public void init(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black

        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);

        for(int i = 0; i < textureNames.length; i++){
            try {
                texture[i] = TextureReader.readTexture(assetsFolderName + "//" + textureNames[i] , true);
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
            } catch( IOException e ) {
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
        yName++;
        if(yName>100){
            yName=0;
        }
        moveText(gl,yName,0,1);

        yCode++;
        if(yCode>100){
            yCode=0;
        }
        moveText(gl,yCode,1,1);

        yNum++;
        if(yNum>100){
            yNum=0;
        }
        moveText(gl,yNum,2,1);
    }




    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

   public void moveText(GL gl, int y,int index,float scale){

       gl.glEnable(GL.GL_BLEND);
       gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);	// Turn Blending On

       gl.glPushMatrix();
       gl.glTranslated( x/(maxWidth/2.0) - 0.9, y/(maxHeight/2.0) - 0.9, 0);
       gl.glScaled(0.1*scale, 0.1*scale, 1);
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

    public void DrawBackground(GL gl){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[texture.length-1]);	// Turn Blending On

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

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
