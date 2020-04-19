package mlc.a.doramiorevenge.Model;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Figura {

    private float x,y;
    private int color;

    public Figura(float x, float y, int color){
        this.x=x;
        this.y=y;
        this.color=color;
    }

    public int getColor(){
        return color;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void onDraw(Paint p, Canvas canvas){
    }

    public boolean estaDentro(float x, float y){
        return true;
    }

}
