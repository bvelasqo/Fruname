package com.udem.fruname.juegos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
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
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class descubreLaImagen extends AppCompatActivity {

    TextView lbPregunta, lbNombreJugador, lbTiempo, lbPuntaje;
    Button btnOpcion1, btnOpcion2, btnOpcion3, btnPregunta1, btnPregunta2, btnPregunta3, btnPregunta4,
            btnPregunta5, btnPregunta6, btnPregunta7, btnPregunta8, btnPregunta9, btnPregunta10, btnPregunta11,
            btnPregunta12, botonActual;
    Random random = new Random();
    int aleatorio, contestadas=0, puntos=0, scoreActual, xpActual, nivelActual, numBoton;
    String correcta, uid, nombreJugador;
    Pregunta preguntaActual;
    CountDownTimer countDownTimer;
    com.udem.fruname.juegos.operacionesDB operacionesDB;
    ArrayList<Pregunta> preguntas;
    ArrayList<Button> opciones;
    ArrayList<Button> botones;
    FirebaseAuth mAuth;
    DatabaseReference mDataBase;
    private static final int XPDI=200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descubre_la_imagen);
        conectar();
        numBoton=getIntent().getIntExtra("boton",0);
        lbPuntaje.setText("0");
        operacionesDB=new operacionesDB(getApplicationContext());
        preguntas = operacionesDB.getPreguntaDI();
        opciones = new ArrayList<>();
        botones = new ArrayList<>();
        mAuth=FirebaseAuth.getInstance();
        mDataBase= FirebaseDatabase.getInstance().getReference();
        uid=mAuth.getCurrentUser().getUid();
        traerDatos();
        lbNombreJugador.setText(nombreJugador);

        botones.add(btnPregunta1);
        botones.add(btnPregunta2);
        botones.add(btnPregunta3);
        botones.add(btnPregunta4);
        botones.add(btnPregunta5);
        botones.add(btnPregunta6);
        botones.add(btnPregunta7);
        botones.add(btnPregunta8);
        botones.add(btnPregunta9);
        botones.add(btnPregunta10);
        botones.add(btnPregunta11);
        botones.add(btnPregunta12);

        for(final Button btnPregunta : botones){
            btnPregunta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mostrarPreguntas();
                    botonActual =btnPregunta;
                    countDownTimer = new CountDownTimer(10000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            lbTiempo.setText(String.format(Locale.getDefault(), "%d sec", millisUntilFinished / 1000L));
                        }

                        @Override
                        public void onFinish() {
                            botonActual.setBackgroundColor(Color.RED);
                            botonActual.setEnabled(false);
                            lbTiempo.setText("Se termino el tiempo");
                        }
                    }.start();
                    contestadas++;
                    esCorrecta();
                    preguntas.remove(preguntaActual);
                }
            });
        }
    }

    private void esCorrecta(){
        opciones.add(btnOpcion1);
        opciones.add(btnOpcion2);
        opciones.add(btnOpcion3);
        for(final Button boton : opciones){
            boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    countDownTimer.cancel();
                    if(correcta.equals(boton.getText().toString())){
                        Toast.makeText(getApplicationContext(),"RESPUESTA CORRECTA",Toast.LENGTH_SHORT).show();
                        puntos+=100;
                        if(botonActual == btnPregunta1){
                            botonActual.setBackground(botonActual.getContext().getResources().getDrawable(R.drawable.catillo1));
                        } else if(botonActual == btnPregunta2){
                            botonActual.setBackground(botonActual.getContext().getResources().getDrawable(R.drawable.castillo2));
                        } else if(botonActual == btnPregunta3){
                            botonActual.setBackground(botonActual.getContext().getResources().getDrawable(R.drawable.castillo3));
                        } else if(botonActual == btnPregunta4){
                            botonActual.setBackground(botonActual.getContext().getResources().getDrawable(R.drawable.castillo4));
                        } else if(botonActual == btnPregunta5){
                            botonActual.setBackground(botonActual.getContext().getResources().getDrawable(R.drawable.castillo5));
                        } else if(botonActual == btnPregunta6){
                            botonActual.setBackground(botonActual.getContext().getResources().getDrawable(R.drawable.castillo6));
                        } else if(botonActual == btnPregunta7){
                            botonActual.setBackground(botonActual.getContext().getResources().getDrawable(R.drawable.castillo7));
                        } else if(botonActual == btnPregunta8){
                            botonActual.setBackground(botonActual.getContext().getResources().getDrawable(R.drawable.castillo8));
                        } else if(botonActual == btnPregunta9){
                            botonActual.setBackground(botonActual.getContext().getResources().getDrawable(R.drawable.castillo9));
                        } else if(botonActual == btnPregunta10){
                            botonActual.setBackground(botonActual.getContext().getResources().getDrawable(R.drawable.castillo10));
                        } else if(botonActual == btnPregunta11){
                            botonActual.setBackground(botonActual.getContext().getResources().getDrawable(R.drawable.castillo11));
                        } else if(botonActual == btnPregunta12){
                            botonActual.setBackground(botonActual.getContext().getResources().getDrawable(R.drawable.castillo12));
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),"INCORRECTO",Toast.LENGTH_SHORT).show();
                        botonActual.setBackgroundColor(Color.RED);
                    }
                    lbPuntaje.setText(puntos+"");
                    botonActual.setEnabled(false);
                }
            });
        }
    }

    private void mostrarPreguntas(){
        if(contestadas<12){
            aleatorio = random.nextInt(40-contestadas);
            preguntaActual = preguntas.get(aleatorio);
            correcta = preguntas.get(aleatorio).getCorrecta();
            lbPregunta.setText(preguntas.get(aleatorio).getPregunta());
            btnOpcion1.setText(preguntas.get(aleatorio).getOpcion1());
            btnOpcion2.setText(preguntas.get(aleatorio).getOpcion2());
            btnOpcion3.setText(preguntas.get(aleatorio).getOpcion3());
        } else {
            Toast.makeText(getApplicationContext(),"FIN DEL JUEGO",Toast.LENGTH_LONG).show();
            TerminarJuego();
        }

    }

    private void traerDatos(){
        mDataBase.child("Jugadores").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                scoreActual= Integer.parseInt(dataSnapshot.child("score").getValue().toString());
                xpActual=Integer.parseInt(dataSnapshot.child("xp").getValue().toString());
                nivelActual=Integer.parseInt(dataSnapshot.child("nivel").getValue().toString());
                nombreJugador = dataSnapshot.child("nombre").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void TerminarJuego() {
        Map<String,Object> datos = new HashMap();
        if(numBoton==nivelActual)
            datos.put("nivel",nivelActual+1);
        datos.put("xp",xpActual+XPDI);
        datos.put("score",scoreActual+puntos);
        mDataBase.child("Jugadores").child(uid).updateChildren(datos);
        startActivity(new Intent(descubreLaImagen.this, tablero.class));
        finish();
    }

    private void conectar() {
        lbPregunta= findViewById(R.id.lbPregunta);
        lbNombreJugador = findViewById(R.id.lbNombreJugador);
        lbTiempo = findViewById(R.id.lbTiempo);
        lbPuntaje = findViewById(R.id.lbPuntaje);
        btnOpcion1 = findViewById(R.id.btnOpcion1);
        btnOpcion2 = findViewById(R.id.btnOpcion2);
        btnOpcion3 = findViewById(R.id.btnOpcion3);
        btnPregunta1 = findViewById(R.id.btnPregunta1);
        btnPregunta2 = findViewById(R.id.btnPregunta2);
        btnPregunta3 = findViewById(R.id.btnPregunta3);
        btnPregunta4 = findViewById(R.id.btnPregunta4);
        btnPregunta5 = findViewById(R.id.btnPregunta5);
        btnPregunta6 = findViewById(R.id.btnPregunta6);
        btnPregunta7 = findViewById(R.id.btnPregunta7);
        btnPregunta8 = findViewById(R.id.btnPregunta8);
        btnPregunta9 = findViewById(R.id.btnPregunta9);
        btnPregunta10 = findViewById(R.id.btnPregunta10);
        btnPregunta11 = findViewById(R.id.btnPregunta11);
        btnPregunta12 = findViewById(R.id.btnPregunta12);
    }
}
