package mlc.a.doramiorevenge;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import mlc.a.doramiorevenge.Game.GameActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.main_layout);

        Button btnJugar = findViewById(R.id.button);
        Button btnRecords = findViewById(R.id.button2);

        btnJugar.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), GameActivity.class);
            startActivity(i);
        });

        btnRecords.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), RecordsActivity.class);
            startActivity(i);
        });

    }

    @Override
    public void onBackPressed() {
        if (this.getClass().equals(MainActivity.class)) {
            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
            dialogo1.setTitle("CERRAR APLICACIÓN.");
            dialogo1.setMessage("¿Quiere cerrar la aplicación?");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    System.exit(0);
                }
            });
            dialogo1.setNegativeButton("No", null);
            dialogo1.show();
        }
        else super.onBackPressed();
    }
}
