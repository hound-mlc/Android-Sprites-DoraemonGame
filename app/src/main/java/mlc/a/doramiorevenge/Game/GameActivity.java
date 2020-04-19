package mlc.a.doramiorevenge.Game;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import mlc.a.doramiorevenge.R;

public class GameActivity extends AppCompatActivity {

    MediaPlayer music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(new GameSurface(this,getApplicationContext()));
        music = MediaPlayer.create(this, R.raw.playing);
        music.setVolume(1f,1f);
        music.start();
        music.setLooping(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        music.stop();
        finish();
    }

    public void dialogWin(final int puntos){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setTitle("¡Has ganado!. Has conseguido: "+puntos+" puntos.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                SharedPreferences sharedPreferences = getSharedPreferences("puntuaciones", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                int primero = sharedPreferences.getInt("primero",0);
                int segundo  = sharedPreferences.getInt("segundo",0);
                int tercero = sharedPreferences.getInt("tercero",0);

                if (puntos>primero){
                    editor.putInt("primero",puntos);
                    editor.putInt("segundo",primero);
                    editor.putInt("tercero",segundo);
                    editor.commit();
                }
                else if (puntos>segundo){
                    editor.putInt("segundo",puntos);
                    editor.putInt("tercero",segundo);
                    editor.commit();
                }
                else if (puntos>tercero) {
                    editor.putInt("tercero", puntos);
                    editor.commit();
                }

                music.stop();
                finish();
                onBackPressed();
            }
        });
        final AlertDialog dialogA = builder.create();
        dialogA.setView(dialogA.getLayoutInflater().inflate(R.layout.msglay, null));
        dialogA.show();
    }

    public void dialogLose(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setTitle("¡Perdiste! Has pegado a tu amigo Nobita. Has conseguido: 0 puntos.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                music.stop();
                finish();
                onBackPressed();
            }
        });
        final AlertDialog dialogA = builder.create();
        dialogA.setView(dialogA.getLayoutInflater().inflate(R.layout.msglay, null));
        dialogA.show();
    }

}
