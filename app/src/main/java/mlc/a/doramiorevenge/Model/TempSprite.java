package mlc.a.doramiorevenge.Model;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.List;

import mlc.a.doramiorevenge.Game.GameSurface;

public class TempSprite {

    private float x;
    private float y;
    private Bitmap bmp;
    private int life = 40;
    private List<TempSprite> temps;

    public TempSprite(List<TempSprite> temps, GameSurface surface, float x, float y, Bitmap bmp){
        this.x = Math.min(Math.max(x - bmp.getWidth() / 2, 0),surface.getWidth()-bmp.getWidth());
        this.y = Math.min(Math.max(y - bmp.getHeight() / 2,0),surface.getHeight()-bmp.getHeight());
        this.bmp=bmp;
        this.temps=temps;
    }

    public void onDraw(Canvas canvas){
        update();
        canvas.drawBitmap(bmp,x,y,null);
    }

    public void update(){
        if (--life < 1){
            temps.remove(this);
        }
    }

}
