package com.udem.fruname.juegos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udem.fruname.R;
import com.udem.fruname.tablero;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ruletaPreguntados extends AppCompatActivity {
Button btnGirar;
ImageView ruleta;
Random r;
int degree = 0, degreeOld = 0;
private static final float FACTOR = 45f;
    int contador, numBoton,nivelActual,xpActual,scoreactual,puntos;
    private static final int XPAB=180;
    DatabaseReference mDataBase;
    FirebaseAuth mAuth;
    private String uid;
    private int contadorBuenas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruleta_preguntados);
        conectar();
        mAuth = FirebaseAuth.getInstance();
        mDataBase= FirebaseDatabase.getInstance().getReference();
        uid=mAuth.getCurrentUser().getUid();
        contadorBuenas=getIntent().getIntExtra("contB",0);
        numBoton=getIntent().getIntExtra("boton",0);
        puntos = getIntent().getIntExtra("puntos",0);
        contador = getIntent().getIntExtra("contar",0);
        scoreactual=getIntent().getIntExtra("scoreA",0);
        xpActual=getIntent().getIntExtra("xpA",0);
        nivelActual=getIntent().getIntExtra("nivelA",0);
        r = new Random();
        if (contador>= 2){
            FinJuego();
        }

        btnGirar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                degreeOld = degree % 360;
                degree = r.nextInt(3600)+720;
                RotateAnimation rotar = new RotateAnimation(degreeOld,degree,
                RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
                rotar.setDuration(3600);
                rotar.setFillAfter(true);
                rotar.setInterpolator(new DecelerateInterpolator());
                rotar.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent i = new Intent(getApplicationContext(),preguntados.class);
                        String n=NumeroActual(360 - (degree % 360));
                        i.putExtra("categoria",n);
                        i.putExtra("puntos",puntos);
                        i.putExtra("contar",contador);
                        i.putExtra("scoreA",scoreactual);
                        i.putExtra("xpA",xpActual);
                        i.putExtra("nivelA",nivelActual);
                        i.putExtra("boton",numBoton);
                        i.putExtra("contB",contadorBuenas);

                        startActivity(i);
                        finish();
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                ruleta.startAnimation(rotar);
            }
        });
    }


    private void FinJuego() {
        Map<String,Object> datos = new HashMap();
        if(numBoton==nivelActual&&contadorBuenas>=2)
            datos.put("nivel",nivelActual+1);
        datos.put("xp",xpActual+XPAB);
        datos.put("score",scoreactual+puntos);
        mDataBase.child("Jugadores").child(uid).updateChildren(datos);
        startActivity(new Intent(ruletaPreguntados.this, tablero.class));
        finish();
    }

    private String NumeroActual(int degree){
        String text = "";

        if(degree >= FACTOR && degree < FACTOR*3){
            text = "DEPORTE";
        }
        if(degree >= FACTOR*3 && degree < FACTOR*5){
            text = "CULTURA";
        }
        if(degree >= FACTOR*5 && degree < FACTOR*7){
            text = "GEOGRAFIA";
        }
        if(degree >= FACTOR*7 && degree < FACTOR*9){
            text = "HISTORIA";
        }
        if(degree >= FACTOR*3 && degree < 360 || degree >= 0 && degree < FACTOR*1)
            text="CULTURA";
        return text;
    }

    private void conectar() {
        btnGirar = findViewById(R.id.button);
        ruleta = findViewById(R.id.imageView7);
    }
}
