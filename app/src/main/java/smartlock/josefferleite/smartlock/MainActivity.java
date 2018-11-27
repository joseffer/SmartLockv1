package smartlock.josefferleite.smartlock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    private String[] tempostring =new String[]{"1 Minuto","5 Minutos" ,"10 Minutos", "15 Minutos",
            "20 Minutos", "30 Minutos","40 Minutos",
            "50 Minutos","1 hora"};
    private int[] tempomilliseg = new int[]{60000, 300000, 600000,
            900000, 1200000, 1800000, 2400000, 3000000, 3600000};
  private   Button aceitar;
  private   Spinner sp;
  private   int valormilli;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter <String>  adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,tempostring);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp = (Spinner) findViewById(R.id.sp_tempo);
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int postion, long id) {
                valormilli = postion;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        aceitar = (Button) findViewById(R.id.btn_confirmar);
        aceitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alarme_ativar(tempomilliseg[valormilli]);
            }
        });

    }


    public void Alarme_ativar(int tempoemmilli){
        final boolean alarmeAtivo = (PendingIntent.getBroadcast(this, 0 ,new Intent("chamar"),PendingIntent.FLAG_NO_CREATE) == null);

        if (alarmeAtivo){
            Log.i("alarme","novo alarme");
            Intent intent = new Intent("chamar");//nome da action personalizada chamada pelo android manifest
            PendingIntent p = PendingIntent.getBroadcast(this, 0, intent, 0); //deixa a intetente pedente
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(System.currentTimeMillis());// tempo atual do systema em mile segundos
           // c.add(Calendar.SECOND, 3); // tempo de delay para aparecer a aplicacao
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), tempoemmilli, p);
            Log.i("alarme","Funfou");
        }else{
            Log.i("alarme","alarme ativo");
        }
    }
}
