package mlc.a.doramiorevenge.Game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class ThreadCV extends Thread {

    private SurfaceHolder sh;
    private GameSurface view;
    private boolean run;
    static final long FPS=20;

    public ThreadCV(SurfaceHolder sh, GameSurface view) {
        this.sh=sh;
        this.view=view;
        run=false;
    }

    public void setRunning(boolean run) {
        this.run=run;
    }

    public void run() {
        Canvas cv;
        long tickPS = 1000 / FPS;
        long startTime;
        long sleepTime;

        while(run) {
            cv = null;
            startTime = System.currentTimeMillis();

            try {

                cv = sh.lockCanvas(null);
                if (cv!=null) {
                    synchronized (sh) {
                        view.postInvalidate();
                    }
                }

            } finally {
                if (cv!=null) sh.unlockCanvasAndPost(cv);
            }
            sleepTime = tickPS - (System.currentTimeMillis() - startTime);

            try {
                if (sleepTime > 0) sleep(sleepTime);
                else sleep(10);
            }
            catch (Exception e){}
        }
    }

}
