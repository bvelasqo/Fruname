package com.udem.fruname.juegos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.udem.fruname.R;

import java.util.Random;

public class ruletaPreguntados extends AppCompatActivity {
Button btnGirar;
ImageView ruleta;
TextView tvAngulo;
Random r;
int degree = 0, degreeOld = 0;
private static final float FACTOR = 45f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruleta_preguntados);
        conectar();
        r = new Random();
        btnGirar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                degreeOld = degree % 360;
                degree = r.nextInt(3600)+720;
                RotateAnimation rotar = new RotateAnimation(degreeOld,degree,
                        RotateAnimation.RELATIVE_TO_SELF,0.5f);
                rotar.setDuration(3600);
                rotar.setFillAfter(true);
                rotar.setInterpolator(new DecelerateInterpolator());
                rotar.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        tvAngulo.setText(NumeroActual(360 - (degree % 360)));
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                ruleta.startAnimation(rotar);
            }
        });
    }

    private String NumeroActual(int degree){
        String text = "";

        if(degree >= FACTOR && degree < FACTOR*3){
            text = "Deporte";
        }
        if(degree >= FACTOR*3 && degree < FACTOR*5){
            text = "Cultura General";
        }
        if(degree >= FACTOR*5 && degree < FACTOR*7){
            text = "Geografia";
        }
        if(degree >= FACTOR*7 && degree < FACTOR*9){
            text = "Historia";
        }
        if(degree >= FACTOR*3 && degree < 360 || degree >= 0 && degree < FACTOR*1)
            text="Cultura General";
        return text;
    }

    private void conectar() {
        btnGirar = findViewById(R.id.button);
        ruleta = findViewById(R.id.imageView7);
        tvAngulo = findViewById(R.id.textView2);
    }
}
