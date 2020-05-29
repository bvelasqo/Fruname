package com.udem.fruname.juegos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.udem.fruname.R;
import com.udem.fruname.dbresources.DBHelper;

import java.util.ArrayList;
import java.util.Random;

public class preguntados extends AppCompatActivity {
	Button opcion1, opcion2, opcion3;
	TextView tvPreguntas;
	String categoria, correcta;
	operacionesDB operacionesDB;
	ArrayList<Pregunta> listaPreguntas;
	ArrayList<Button> opciones;
	Pregunta preguntaActual;
	int contador;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preguntados);
		conectar();
		operacionesDB = new operacionesDB(getApplicationContext());
		categoria = getIntent().getStringExtra("categoria");
		listaPreguntas = operacionesDB.getPreguntaPR(categoria);
		actualizarPregunta();
		addBotones();
	}

	private void conectar() {
		opcion1 = findViewById(R.id.btnOpcion1);
		opcion2 = findViewById(R.id.btnOpcion2);
		opcion3 = findViewById(R.id.btnOpcion3);
		tvPreguntas = findViewById(R.id.tvPregunta);
	}

	private void actualizarPregunta() {
		if (contador < 5) {
			Random random = new Random();
			int pregunta = random.nextInt(listaPreguntas.size() - contador);
			preguntaActual = listaPreguntas.get(pregunta);
			correcta = listaPreguntas.get(pregunta).getCorrecta();
			opcion1.setText(listaPreguntas.get(pregunta).getOpcion1());
			opcion2.setText(listaPreguntas.get(pregunta).getOpcion2());
			opcion3.setText(listaPreguntas.get(pregunta).getOpcion3());
		} else FinJuego();
	}

	private void FinJuego() {
	}

	private void addBotones() {
		opciones.add(opcion1);
		opciones.add(opcion2);
		opciones.add(opcion3);
		for (final Button b : opciones) {
			b.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (correcta.equals(b.getText().toString())) {
						Toast.makeText(getApplicationContext(), "¡Bien Hecho!¿Vamos por otra?", Toast.LENGTH_LONG).show();
						b.setBackground(getResources().getDrawable(R.drawable.botonacertado));
						Intent i = new Intent(getApplicationContext(),ruletaPreguntados.class);
						startActivity(i);
					} else {
						Toast.makeText(getApplicationContext(), "No te desanimes juega otra vez!!, la respuesta es: " + correcta, Toast.LENGTH_LONG).show();
						b.setBackground(getResources().getDrawable(R.drawable.botonincorrecto));
						Intent i = new Intent(getApplicationContext(),ruletaPreguntados.class);
						startActivity(i);
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
							contador++;
							listaPreguntas.remove(preguntaActual);
							Intent i = new Intent(getApplicationContext(),ruletaPreguntados.class);
							startActivity(i);
						}
					}.start();
				}
			});
		}
	}

	private void enabledButton(boolean b) {
		opcion1.setEnabled(b);
		opcion2.setEnabled(b);
		opcion3.setEnabled(b);
	}
}
