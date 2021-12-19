package com.cs304.lab9.Example1;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.cs304.lab9.AnimListener;
import com.cs304.lab9.Texture.TextureReader;

import java.awt.event.*;
import java.io.IOException;
import javax.media.opengl.*;

import java.util.BitSet;
import javax.media.opengl.glu.GLU;

public class AnimGLEventListener extends AnimListener {

    String textureName = "Back.png"; // name of picture
    TextureReader.Texture texture;
    int textureIndex[] = new int[1]; //the size is set as the no of photos

    /*
     5 means gun in array pos
     x and y coordinate for gun 
     */
    public void init(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black

        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

        //number of textures,array to hold the indeces
        gl.glGenTextures(1, textureIndex, 0);
            /******************************/
        try {
            //converts the photo from hard disk to the memory
            texture = TextureReader.readTexture(assetsFolderName + "//" + textureName, true);
            gl.glBindTexture(GL.GL_TEXTURE_2D, textureIndex[0]);

//                mipmapsFromPNG(gl, new GLU(), texture[i]);
            new GLU().gluBuild2DMipmaps(
                    GL.GL_TEXTURE_2D,
                    GL.GL_RGBA, // Internal Texel Format,
                    texture.getWidth(), texture.getHeight(),
                    GL.GL_RGBA, // External format from image,
                    GL.GL_UNSIGNED_BYTE,
                    texture.getPixels() // Imagedata
            );
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        /******************************/
    }

    public void display(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();

        DrawBackground(gl);
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    public void DrawBackground(GL gl) {

        /**********************************************/
        // before drawing any picture we have to do these methods
        gl.glEnable(GL.GL_BLEND);    // Turn Blending On
        gl.glBindTexture(GL.GL_TEXTURE_2D, textureIndex[0]);
                                        //
        /**********************************************/
//            gl.glScaled(0.5, 0.5, 0); //scales the JFrame

        gl.glBegin(GL.GL_QUADS);
        // Front Face
        // means -> textCoord() is .. connect it with vertex()
        gl.glTexCoord2f(0.0f, 0.0f); //bind this coordinate
        gl.glVertex3f(-1f, -1f, -1.0f); //with this coordinate

        gl.glTexCoord2f(1.0f, 0.0f);// and bind this
        gl.glVertex3f(1.0f, -1.0f, -1.0f);// with this

        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);

        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);

        gl.glEnd();

        gl.glDisable(GL.GL_BLEND);
        //after putting the picture we have to disable the blend
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
