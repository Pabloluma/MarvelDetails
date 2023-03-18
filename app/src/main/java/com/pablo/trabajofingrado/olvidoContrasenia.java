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
    /*Button boton2;
    TextView text1;
    TextView text2;*/
    TextView text3;
    String mail = "";
    FirebaseAuth auth;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvido_contrasenia);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.redMarvel));
        /*text1 = findViewById(R.id.Passwd);
        text2 = findViewById(R.id.Passwd2);*/
        text3 = findViewById(R.id.usuario);
        botonsigu = findViewById(R.id.siguiente);
        //boton2 = findViewById(R.id.buttonCambiar);
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);

        /*text1.setVisibility(View.INVISIBLE);
        text2.setVisibility(View.INVISIBLE);*/
        text3.setVisibility(View.VISIBLE);
        botonsigu.setVisibility(View.VISIBLE);
      //  boton2.setVisibility(View.INVISIBLE);
        botonsigu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mail = text3.getText().toString();
                if(!mail.isEmpty()){
                    dialog.setMessage("Espere un momento...");
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    resetPassword();
                }else{
                    Toast.makeText(olvidoContrasenia.this, "Hay que ingresar un email", Toast.LENGTH_SHORT).show();
                }
                /*if(sacarNombre().contains(text3.getText().toString())){
                    text1.setVisibility(View.VISIBLE);
                    text2.setVisibility(View.VISIBLE);
                    text3.setVisibility(View.INVISIBLE);
                    botonsigu.setVisibility(View.INVISIBLE);
                    boton2.setVisibility(View.VISIBLE);

                }else{
                    Toast.makeText(olvidoContrasenia.this, "Intentalo de nuevo", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        /*boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(olvidoContrasenia.this, "Se ha cambiado", Toast.LENGTH_SHORT).show();
                /*Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);*/
            /*}
        });*/
    }

    private void resetPassword() {
        auth.setLanguageCode("es");
        auth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(olvidoContrasenia.this, "Correo enviado", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }else {
                    Toast.makeText(olvidoContrasenia.this, "No se pudo enviar el email", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public ArrayList<String> sacarNombre(){
        DB admin = new DB(getApplicationContext(), "Bases", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor cursor = bd.rawQuery("SELECT NombreUsuario FROM Usuarios", null);
        ArrayList<String> nombres = new ArrayList<>();

        while(cursor.moveToNext()){
            nombres.add(cursor.getString(0));
        }
        cursor.close();

        return nombres;
    }
//Hay que hacer un metodo para sacar la contraseña del usuario insertado

//Actualizan los datos
    public void actualizarDatos(){
        DB admin = new DB(getApplicationContext(), "Bases", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        /*ContentValues cv = new ContentValues();
        cv.put("contrasenia", );
        String[] args = new String []{ "Academia Android"};
        db.update("comments", cv, "user=?", args);*/

    }
}