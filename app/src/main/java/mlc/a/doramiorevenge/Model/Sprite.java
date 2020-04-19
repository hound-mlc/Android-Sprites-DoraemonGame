package mlc.a.doramiorevenge.Model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Random;

import mlc.a.doramiorevenge.Game.GameSurface;

public class Sprite {

    private static final int ROWS=4;
    private static final int COLUMNS=3;
    private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;
    private GameSurface surface;
    private Bitmap bmp;
    private int currentFrame=0;
    private int width;
    private int heigth;
    private int low=-20;
    private int high = 21;
    private boolean nobita;
    private boolean rey;
    private int life =  300;
    ArrayList<Sprite> lista;

    int[] DIRECTION = {3,1,0,2};

    public Sprite(ArrayList<Sprite> lista, GameSurface surface, Bitmap bmp, boolean nobita, boolean rey){
        this.surface=surface;
        this.bmp=bmp;
        this.width= bmp.getWidth() / COLUMNS;
        this.heigth = bmp.getHeight() / ROWS;
        Random rnd = new Random();
        x = rnd.nextInt(surface.getWidth() - (width+4));
        y = rnd.nextInt((surface.getHeight()/5*4) - (heigth+4));
        xSpeed = rnd.nextInt(21+20)-21;
        ySpeed = rnd.nextInt(21+20)-21;
        this.nobita=nobita;
        this.rey = rey;
        this.lista=lista;
    }

    public boolean isNobita(){
        return nobita;
    }

    public boolean isRey(){
        return rey;
    }

    private int getAnimationRow(){
        double dirDouble = (Math.atan2(xSpeed, ySpeed)/(Math.PI/2)+2);
        int direccion = (int) Math.round(dirDouble)%ROWS;
        return DIRECTION[direccion];
    }

    public boolean isCollition(float x2, float y2){
        return x2 > x && x2< x+width && y2 > y && y2 <y+heigth;
    }

    public float getMiddleX(){
        return Math.round(this.x+this.width/2);
    }

    public float getMiddleY(){
        return Math.round(this.y+this.heigth/2);
    }

    private void update(){

        if (x > surface.getWidth() - width - xSpeed || x + xSpeed < 0) xSpeed = -xSpeed;
        x = x + xSpeed;

        if (y>(surface.getHeight()/5)*4- heigth - ySpeed || y + ySpeed < 0) ySpeed = -ySpeed;
        y = y + ySpeed;
        currentFrame = ++currentFrame%COLUMNS;

        if (nobita){
            synchronized (surface.getHolder()){
                if (--life < 1){
                    lista.remove(this);
                    surface.nobActivo = false;
                }
            }
        }
    }

    public void onDraw(Canvas cv){
        update();
        int srcX = currentFrame * width;
        int srcY = getAnimationRow()*heigth;
        Rect src = new Rect(srcX, srcY, srcX+width, srcY+heigth);
        Rect dst = new Rect(x,y,x+width,y+heigth);
        cv.drawBitmap(bmp,src,dst,null);
    }

}
