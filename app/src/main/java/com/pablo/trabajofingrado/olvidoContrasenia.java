package com.pablo.trabajofingrado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class olvidoContrasenia extends AppCompatActivity {
    Button botonsigu;
    TextView text3;
    String mail = "";
    FirebaseAuth auth;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvido_contrasenia);
        getSupportActionBar().setTitle("Recuperar Contraseña");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setStatusBarColor(getResources().getColor(R.color.redMarvel));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.redMarvel)));
        text3 = findViewById(R.id.usuario);
        botonsigu = findViewById(R.id.siguiente);
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        text3.setVisibility(View.VISIBLE);
        botonsigu.setVisibility(View.VISIBLE);
        botonsigu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void resetPassword() {
        auth.setLanguageCode("es");
        mail = text3.getText().toString();
        auth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    dialog.setMessage("Espere un momento...");
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    Toast.makeText(olvidoContrasenia.this, "Correo enviado", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }else {
                    Toast.makeText(olvidoContrasenia.this, "No se pudo enviar el email", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}