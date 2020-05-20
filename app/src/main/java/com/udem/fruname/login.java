package com.udem.fruname;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText txtPassword,txtEmail;
    String password,email;
    Button btnIngresar,btnCrearCuenta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Conectar();
        mAuth=FirebaseAuth.getInstance();
    }

    public void BtnCrearCuenta(View view){
        startActivity(new Intent(login.this,registrar.class));
    }

    public void BtnIngresar(View view){
        password=txtPassword.getText().toString();
        email=txtEmail.getText().toString();
        if(!(password.isEmpty()||email.isEmpty())){
            Ingresar();
        }else{
            Toast.makeText(getApplicationContext(),"Llene todos los campos",Toast.LENGTH_LONG).show();
        }
    }

    private void Ingresar() {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String uid =mAuth.getCurrentUser().getUid();
                    Intent intent= new Intent(login.this,tablero.class);
                    intent.putExtra("uid",uid);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Verifique los datos",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){
            String uid =mAuth.getCurrentUser().getUid();
            Intent intent= new Intent(login.this,tablero.class);
            intent.putExtra("uid",uid);
            startActivity(intent);
            finish();
        }
    }

    private void Conectar() {
        txtEmail=findViewById(R.id.txtEmailLogin);
        txtPassword=findViewById(R.id.txtPasswordLogin);
        btnCrearCuenta=findViewById(R.id.btnCrearCuenta);
        btnIngresar=findViewById(R.id.btnIngresar);
    }
}
