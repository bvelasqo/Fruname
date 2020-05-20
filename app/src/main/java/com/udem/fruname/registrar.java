package com.udem.fruname;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class registrar extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDataBase;
    Button btnRegistrar;
    EditText txtId,txtEmail,txtPassword,txtNombre;
    String nombre,id,email,password;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        Conectar();
        mAuth=FirebaseAuth.getInstance();
        mDataBase= FirebaseDatabase.getInstance().getReference();
    }

    public void BtnRegistrar(View view){
         nombre=txtNombre.getText().toString();
         id=txtId.getText().toString();
         email=txtEmail.getText().toString();
         password=txtPassword.getText().toString();
        if(!(nombre.isEmpty()||id.isEmpty()||email.isEmpty()||password.isEmpty())){
            if(password.length()>=6){
                Registrar();
            }else{
                Toast.makeText(getApplicationContext(),"La contraseña debe tener más de 6 carácteres",
                        Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getApplicationContext(),"Llene todos los campos",Toast.LENGTH_LONG).show();
        }
    }

    private void Registrar() {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull final Task<AuthResult> task) {
                if(task.isSuccessful()){
                    final String uid =mAuth.getCurrentUser().getUid();
                    Map<String,Object> datos = new HashMap();
                    datos.put("nombre",nombre);
                    datos.put("id",id);
                    datos.put("email",email);
                    datos.put("password",password);
                    datos.put("nivel",1);
                    datos.put("xp",0);
                    datos.put("score",0);
                    mDataBase.child("Jugadores").child(uid).setValue(datos).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                Intent intent = new Intent(registrar.this,tablero.class);
                                intent.putExtra("uid",uid);
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(),task2.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void Conectar() {
        btnRegistrar=findViewById(R.id.btnRegister);
        txtId=findViewById(R.id.txtIdRegister);
        txtEmail=findViewById(R.id.txtEmailRegister);
        txtPassword=findViewById(R.id.txtPasswordRegister);
        txtNombre=findViewById(R.id.txtNombreRegister);
        imageView=findViewById(R.id.imageView);
    }
}
