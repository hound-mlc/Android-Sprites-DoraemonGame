package mlc.a.doramiorevenge;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecordsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.lay_points);

        SharedPreferences sharedPreferences = getSharedPreferences("puntuaciones", Context.MODE_PRIVATE);
        int primero = sharedPreferences.getInt("primero",0);
        int segundo  = sharedPreferences.getInt("segundo",0);
        int tercero = sharedPreferences.getInt("tercero",0);

        TextView txtPrimero = findViewById(R.id.txtPrimero);
        TextView txtSegundo = findViewById(R.id.txtSegundo);
        TextView txtTercero = findViewById(R.id.txtTercero);

        txtPrimero.setText(primero+"");
        txtSegundo.setText(segundo+"");
        txtTercero.setText(tercero+"");
    }
}
