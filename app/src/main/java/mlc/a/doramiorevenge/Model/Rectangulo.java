package mlc.a.doramiorevenge.Model;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Rectangulo extends Figura{

    private float largo,ancho;

    public Rectangulo(float x, float y, float largo, float ancho, int color){
        super(x,y,color);
        this.largo=largo;
        this.ancho=ancho;
    }

    public void onDraw(Paint p, Canvas canvas){
        p.setColor(this.getColor());
        p.setAntiAlias(true);
        canvas.drawRect(this.getX(),this.getY(),this.getX()+this.getAncho(),this.getY()+this.getLargo(),p);
    }

    public boolean estaDentro(float x, float y){
        if (x>=this.getX() && x<=this.getX()+this.getAncho() && y>=this.getY() && y<=this.getY()+this.getLargo()){
            return true;
        }
        return false;
    }

    public float getLargo() {
        return largo;
    }

    public void setLargo(float largo) {
        this.largo = largo;
    }

    public float getAncho() {
        return ancho;
    }

    public void setAncho(float ancho) {
        this.ancho = ancho;
    }
}
