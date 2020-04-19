package mlc.a.doramiorevenge.Game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mlc.a.doramiorevenge.Model.Circulo;
import mlc.a.doramiorevenge.Model.Doramio;
import mlc.a.doramiorevenge.Model.Rectangulo;
import mlc.a.doramiorevenge.Model.Sprite;
import mlc.a.doramiorevenge.Model.TempSprite;
import mlc.a.doramiorevenge.R;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback{

    ThreadCV hilo;
    Sprite img1;
    Bitmap pic;
    int puntos=0;
    int ratasGrandesMuertas=0;
    Bitmap tomb;
    Bitmap cruceta;
    Bitmap boton;
    Bitmap mapa;
    Bitmap backpad;
    private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
    private List<TempSprite> spritesTemporales = new ArrayList<TempSprite>();
    Doramio doraemon;
    long lastClick;
    float limiteGamepad;
    Rectangulo top;
    Rectangulo left;
    Rectangulo bottom;
    Rectangulo right;
    Circulo mach;
    float udX;
    float udY;
    Random rnd = new Random();
    public boolean nobActivo = false;
    GameActivity activity;
    static SoundPool soundPool;
    static int[] sm;

    public GameSurface(GameActivity activity, Context context){
        super(context);
        setBackgroundColor(Color.WHITE);
        getHolder().addCallback(this);
        this.activity=activity;
        InitSound(context);
    }

    @Override
    public void onDraw(Canvas canvas) {
        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.WHITE);
        canvas.drawPaint(p);


        canvas.drawBitmap(mapa,0,0,p);
        canvas.drawBitmap(backpad,0,limiteGamepad,p);

        /*p.setAlpha(70);
        p.setColor(Color.rgb(156,125,114));
        canvas.drawRect(0,limiteGamepad,getWidth(),getHeight(),p);*/

        p.setColor(Color.BLACK);
        p.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        p.setTextSize(60);
        canvas.drawText("Puntos = "+puntos,30,80,p);
        canvas.drawText("Reyes = "+ratasGrandesMuertas,getWidth()/3*2,80,p);
        canvas.drawBitmap(cruceta,udX,limiteGamepad,p);
        canvas.drawBitmap(boton,udX*7,limiteGamepad+udY,p);
        mach.onDraw(p,canvas);
        top.onDraw(p,canvas);
        bottom.onDraw(p,canvas);
        right.onDraw(p,canvas);
        left.onDraw(p,canvas);


        synchronized (getHolder()){
            for (int i=spritesTemporales.size()-1;i>=0;i--){
                spritesTemporales.get(i).onDraw(canvas);
            }
        }


        synchronized (getHolder()){
            doraemon.onDraw(canvas);
            for (int i=sprites.size()-1;i>=0;i--){
                sprites.get(i).onDraw(canvas);
            }
        }

        synchronized (getHolder()){
            if (sprites.size()==3){
                createSprites();
            }
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        /*if (System.currentTimeMillis() - lastClick > 500){
            lastClick = System.currentTimeMillis();
            for (int i = sprites.size()-1;i>=0;i--){
                if (sprites.get(i).isCollition(event.getX(),event.getY())){
                    sprites.remove(sprites.get(i));

                    break;
                }
            }
        }*/

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                if (top.estaDentro(event.getX(),event.getY())) {
                    doraemon.xSpeed = 0;
                    doraemon.ySpeed = -21;
                }
                else if (bottom.estaDentro(event.getX(),event.getY())){
                    doraemon.xSpeed = 0;
                    doraemon.ySpeed = 21;
                }
                else if (left.estaDentro(event.getX(),event.getY())){
                    doraemon.ySpeed = 0;
                    doraemon.xSpeed = -21;
                }
                else if (right.estaDentro(event.getX(),event.getY())){
                    doraemon.ySpeed = 0;
                    doraemon.xSpeed = 21;
                }

                if (mach.estaDentro(event.getX(),event.getY())){
                    synchronized (getHolder()){
                        for (int i=sprites.size()-1;i>=0;i--){
                            if (doraemon.isCollition(sprites.get(i).getMiddleX(),sprites.get(i).getMiddleY())){
                                spritesTemporales.add(new TempSprite(spritesTemporales,this,sprites.get(i).getMiddleX(),sprites.get(i).getMiddleY(),tomb));

                                if (!sprites.get(i).isNobita() && !sprites.get(i).isRey()) {
                                    puntos+=5;
                                    playSound(0);
                                }
                                else if (sprites.get(i).isRey()){
                                    puntos+=20;
                                    ratasGrandesMuertas++;
                                    playSound(0);
                                    if (ratasGrandesMuertas == 3){
                                        activity.dialogWin(puntos);
                                        activity.music.stop();
                                        playSound(1);
                                    }
                                }
                                else if (sprites.get(i).isNobita()){
                                    playSound(2);
                                    activity.music.stop();
                                    activity.dialogLose();
                                }

                                synchronized (getHolder()){
                                    sprites.remove(sprites.get(i));
                                }
                                Random rnd = new Random();
                                int num = rnd.nextInt(11);
                                if (num==1) {
                                    synchronized (getHolder()) {
                                        createKing();
                                    }
                                }
                                else if (num == 2 || num == 4 || num == 6){
                                    synchronized (getHolder()) {
                                        if (!nobActivo) {
                                            createNobita();
                                            nobActivo = true;
                                        }
                                    }
                                }
                                else {
                                    synchronized (getHolder()) {
                                        createSprites();
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                doraemon.ySpeed = 0;
                doraemon.xSpeed = 0;
                break;
        }
        return true;
    }



    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        hilo = new ThreadCV(getHolder(),this);
        hilo.setRunning(true);
        hilo.start();
        synchronized (getHolder()) {
            createSprites();
            createSprites();
            createSprites();
            createSprites();
        }
        tomb = BitmapFactory.decodeResource(getResources(), R.drawable.tomb);
        limiteGamepad = getHeight()/5*4;
        udX = getWidth()/12+15;
        udY = (getHeight()/5)/5;
        top = new Rectangulo(udX*2+34,limiteGamepad+21,udY*2-20,udX+40,Color.TRANSPARENT);
        bottom = new Rectangulo(udX*2+34,limiteGamepad+(udY*3),udY*2-20,udX+40,Color.TRANSPARENT);
        left = new Rectangulo(udX+19,limiteGamepad+(udY*2)-28,udY+54,udX+40,Color.TRANSPARENT);
        right = new Rectangulo(udX*3+47,limiteGamepad+(udY*2)-28,udY+54,udX+40,Color.TRANSPARENT);

        Bitmap cruz = BitmapFactory.decodeResource(getResources(),R.drawable.pad);
        cruceta = cruz.createScaledBitmap(cruz,(int)udX*4,(int)udY*5,true);
        Bitmap circulo = BitmapFactory.decodeResource(getResources(),R.drawable.machbutton);
        boton = circulo.createScaledBitmap(circulo,(int)udX*2+30,(int)udY*3,true);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.drawable.doramio);
        doraemon = new Doramio(this,bmp);
        mach = new Circulo(udX*8+15,limiteGamepad+udY+110,127, Color.TRANSPARENT);
        Bitmap mapita = BitmapFactory.decodeResource(getResources(),R.drawable.map);
        mapa = mapita.createScaledBitmap(mapita,getWidth(),(int)limiteGamepad,true);
        Bitmap detrasBack = BitmapFactory.decodeResource(getResources(),R.drawable.backpad);
        backpad = mapita.createScaledBitmap(detrasBack,getWidth(),getHeight()/5,true);
    }

    private Sprite createSprite(int resource, boolean nobita, boolean rey){
        Bitmap bmp = BitmapFactory.decodeResource(getResources(),resource);
        return new Sprite(sprites,this,bmp,nobita,rey);
    }

    private void createSprites(){
        sprites.add(createSprite(R.drawable.rat,false,false));
    }

    private void createKing(){
        sprites.add(createSprite(R.drawable.fatrats,false,true));
    }

    private void createNobita(){
        sprites.add(createSprite(R.drawable.nobita3,true,false));
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean retry = true;

        hilo.setRunning(false);
        while (retry) {
            try {
                hilo.join();
                retry = false;
            }
            catch (InterruptedException e) {

            }
        }
    }

    public static void InitSound(Context context) {

        int maxStreams = 1;
        Context mContext = (context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(maxStreams)
                    .build();
        } else {
            soundPool = new SoundPool(maxStreams, AudioManager.STREAM_MUSIC, 0);
        }

        sm = new int[3];
        sm[0] = soundPool.load(mContext, R.raw.kill, 1);
        sm[1] = soundPool.load(mContext, R.raw.win, 1);
        sm[2] = soundPool.load(mContext, R.raw.doramio, 1);

    }

    static void playSound(int sound) {
        soundPool.play(sm[sound], 1, 1, 1, 0, 1f);
    }

}
