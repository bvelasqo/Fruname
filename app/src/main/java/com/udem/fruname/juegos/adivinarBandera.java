package com.udem.fruname.juegos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udem.fruname.R;
import com.udem.fruname.tablero;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class adivinarBandera extends AppCompatActivity {

    ImageView bandera;
    Button op1,op2,op3,op4;
    TextView score;
    String correcta,uid;
    com.udem.fruname.juegos.operacionesDB operacionesDB;
    int contadorContestadas=0,puntos=0,scoreactual,xpActual,nivelActual,numBoton;
    FirebaseAuth mAuth;
    DatabaseReference mDataBase;
    private static final int XPAB=180;
    ArrayList<Pregunta> preguntas;
    Pregunta preguntaActual;
    ArrayList<Button> opciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adivinar_bandera);
        Conectar();
        score.setText("0");
        numBoton=getIntent().getIntExtra("boton",0);
        opciones=new ArrayList<>();
        mAuth=FirebaseAuth.getInstance();
        mDataBase= FirebaseDatabase.getInstance().getReference();
        uid=mAuth.getCurrentUser().getUid();
        operacionesDB=new operacionesDB(getApplicationContext());
        preguntas=operacionesDB.getPreguntaAB();
        traerDatos();
        actualizarPregunta();
        addBotones();
    }


    private void addBotones(){
        opciones.add(op1);
        opciones.add(op2);
        opciones.add(op3);
        opciones.add(op4);
        for (final Button b:opciones){
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(correcta.equals(b.getText().toString())){
                        Toast.makeText(getApplicationContext(),"¡Acertaste!",Toast.LENGTH_LONG).show();
                        b.setBackground(getResources().getDrawable(R.drawable.botonacertado));
                        puntos+=200;
                    }else{
                        Toast.makeText(getApplicationContext(),"Respuesta equivocada era: "+correcta,Toast.LENGTH_LONG).show();
                        b.setBackground(getResources().getDrawable(R.drawable.botonincorrecto));
                    }
                    enabledButton(false);
                    new CountDownTimer(2000,1000){
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            enabledButton(true);
                            b.setBackground(getResources().getDrawable(R.drawable.botonesalternativo));
                            contadorContestadas++;
                            preguntas.remove(preguntaActual);
                            actualizarPregunta();
                            score.setText(puntos+"");
                        }
                    }.start();
                }
            });
        }
    }

    private void actualizarPregunta(){
        if(contadorContestadas<5){
            Random random = new Random();
            int pregunta= random.nextInt(20-contadorContestadas);
            preguntaActual=preguntas.get(pregunta);
            correcta=preguntas.get(pregunta).getCorrecta();
            ActualizarBandera();
            op1.setText(preguntas.get(pregunta).getOpcion1());
            op2.setText(preguntas.get(pregunta).getOpcion2());
            op3.setText(preguntas.get(pregunta).getOpcion3());
            op4.setText(preguntas.get(pregunta).getOpcion4());
        }
        else{
            Finalizar();
        }
    }

    private void enabledButton(boolean a){
        op1.setEnabled(a);
        op2.setEnabled(a);
        op3.setEnabled(a);
        op4.setEnabled(a);
    }

    private void traerDatos(){
        mDataBase.child("Jugadores").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                scoreactual= Integer.parseInt(dataSnapshot.child("score").getValue().toString());
                xpActual=Integer.parseInt(dataSnapshot.child("xp").getValue().toString());
                nivelActual=Integer.parseInt(dataSnapshot.child("nivel").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void Finalizar() {
        Map<String,Object> datos = new HashMap();
        if(numBoton==nivelActual)
            datos.put("nivel",nivelActual+1);
        datos.put("xp",xpActual+XPAB);
        datos.put("score",scoreactual+puntos);
        mDataBase.child("Jugadores").child(uid).updateChildren(datos);
        startActivity(new Intent(adivinarBandera.this, tablero.class));
        finish();
    }

    private void Conectar() {
        bandera=findViewById(R.id.imgBanderaAB);
        op1=findViewById(R.id.btnOpcion1AB);
        op2=findViewById(R.id.btnOpcion2AB);
        op3=findViewById(R.id.btnOpcion3AB);
        op4=findViewById(R.id.btnOpcion4AB);
        score=findViewById(R.id.tvScoreAB);
    }

    private void ActualizarBandera(){
        switch (correcta){
            case "Colombia":
                bandera.setImageResource(R.drawable.colombia);
                break;
            case "Brasil":
                bandera.setImageResource(R.drawable.brasil);
                break;
            case "Italia":
                bandera.setImageResource(R.drawable.italia);
                break;
            case "Ecuador":
                bandera.setImageResource(R.drawable.ecuador);
                break;
            case "Ghana":
                bandera.setImageResource(R.drawable.ghana);
                break;
            case "Honduras":
                bandera.setImageResource(R.drawable.honduras);
                break;
            case "Serbia":
                bandera.setImageResource(R.drawable.serbia);
                break;
            case "Emiratos Árabes":
                bandera.setImageResource(R.drawable.emiratos_arabes_unidos);
                break;
            case "Camerún":
                bandera.setImageResource(R.drawable.camerun);
                break;
            case "Australia":
                bandera.setImageResource(R.drawable.australia);
                break;
            case "Irlanda del Norte":
                bandera.setImageResource(R.drawable.irlanda_del_norte);
                break;
            case "Andorra":
                bandera.setImageResource(R.drawable.andorra);
                break;
            case "Chile":
                bandera.setImageResource(R.drawable.chile);
                break;
            case "Japon":
                bandera.setImageResource(R.drawable.japon);
                break;
            case "Antioquia":
                bandera.setImageResource(R.drawable.antioquia);
                break;
            case "Unión soviética":
                bandera.setImageResource(R.drawable.union_sovietica);
                break;
            case "Argentina":
                bandera.setImageResource(R.drawable.argentina);
                break;
            case "Holanda":
                bandera.setImageResource(R.drawable.holanda);
                break;
            case "México":
                bandera.setImageResource(R.drawable.mexico);
                break;
        }
    }
}
