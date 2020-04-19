package mlc.a.doramiorevenge.Model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

import mlc.a.doramiorevenge.Game.GameSurface;

public class Doramio {

    private static final int ROWS=4;
    private static final int COLUMNS=3;
    private int x=100;
    private int y=500;
    public int xSpeed;
    public int ySpeed;
    private GameSurface surface;
    private Bitmap bmp;
    private int currentFrame=0;
    private int width;
    private int heigth;

    int[] DIRECTION = {3,1,0,2};

    public Doramio(GameSurface surface, Bitmap bmp){
        this.surface=surface;
        this.bmp=bmp;
        this.width= bmp.getWidth() / COLUMNS;
        this.heigth = bmp.getHeight() / ROWS;
        Random rnd = new Random();
        xSpeed = 0;
        ySpeed = 0;
    }

    private int getAnimationRow(){
        double dirDouble = (Math.atan2(xSpeed, ySpeed)/(Math.PI/2)+2);
        int direccion = (int) Math.round(dirDouble)%ROWS;
        return DIRECTION[direccion];
    }

    public boolean isCollition(float x2, float y2){
        return x2 > x && x2< x+width && y2 > y && y2 <y+heigth;
    }

    private void update(){

        if (x > surface.getWidth() - width - xSpeed || x + xSpeed < 0) xSpeed = 0;
        x = x + xSpeed;

        if (y>(surface.getHeight()/5)*4- heigth - ySpeed || y + ySpeed < 0) ySpeed = 0;
        y = y + ySpeed;
        currentFrame = ++currentFrame%COLUMNS;
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
