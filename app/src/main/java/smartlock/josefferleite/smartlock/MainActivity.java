package smartlock.josefferleite.smartlock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    Button aceitar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        aceitar = (Button) findViewById(R.id.btn_confirmar);
        aceitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alarme_ativar();
            }
        });

    }


    public void Alarme_ativar(){
        final boolean alarmeAtivo = (PendingIntent.getBroadcast(this, 0 ,new Intent("chamar"),PendingIntent.FLAG_NO_CREATE) == null);

        if (alarmeAtivo){
            Log.i("alarme","novo alarme");
            Intent intent = new Intent("chamar");//nome da action personalizada chamada pelo android manifest
            PendingIntent p = PendingIntent.getBroadcast(this, 0, intent, 0); //deixa a intetente pedente
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(System.currentTimeMillis());// tempo atual do systema em mile segundos
           // c.add(Calendar.SECOND, 3); // tempo de delay para aparecer a aplicacao
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 200, p);
        }else{
            Log.i("alarme","alarme ativo");
        }
    }
}
