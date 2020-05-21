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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablero);
        Conectar();
        mDataBase= FirebaseDatabase.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();
        uid=mAuth.getCurrentUser().getUid();
        actualizarDatos();
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
                                //Random random = new Random();
                                //int juego =random.nextInt(6)+1;
                                Intent intent=new Intent(tablero.this, adivinarBandera.class);
                                intent.putExtra("boton", finalI +1);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"no existe",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getDetails(),Toast.LENGTH_LONG).show();
            }
        });

    }
}
