package com.udem.fruname.juegos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import com.udem.fruname.dbresources.DBHelper;
import com.udem.fruname.tablero;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class preguntados extends AppCompatActivity {
	Button opcion1, opcion2, opcion3;
	TextView tvPreguntas,tvscore,tvnombre;
	String categoria, correcta,uid,nombre;
	operacionesDB operacionesDB;
	FirebaseAuth mAuth;
	ArrayList<Pregunta> listaPreguntas;
	ArrayList<Button> opciones;
	DatabaseReference mDataBase;
	int contadorBuenas;

	Pregunta preguntaActual;
    int contador, numBoton,nivelActual,xpActual,scoreactual,puntos;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preguntados);
		conectar();
		opciones=new ArrayList<>();
		mAuth = FirebaseAuth.getInstance();
		mDataBase= FirebaseDatabase.getInstance().getReference();
		uid=mAuth.getCurrentUser().getUid();
		operacionesDB = new operacionesDB(getApplicationContext());
		numBoton=getIntent().getIntExtra("boton",0);
        contadorBuenas=getIntent().getIntExtra("contB",0);
		categoria = getIntent().getStringExtra("categoria");
		puntos = getIntent().getIntExtra("puntos",0);
		contador = getIntent().getIntExtra("contar",0);
        scoreactual=getIntent().getIntExtra("scoreA",0);
        xpActual=getIntent().getIntExtra("xpA",0);
        nivelActual=getIntent().getIntExtra("nivelA",0);
		listaPreguntas = operacionesDB.getPreguntaPR(categoria);
        tvscore.setText(puntos+"");
        actualizarPregunta();
		addBotones();
	}



	private void conectar() {
		opcion1 = findViewById(R.id.btnOpcion1);
		opcion2 = findViewById(R.id.btnOpcion2);
		opcion3 = findViewById(R.id.btnOpcion3);
		tvPreguntas = findViewById(R.id.tvPregunta);
		tvscore = findViewById(R.id.tvscorePR);
		tvnombre = findViewById(R.id.tvNombrePR);
	}

	private void actualizarPregunta() {
		if (contador < 5) {
            Random random = new Random();
            int pregunta = random.nextInt(listaPreguntas.size() - contador);
            preguntaActual = listaPreguntas.get(pregunta);
            tvPreguntas.setText(preguntaActual.getPregunta());
            correcta = listaPreguntas.get(pregunta).getCorrecta();
            opcion1.setText(listaPreguntas.get(pregunta).getOpcion1());
            opcion2.setText(listaPreguntas.get(pregunta).getOpcion2());
            opcion3.setText(listaPreguntas.get(pregunta).getOpcion3());
        }
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
                        contadorBuenas++;
                        puntos+=200;
                    } else {
                        Toast.makeText(getApplicationContext(), "No te desanimes juega otra vez!!, la respuesta es: " + correcta, Toast.LENGTH_LONG).show();
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
                            contador++;
                            listaPreguntas.remove(preguntaActual);
                            Intent i = new Intent(getApplicationContext(),ruletaPreguntados.class);
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
                    }.start();
                }
            });

			b.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (correcta.equals(b.getText().toString())) {
						Toast.makeText(getApplicationContext(), "¡Bien Hecho!¿Vamos por otra?", Toast.LENGTH_LONG).show();
						b.setBackground(getResources().getDrawable(R.drawable.botonacertado));
						puntos+=200;
					} else {
						Toast.makeText(getApplicationContext(), "No te desanimes juega otra vez!!, la respuesta es: " + correcta, Toast.LENGTH_LONG).show();
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
							contador++;
							listaPreguntas.remove(preguntaActual);
							Intent i = new Intent(getApplicationContext(),ruletaPreguntados.class);
							i.putExtra("puntos",puntos);
							i.putExtra("contar",contador);
							startActivity(i);
							finish();
							tvscore.setText(puntos+"");
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
