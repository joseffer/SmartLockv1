package smartlock.josefferleite.smartlock;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TelaPergunta extends AppCompatActivity {

    private TextView txtpergunta;
    private android.support.v7.widget.AppCompatButton btnresp1;
    private android.support.v7.widget.AppCompatButton btnresp2;
    private android.support.v7.widget.AppCompatButton btnresp3;
    private android.support.design.widget.FloatingActionButton faq;
    private Button btnok;
    private DatabaseReference databaseReference;
    private List<Perguntas> arrayperguntas = new ArrayList<Perguntas>();
    int  numerobtnselecionado = 0;
    Perguntas  perguntas = new Perguntas();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_pergunta);

        if (arrayperguntas.isEmpty())
            carregarDados();
        else
            carregarinfotela();


        startLockTask();


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

                  //  Log.i("dados",perguntas.getExplicacao() + perguntas.getPergunta() + perguntas.getResp1() + perguntas.getResp2()
                  //  + perguntas.getResp3() + perguntas.getResposta_certa());

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
        int numeroaleatorio = 0;

        Random random = new Random();
        numeroaleatorio = random.nextInt(arrayperguntas.size());

        perguntas   =  arrayperguntas.get(numeroaleatorio);
        txtpergunta = (TextView) findViewById(R.id.txt_pergunta);
        btnresp1    = (android.support.v7.widget.AppCompatButton) findViewById(R.id.btn_resp1);
        btnresp2    = (android.support.v7.widget.AppCompatButton) findViewById(R.id.btn_resp2);
        btnresp3    = (android.support.v7.widget.AppCompatButton) findViewById(R.id.btn_resp3);
        faq         =   (android.support.design.widget.FloatingActionButton) findViewById(R.id.faq);
        btnok       = (Button) findViewById(R.id.btn_ok);

        txtpergunta.setText(perguntas.getPergunta());
        btnresp1.setText(perguntas.getResp1());
        btnresp2.setText(perguntas.getResp2());
        btnresp3.setText(perguntas.getResp3());

        btnresp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnresp1.setBackgroundResource(R.color.colorAccent);
                btnresp2.setBackgroundResource(R.color.colorPrimaryDark);
                btnresp3.setBackgroundResource(R.color.colorPrimaryDark);
                numerobtnselecionado = 1;

            }
        }
        );
        btnresp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnresp2.setBackgroundResource(R.color.colorAccent);
                btnresp1.setBackgroundResource(R.color.colorPrimaryDark);
                btnresp3.setBackgroundResource(R.color.colorPrimaryDark);
                numerobtnselecionado = 2;
            }
        });
        btnresp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnresp3.setBackgroundResource(R.color.colorAccent);
                btnresp2.setBackgroundResource(R.color.colorPrimaryDark);
                btnresp1.setBackgroundResource(R.color.colorPrimaryDark);
                numerobtnselecionado = 3;
            }
        });


        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarresp();
            }
        });


    faq.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            explicacao();

        }
    });

    }

   public void validarresp(){
       btnresp1  = (android.support.v7.widget.AppCompatButton) findViewById(R.id.btn_resp1);
       btnresp2  = (android.support.v7.widget.AppCompatButton) findViewById(R.id.btn_resp2);
       btnresp3  = (android.support.v7.widget.AppCompatButton) findViewById(R.id.btn_resp3);

       long date = System.currentTimeMillis();
       SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
       String dateString = sdf.format(date);

        switch (numerobtnselecionado) {
            case 1:
                if (perguntas.getResposta_certa().equals(btnresp1.getText()))
                   acerto(dateString);
                else
                  errou(dateString);
                break;
            case 2:
                if (btnresp2.getText().equals( perguntas.getResposta_certa()))
                    acerto(dateString);
                else
                    errou(dateString);
                break;
            case 3:
                if (btnresp3.getText().equals( perguntas.getResposta_certa()))
                    acerto(dateString);
                else
                    errou(dateString);
                break;
            case 0:
                Toast.makeText(this,"Escolha uma opcao",Toast.LENGTH_SHORT).show();
                break;
        }
   }

   public void acerto(final String dataatual){
       databaseReference = FirebaseDatabase.getInstance().getReference();
       databaseReference = databaseReference.child("NumResp").child(dataatual);
           databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange( DataSnapshot dataSnapshot) {

                    if (dataSnapshot.getValue() == null){
                        databaseReference.child("errada").setValue(0);
                        databaseReference.child("certo").setValue(1);
                    }else {
                        int total = Integer.parseInt(dataSnapshot.child("certo").getValue().toString()); // Carrega o valor do child "total" na variavel inteira total
                        total = total + 1;                                       // Incrementa 10 na variavel total (exemplo)
                        databaseReference.child("certo").setValue(total);
                    }
                  /* Resps_Status resps_status = dataSnapshot.getValue(Resps_Status.class);

                   //if (resps_status == null) {

                       respsStatusok = resps_status;
                       Log.i("aa",  "vazio");
                   }else {
                       respsStatusok = resps_status;
                       Log.i("aa", "vc" + resps_status.getCerto());

                   }
                   */
               }

               @Override
               public void onCancelled( DatabaseError databaseError) {

                   Log.i("naobaixao", "naobaixao");
               }
           });


       /*   if (respsStatusok == null){
            databaseReference.child("errada").setValue(0);
            databaseReference.child("certo").setValue(1);
        }else
       databaseReference.child("certo").setValue(respsStatusok.getCerto() + 1);
*/
       AlertDialog.Builder mensagem = new AlertDialog.Builder(this);
        mensagem.setTitle("Parabnes");
        mensagem.setIcon(R.drawable.carinha_feliz);
        mensagem.setMessage("Parabens Voce acerto, pode voltar a mexer no celular");
       mensagem.setPositiveButton("0k", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               stopLockTask();



               finish();
           }
       });
    mensagem.show();
   }


    public void errou(String dataatual){

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference = databaseReference.child("NumResp").child(dataatual);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange( DataSnapshot dataSnapshot) {

                    if (dataSnapshot.getValue() == null){
                        databaseReference.child("errada").setValue(1);
                        databaseReference.child("certo").setValue(0);
                    }else {

                        int total = Integer.parseInt(dataSnapshot.child("errada").getValue().toString()); // Carrega o valor do child "total" na variavel inteira total
                        total = total + 1;                                       // Incrementa 10 na variavel total (exemplo)
                        databaseReference.child("errada").setValue(total);
                    }
                    /*Resps_Status  resps_status = dataSnapshot.getValue(Resps_Status.class);
                   if (resps_status == null) {
                       respsStatusok =resps_status;
                       Log.i("aa",  "vazio");
                   }
                   else{
                    respsStatusok = resps_status;
                    Log.i("aa",  "vc" + resps_status.getErrado());
                    }*/
                }

                @Override
                public void onCancelled( DatabaseError databaseError) {

                    Log.i("naobaixao", "naobaixao");
                }
            });


     /*   if (respsStatusok == null){
            databaseReference.child("errada").setValue(1);
            databaseReference.child("certo").setValue(0);

        }else
            databaseReference.child("errada").setValue(respsStatusok.getErrado() + 1);

*/
        AlertDialog.Builder mensagem = new AlertDialog.Builder(this);
        mensagem.setTitle("ops");
        mensagem.setIcon(R.drawable.carinha_triste);
        mensagem.setMessage("Você errou"+perguntas.getExplicacao());
        mensagem.setPositiveButton("0k", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {




                carregarinfotela();
            }
        });
        mensagem.show();
    }

    public void explicacao(){
        AlertDialog.Builder mensagem = new AlertDialog.Builder(this);
        mensagem.setTitle("Explicação");
        mensagem.setIcon(R.drawable.ic_faq);
        mensagem.setMessage(perguntas.getExplicacao());
        mensagem.setPositiveButton("0k", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        mensagem.show();
    }
}

