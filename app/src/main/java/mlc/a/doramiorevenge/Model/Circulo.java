package mlc.a.doramiorevenge.Model;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Circulo extends Figura {

    private float radio;

    public Circulo(float x, float y, float radio, int color){
        super(x,y,color);
        this.radio=radio;
    }

    public float getRadio() {
        return radio;
    }

    public void setRadio(float radio) {
        this.radio = radio;
    }

    @Override
    public void onDraw(Paint p, Canvas canvas) {
        p.setColor(this.getColor());
        p.setAntiAlias(true);
        canvas.drawCircle(this.getX(),this.getY(),this.getRadio(),p);
    }

    @Override
    public boolean estaDentro(float x, float y) {
        float distanciaX = x -this.getX();
        float distanciaY = y -this.getY();
        if (Math.pow(this.getRadio(),2)>(Math.pow(distanciaX,2)+Math.pow(distanciaY,2))){
            return true;
        }
        return false;
    }

}
