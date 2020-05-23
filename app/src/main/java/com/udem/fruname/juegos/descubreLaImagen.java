package com.udem.fruname.juegos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import com.udem.fruname.R;

import java.util.Random;

public class descubreLaImagen extends AppCompatActivity {

    TextView lbPregunta, lbNombreJugador, lbTiempo, lbPuntaje;
    Button btnOpcion1, btnOpcion2, btnOpcion3, btnPregunta1, btnPregunta2, btnPregunta3, btnPregunta4,
            btnPregunta5, btnPregunta6, btnPregunta7, btnPregunta8, btnPregunta9, btnPregunta10, btnPregunta11,
            btnPregunta12;
    Random random = new Random();
    CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descubre_la_imagen);
        conectar();
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
