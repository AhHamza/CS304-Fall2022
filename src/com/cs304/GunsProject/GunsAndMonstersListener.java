package com.cs304.GunsProject;

import AnimListener.AnimListener1;
import com.sun.opengl.util.j2d.TextRenderer;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.BitSet;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class GunsAndMonstersListener extends AnimListener1 implements GLEventListener {
    private static final int MIN_X = 0;
    private static final int MAX_X = 1700;
    private static final int MIN_Y = 0;
    private static final int MAX_Y = 900;
    private static final int DI = 50; // no. of rows in the matrix
    private static final int DJ = 50; // no. of columns in the matrix
    private int counter = 0;

    int x = MAX_X / 2;
    int y = 0;
    private int score = 0;
    int heals = 0;

    private static final int BULLET_MARGIN = 2;
    private static final int GUN_MARGIN = 6;

    private static final int MAX_ENEMIES = 1; // number of enemies generated each time the display function is called

    // 0=empty & 1=gun & 2= enemy
    private int[][] enemy = new int[DI][DJ];
    // 0= empty & 1=bullet
    private int[][] bullet = new int[DI][DJ];
    private int gunX;
    private int gunY;


    String textureNames[] = {"0_Golem_Run Throwing_000.png", "0_Golem_Run Throwing_001.png",
            "0_Golem_Run Throwing_000.png", "0_Golem_Run Throwing_003.png", "0_Golem_Run Throwing_004.png",
            "0_Golem_Run Throwing_005.png", "0_Golem_Run Throwing_006.png",
            "0_Golem_Run Throwing_007.png", "0_Golem_Run Throwing_008.png",
            "0_Golem_Run Throwing_009.png", "0_Golem_Run Throwing_010.png",
            "0_Golem_Run Throwing_011.png", "Man1.png", "Man2.png", "Man3.png", "Man4.png", "bullet.png",
            "heart5.png", "heart4.png", "heart3.png", "heart2.png", "heart1.png",
            "heart0.png", "GameOver.png", "Pause.png", "Back.png"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];

    TextRenderer renderer = new TextRenderer(new Font("SanasSerif", Font.BOLD, 20));

    GLAutoDrawable gld;


    @Override
    public void init(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black

        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);

        for (int i = 0; i < textureNames.length; i++) {

            try {
                //to ignore two empty picture
                if (i == textureNames.length - 2 || i == textureNames.length - 3 || i == textureNames.length - 4) {
                    continue;
                }

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

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        DrawBackground(gl);


        moveEnemies();
        moveBullets();

        //generate enemies  once each 8 times the display is called
        if (counter % 8 == 0) {
            generateEnemies();
        }

        counter++;

        resolveCollision(gl);

        // 13 ->16 soldiers
        for (int k = 13; k <= 16; k++) {
            drawGun(gl, convertX(gunX), convertY(gunY), k, 1);
        }
// 0->12 monsters
        for (int k = 0; k <12; k++) {
            drawEnemies(gl,k);
        }
        drawBullets(gl);

    }

    public void DrawBackground(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[textures.length - 1]);    // Turn Blending On

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

    public void DrawTime() throws ParseException {

        String time1 = java.time.LocalTime.now() + "";
        String time2 = java.time.LocalTime.now() + "";

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date1 = format.parse(time1);
        Date date2 = format.parse(time2);
        long difference = date2.getTime() - date1.getTime();

        String fi = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(difference),
                TimeUnit.MILLISECONDS.toSeconds(difference) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(difference)) - 4);

        renderer.beginRendering(gld.getWidth(), gld.getHeight());
        renderer.draw(fi, 600, 620);
        renderer.endRendering();


    }

    public void DrawHeals(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[heals + 16]);    // Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated(-.70, .70, 0);
        gl.glScaled(0.1, 0.1, 1);

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

    public void DrawGameOver(GL gl) {

        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[22]);    // Turn Blending On

        gl.glPushMatrix();
        gl.glScaled(0.6, 0.6, 1);

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

    public void DrawPause(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[23]);    // Turn Blending On

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

    public void DrawScore() {

        String fi = "Score: " + score;
        renderer.beginRendering(gld.getWidth(), gld.getHeight());
        renderer.draw(fi, 600, 620); //minus
        renderer.endRendering();

    }


    // if bullet is at the required place, draw it
    private void drawBullets(GL gl) {
        for (int i = 0; i < DI; i++) {
            for (int j = 0; j < DJ; j++) {
                // if there's a bullet in bullet[i][j]
                if (bullet[i][j] == 1) {
                    drawBullet(gl, convertX(i), convertY(j), 1); //draw it
                    System.out.println("ahmw=ed");
                }
            }
        }
    }

    public void drawBullet(GL gl, int x, int y, float scale) {
        // float scalex = 118f,scaley = 442f;
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[16]);    // Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated(x / (MAX_X / 2.0) - 0.9, y / (MAX_Y / 2.0) - 0.9, 0);
        gl.glScaled(0.1 * scale, 0.1 * scale, 1);


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


    // if enemies is at the required place, draw it
    private void drawEnemies(GL gl,int index) {
        // 0=empty & 1=gun & (2= enemy)

        for (int i = 0; i < DJ; i++) {
            for (int j = 0; j < DI; j++) {
                if (enemy[i][j] == 2) {
//
                        drawEnemy(gl, convertX(i), convertY(j), index, 1);


                }
            }
        }
    }

    // Draws enemy at  gl.glVertex2i(convertX(x), convertY(y));
    public void drawEnemy(GL gl, int x, int y, int index, float scale) {
//        gl.glEnable(GL.GL_BLEND);
//        gl.glBindTexture(GL.GL_TEXTURE_2D, texturesG[index]);	// Turn Blending On
//
//        gl.glPushMatrix();
//        gl.glTranslated( x/(MAX_X/2.0) - 0.9, y/(MAX_Y/2.0) - 0.9, 0);
//        gl.glScaled(0.1*scale, 0.1*scale, 1);
//        gl.glRotatef(-90, 0, 0, 1);
//
//        gl.glBegin(GL.GL_QUADS);
//        // Front Face
//        gl.glTexCoord2f(0.0f, 0.0f);
//        gl.glVertex3f(-1.0f, -1.0f, -1.0f
//        gl.glTexCoord2f(1.0f, 0.0f);
//        gl.glVertex3f(1.0f, -1.0f, -1.0f);
//        gl.glTexCoord2f(1.0f, 1.0f);
//        gl.glVertex3f(1.0f, 1.0f, -1.0f);
//        gl.glTexCoord2f(0.0f, 1.0f);
//        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
//        gl.glEnd();
//        gl.glPopMatrix();
//
//        gl.glDisable(GL.GL_BLEND);

        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);    // Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated(x / (MAX_X / 2.0) - 0.9, y / (MAX_Y / 2.0) - 0.9, 0);

        gl.glScaled(0.1 * scale, 0.1 * scale, 1);
        gl.glRotated(-90, 0, 0, 1);

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

    //draw gun at     gl.glVertex2i(convertX(gunX), convertY(gunY));
    public void drawGun(GL gl, int x, int y, int index, float scale) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);    // Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated(x / (MAX_X / 2.0) - 0.9, y / (MAX_Y / 2.0) - 0.9, 0);
        gl.glScaled(0.1 * scale, 0.1 * scale, 1);

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

    private void resolveCollision(GL gl) {
        resolveBulletCollision();
        resolveGunCollision(gl);
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
                            score++;
                            break; //Ask doctor, why did we break?
                        }
                    }
                }
            }
        }
    }

    private void resolveGunCollision(GL gl) {
        for (int i = 0; i < DI; i++) {
            for (int j = 0; j < DJ; j++) {
                for (int k = Math.max(0, j - GUN_MARGIN); k < Math.min(DJ, j + GUN_MARGIN + 1); k++) /*Margin*/ {
                    if (enemy[i][k] == 2 && i == gunX /* x position of gun */ && k == gunY/* y position of gun */) {
                        System.out.println("GameOver");
                        DrawGameOver(gl);

//                        JOptionPane.showMessageDialog(null, "GameOver.", "GameOver",
//                                JOptionPane.WARNING_MESSAGE);
                        System.exit(0);
                    }
                }
            }
        }
    }

    private void generateEnemies() {
        int cnt = MAX_ENEMIES; //MAX_ENEMIES = 1
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
            for (int j = DJ - 2; j >= 0; j--) {
                if (bullet[i][j] == 1) {
                    bullet[i][j] = 0;
                    bullet[i][j + 1] = 1; //index out of bounds if i = DI -1
                }
            }
        }

        for (int i = 0; i < DJ; i++) {
            if (bullet[i][DJ - 1] == 1) {
                bullet[i][DJ - 1] = 0;
            }
        }
    }

    private void moveEnemies() {
        for (int j = 1; j < DJ; j++) {
            for (int i = 1; i < DI; i++) {

                if (enemy[i][j] == 2) {
                    enemy[i][j] = 0; // remove enemy from this position
                    enemy[i][j - 1] = 2; // move enemy to down
                    System.out.println("this is enmis");
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

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {
    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {
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
        if (gunY + 1 != DJ) {
            bullet[gunX][gunY + 1] = 1;
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    public BitSet keyBits = new BitSet(256);

    public void keyReleased(KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.clear(keyCode);
    }

}
