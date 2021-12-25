package com.cs304.GunsProject;
import com.cs304.AnimListener1;
import com.cs304.lab9.Texture.TextureReader;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.BitSet;
public class AnimGLEventListenerSoldiersAndMonsters extends AnimListener1 {
    int animationIndex = 0;
    int maxWidth = 100;
    int maxHeight = 100;
    int x = maxWidth/2, y = maxHeight/2;



    private static final int MIN_X = 0;
    private static final int MAX_X = 1700;
    private static final int MIN_Y = 0;
    private static final int MAX_Y = 900;
    private static final int DI = 50; // no. of rows in the matrix
    private static final int DJ = 50; // no. of columns in the matrix

    private static final int BULLET_MARGIN = 2;
    private static final int GUN_MARGIN = 6;

    private static final int MAX_ENEMIES = 2; // number of enemies generated each time the display function is called

    // 0=empty & 1=gun & 2= enemy
    private int[][] enemy = new int[DI][DJ];
    // 0= empty & 1=bullet
    private int[][] bullet = new int[DI][DJ];
    private int gunX;
    private int gunY;


    // Download enemy textures from https://craftpix.net/freebies/free-monster-2d-game-items/
    String textureNames1[] = {"Man1.png","Man2.png","Man3.png","Man4.png","BackG.png"};
    String textureNames2[] = {"z1.png","z2.png","z3.png","z4.png","backG.png"};
    TextureReader.Texture texture1[] = new TextureReader.Texture[textureNames1.length];
    TextureReader.Texture texture2[] = new TextureReader.Texture[textureNames2.length];
    int textures1[] = new int[textureNames1.length];
    int textures2[] = new int[textureNames2.length];

    /*

     5 means gun in array pos
     x and y coordinate for gun

     */
    public void init(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black

        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames1.length, textures1, 0);

        //for soldiers
        for(int i = 0; i < textureNames1.length; i++){
            try {
                texture1[i] = TextureReader.readTexture(assetsFolderName + "//" + textureNames1[i] , true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures1[i]);

//                mipmapsFromPNG(gl, new GLU(), texture[i]);
                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA, // Internal Texel Format,
                        texture1[i].getWidth(), texture1[i].getHeight(),
                        GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE,
                        texture1[i].getPixels() // Imagedata
                );
            } catch( IOException e ) {
                System.out.println(e);
                e.printStackTrace();
            }
        }

        //For zombies
        for(int i = 0; i < textureNames2.length; i++){
            try {
                texture2[i] = TextureReader.readTexture(assetsFolderName + "//" + textureNames2[i] , true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures2[i]);

//                mipmapsFromPNG(gl, new GLU(), texture[i]);
                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA, // Internal Texel Format,
                        texture2[i].getWidth(), texture2[i].getHeight(),
                        GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE,
                        texture2[i].getPixels() // Imagedata
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
        handleKeyPress();
        animationIndex = animationIndex % 4;


//        DrawGraph(gl);
        DrawSprite(gl, x, y, animationIndex, 1);
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    public void DrawSprite(GL gl,int x, int y, int index, float scale){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures1[index]);	// Turn Blending On

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
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures1[4]);	// Turn Blending On

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

    public void handleKeyPress() {

        if (isKeyPressed(KeyEvent.VK_LEFT)) {
            if (x > 0) {
                x--;
            }
            animationIndex++;
        }
        if (isKeyPressed(KeyEvent.VK_RIGHT)) {
            if (x < maxWidth-10) {
                x++;
            }
            animationIndex++;
        }
        if (isKeyPressed(KeyEvent.VK_DOWN)) {
            if (y > 0) {
                y--;
            }
            animationIndex++;
        }
        if (isKeyPressed(KeyEvent.VK_UP)) {
            if (y < maxHeight-10) {
                y++;
            }
            animationIndex++;
        }
    }

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


    // if bullet is at the required place, draw it
    private void drawBullets(GL gl) {
        for (int i = 0; i < DI; i++) {
            for (int j = 0; j < DJ; j++) {
                // if there's a bullet in bullet[i][j]
                if (bullet[i][j] == 1) {
                    drawBullet(gl, i, j); //draw it
                }
            }
        }
    }

    private void drawBullet(GL gl, int x, int y) {
        gl.glPointSize(5.0f);
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        gl.glBegin(GL.GL_POINTS);
        gl.glVertex2i(convertX(x), convertY(y));
        gl.glEnd();
    }

    // if enemies is at the required place, draw it
    private void drawEnemies(GL gl) {
        // 0=empty & 1=gun & (2= enemy)
        for (int i = 0; i < DI; i++) {
            for (int j = 0; j < DJ; j++) {
                if (enemy[i][j] == 2) {
                    drawEnemy(gl, i, j);
                }
            }
        }
    }

    // Draws enemy at  gl.glVertex2i(convertX(x), convertY(y));
    private void drawEnemy(GL gl, int x, int y) {
        gl.glPointSize(30.0f);
        gl.glColor3f(0.0f, 1.0f, 0.0f);
        gl.glBegin(GL.GL_POINTS);
        gl.glVertex2i(convertX(x), convertY(y));
        gl.glEnd();
    }

    //draw gun at     gl.glVertex2i(convertX(gunX), convertY(gunY));
    private void drawGun(GL gl) {
        gl.glPointSize(20.0f);
        gl.glColor3f(0.0f, 0.0f, 1.0f);
        gl.glBegin(GL.GL_POINTS);
        gl.glVertex2i(convertX(gunX), convertY(gunY)); // we drew the gun at this position using "gunX" and "gunY" but with modificaion
        gl.glEnd();
    }

    private void resolveCollision() {
        resolveBulletCollision();
        resolveGunCollision();
    }

    /*Ask doctor*/ /*I dont understand the margin idea*/
    private void resolveBulletCollision() {
        for (int i = 0; i < DI; i++) {
            for (int j = 0; j < DJ; j++) {
                for (int k = Math.max(0, j - BULLET_MARGIN); k < Math.min(DJ, j + BULLET_MARGIN + 1); k++) {
                    for (int h = Math.max(0, i - BULLET_MARGIN); h < Math.min(DI, i + BULLET_MARGIN + 1); h++) {
                        if (bullet[h][k] == 1 && enemy[i][j] == 2) {
                            bullet[h][k] = 0;
                            enemy[i][j] = 0;
                            break; //Ask doctor, why did we break?
                        }
                    }
                }
            }
        }
    }

    private void resolveGunCollision() {
        for (int i = 0; i < DI; i++) {
            for (int j = 0; j < DJ; j++) {
                for (int k = Math.max(0, j - GUN_MARGIN); k < Math.min(DJ, j + GUN_MARGIN + 1); k++) /*Margin*/ {
                    if (enemy[i][k] == 2 && i == gunX /* x position of gun */ && k == gunY/* y position of gun */) {
                        System.out.println("GameOver");
                        JOptionPane.showMessageDialog(null, "GameOver.", "GameOver",
                                JOptionPane.WARNING_MESSAGE);
                        System.exit(0);
                    }
                }
            }
        }
    }

    private void generateEnemies() {
        int cnt = MAX_ENEMIES; //MAX_ENEMIES = 2
        while (cnt-- > 0) {

            int x = (int) (Math.random() * DJ); //-->Beginning position of enemies, last column is (DI) : 50
            int y = 48;
            enemy[x][y] = 2; // at x and y position there is an (enemy = 2)
        }
    }

    //    private void moveBullets() {
//        //     if i = DI -1 ---> index out of bounds
//        for (int i = DI - 2; i >= 0; i--) {
//            for (int j = 0; j < DJ; j++) {
//                if (bullet[i][j] == 1) {
//                    bullet[i][j] = 0;
//                    bullet[i + 1][j] = 1; //index out of bounds if i = DI -1
//                }
//            }
//        }
//
//        for (int j = 0; j < DJ; j++) {
//            if (bullet[DI - 1][j] == 1) {
//                bullet[DI - 1][j] = 0;
//            }
//        }
//    }
    private void moveBullets() {
        //     if i = DI -1 ---> index out of bounds
        for (int i = 0; i < DI; i++) {
            for (int j = DJ-2; j >=0; j--) {
                if (bullet[i][j] == 1) {
                    bullet[i][j] = 0;
                    bullet[i][j+1] = 1; //index out of bounds if i = DI -1
                }
            }
        }

        for (int i = 0; i < DJ; i++) {
            if (bullet[i][DJ-1] == 1) {
                bullet[i][DJ-1] = 0;
            }
        }
    }

    private void moveEnemies() {
        for (int i = 1; i < DI; i++) {
            for (int j = 1; j < DJ; j++) {
                if (enemy[i][j] == 2) {
                    enemy[i][j] = 0; // remove enemy from this position
                    enemy[i][j - 1] = 2; // move enemy to down
                }
            }
        }

        for (int i = 0; i < DI; i++) {
            // if enemy (2) reaches to the most left(i.e. i=0)
            if (enemy[i][0] == 2) {
                // remove the enemy
                enemy[i][0] = 0;
            }
        }
    }


    private int convertX(int x) {
        return x * MAX_X / DI;
    }

    private int convertY(int y) {
        return y * MAX_Y / DJ;
    }









    public void updateGunPosition(int code) {

        enemy[gunX][gunY] = 0;

        if (code == KeyEvent.VK_UP) {
            gunY++;
        } else if (code == KeyEvent.VK_DOWN) {
            gunY--;
        } else if (code == KeyEvent.VK_RIGHT) {
            gunX++;
        } else if (code == KeyEvent.VK_LEFT) {
            gunX--;
        }

        //if gun tries to exceed most left -> return it back
        if (gunX < 0) {
            gunX++;
        }
//if gun tries to exceed most right(DI) -> return it back
        if (gunX == DI) {
            gunX--;
        }
//if gun tries to exceed above  -> return it back
        if (gunY < 0) {
            gunY++;
        }
//if gun tries to exceed below(DJ)  -> return it back

        if (gunY == DJ) {
            gunY--;
        }

        enemy[gunX][gunY] = 1;

    }


    public void fireBullet() {
        //avoid index out of bounds
        // we did the case of DJ only since the bullet goes only to the right
        if (gunY + 1 != DJ) {
            bullet[gunX][gunY + 1] = 1;
        }
    }


}

