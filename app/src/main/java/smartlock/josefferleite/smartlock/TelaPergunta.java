package smartlock.josefferleite.smartlock;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TelaPergunta extends AppCompatActivity {

    private TextView txtpergunta;
    private android.support.v7.widget.AppCompatButton btnresp1;
    private android.support.v7.widget.AppCompatButton btnresp2;
    private android.support.v7.widget.AppCompatButton btnresp3;
    private DatabaseReference databaseReference;
    private List<Perguntas> arrayperguntas = new ArrayList<Perguntas>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        carregarDados();
        startLockTask();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_pergunta);


          //  carregarDados();
            Log.i("carregamento","Passei");





     }

    public void  carregarDados(){


        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference = databaseReference.child("Perguntas").child("Matematica");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //limpar lista de perguntas
                arrayperguntas.clear();
                //lista de perguntas
                for (DataSnapshot dados: dataSnapshot.getChildren()){

                    Perguntas perguntas = dados.getValue(Perguntas.class);

                    Log.i("dados",perguntas.getExplicacao() + perguntas.getPergunta() + perguntas.getResp1() + perguntas.getResp2()
                    + perguntas.getResp3() + perguntas.getResposta_certa());

                    arrayperguntas.add(perguntas);

                    Log.i("array", String.valueOf((arrayperguntas.size())));
                }
                carregarinfotela();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("naobaixao","naobaixao");
            }
        });


    }


    public void carregarinfotela() {

        Random random = new Random();
        int numeroaleatorio = random.nextInt(arrayperguntas.size());
        Perguntas perguntas = new Perguntas();
        perguntas  =  arrayperguntas.get(numeroaleatorio);
        txtpergunta = (TextView) findViewById(R.id.txt_pergunta);
        btnresp1  = (android.support.v7.widget.AppCompatButton) findViewById(R.id.btn_resp1);
        btnresp2  = (android.support.v7.widget.AppCompatButton) findViewById(R.id.btn_resp2);
        btnresp3  = (android.support.v7.widget.AppCompatButton) findViewById(R.id.btn_resp3);

        txtpergunta.setText(perguntas.getPergunta());
        btnresp1.setText(perguntas.getResp1());
        btnresp2.setText(perguntas.getResp2());
        btnresp3.setText(perguntas.getResp3());
    }
}
