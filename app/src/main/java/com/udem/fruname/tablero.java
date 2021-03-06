package com.udem.fruname;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udem.fruname.juegos.adivinarBandera;
import com.udem.fruname.juegos.descubreLaImagen;
import com.udem.fruname.juegos.ruletaPreguntados;

import java.util.Random;

public class tablero extends AppCompatActivity {
    String uid,nombre;
    int nivel,score,xp;
    private DatabaseReference mDataBase;
    private FirebaseAuth mAuth;
    TextView tvScore,tvXP,tvNombre,tvNivel;
    Button btnCerrarSesion;
    LinearLayout layoutBotones;
    LinearLayout.LayoutParams layoutParams;
    private int scoreactual;
    private int xpActual;
    private int nivelActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablero);
        Conectar();
        mDataBase= FirebaseDatabase.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();
        uid=mAuth.getCurrentUser().getUid();
        actualizarDatos();
        traerDatos();
    }

    public void cerrarSesion(View view){
        mAuth.signOut();
        startActivity(new Intent(tablero.this,login.class));
        finish();
    }

    private void Conectar() {
        tvScore=findViewById(R.id.tvScore);
        tvNivel=findViewById(R.id.tvNivel);
        tvXP=findViewById(R.id.tvXP);
        tvNombre=findViewById(R.id.tvNombre);
        btnCerrarSesion=findViewById(R.id.btnCerrarSesion);
        layoutBotones=findViewById(R.id.layoutBotones);
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }


    private void actualizarDatos(){
        mDataBase.child("Jugadores").child(uid).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                     nombre=dataSnapshot.child("nombre").getValue().toString();
                     score= Integer.parseInt(dataSnapshot.child("score").getValue().toString());
                     xp=Integer.parseInt(dataSnapshot.child("xp").getValue().toString());
                     nivel=Integer.parseInt(dataSnapshot.child("nivel").getValue().toString());
                     tvXP.setText(xp+"");
                     tvNivel.setText("Nivel "+nivel);
                     tvNombre.setText(nombre);
                     tvScore.setText(score+"");
                    for (int i=0;i<nivel;i++){
                        Button button = new Button(getApplicationContext());
                        button.setLayoutParams(layoutParams);
                        button.setBackground(getResources().getDrawable(R.drawable.botonesalternativo));
                        button.setText("Nivel "+(i+1));
                        button.setTextColor(getResources().getColor(R.color.colorAccent));
                        layoutBotones.addView(button);
                        final int finalI = i;
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Random random = new Random();
                                int juego =random.nextInt(3);

                                switch (juego){
                                    case 0:
                                        Intent intent=new Intent(tablero.this, descubreLaImagen.class);
                                        intent.putExtra("boton", finalI +1);
                                        startActivity(intent);
                                        finish();
                                        break;
                                    case 1:
                                        Intent intent2=new Intent(tablero.this, ruletaPreguntados.class);
                                        intent2.putExtra("scoreA",scoreactual);
                                        intent2.putExtra("xpA",xpActual);
                                        intent2.putExtra("nivelA",nivelActual);
                                        intent2.putExtra("boton", finalI +1);
                                        startActivity(intent2);
                                        finish();
                                        break;
                                    case 2:
                                        Intent intent3=new Intent(tablero.this, adivinarBandera.class);
                                        intent3.putExtra("boton", finalI +1);
                                        startActivity(intent3);
                                        finish();
                                        break;
                                }


                            }
                        });
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"no existe",Toast.LENGTH_LONG).show();
                }
                Button button = new Button(getApplicationContext());
                button.setLayoutParams(layoutParams);
                button.setBackground(getResources().getDrawable(R.drawable.botoninvisible));
                layoutBotones.addView(button);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getDetails(),Toast.LENGTH_LONG).show();
            }
        });

    }
    private void traerDatos() {
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
}
