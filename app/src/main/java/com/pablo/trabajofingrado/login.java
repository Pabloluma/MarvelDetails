package com.pablo.trabajofingrado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
    EditText campoNomUsu, campoNombre, campoApellido, campoCorUsu, campoPasswd;
    ArrayList<String> correo = new ArrayList<>();
    ArrayList<String> usuarios = new ArrayList<>();
    String usu = "";
    String nom = "";
    String ape = "";
    String cor = "";
    String con = "";
    FirebaseAuth auth;
    DatabaseReference bd;
    User user;
    long num = 0;
    String correos;
    FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.redMarvel));
        auth = FirebaseAuth.getInstance();
        fUser = auth.getCurrentUser();
        bd = FirebaseDatabase.getInstance().getReference().child("Usuarios");
        user = new User();
        campoNomUsu = findViewById(R.id.idUsuario);
        campoNombre = findViewById(R.id.idNombre);
        campoApellido = findViewById(R.id.idApellido);
        campoCorUsu = findViewById(R.id.idCorreo);
        campoPasswd = findViewById(R.id.idPasswd);
        Button boton = findViewById(R.id.buttonreg);

        bd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    num = (snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void alta(View v) {
        usu = campoNomUsu.getText().toString().trim();
        nom = campoNombre.getText().toString().trim();
        ape = campoApellido.getText().toString().trim();
        cor = campoCorUsu.getText().toString().trim();
        con = campoPasswd.getText().toString().trim();

//        auth.createUserWithEmailAndPassword(cor, con).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//
//           }
//       });


        //Comprueba se está bien formado el correo electrónico
        if (cor.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(cor).matches()) {
            Toast.makeText(login.this, "Pon una direccion de correo electrónico", Toast.LENGTH_SHORT).show();
        } else {

            bd.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String correos = dataSnapshot.child("email").getValue().toString();
                        correo.add(correos);
                    }

                    if (con.length() < 6) {
                        Toast.makeText(login.this, "La contraseña debe tener como mínimo 6 caracteres", Toast.LENGTH_SHORT).show();
                    } else {
                        if (correo.contains(cor)) {
                            campoCorUsu.setError("Este correo ya existe");
                        } else {
                            auth.createUserWithEmailAndPassword(cor,con).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                }
                            });
                            user.setUsername(usu);
                            user.setNombre(nom);
                            user.setApellido(ape);
                            user.setEmail(cor);
                           //user.setContrasenia(con);
                            bd.child(String.valueOf(num + 1)).setValue(user);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            Toast.makeText(getApplicationContext(), "Se ha insertado", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }



        /*DB admin = new DB(this, "Bases", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String nomUsu = campoNomUsu.getText().toString();
        String nombreUsu = campoNombre.getText().toString();
        String apeUsu = campoApellido.getText().toString();
        String CorUsu = campoCorUsu.getText().toString();
        String passwdUsu = campoPasswd.getText().toString();
        if(bd!=null) {
            Cursor cursor = bd.rawQuery("SELECT NombreUsuario,Correo FROM Usuarios", null);
            while (cursor.moveToNext()) {
                usuarios.add(cursor.getString(0));
                correo.add(cursor.getString(1));
                System.out.println(usuarios);
                System.out.println(correo);
            }
            cursor.close();
            if (correo.contains(CorUsu)) {
                Toast.makeText(this, "El Correo ya esta registrado", Toast.LENGTH_LONG).show();
            }else if(usuarios.contains(nomUsu)) {
                Toast.makeText(this, "El nombre de usuario ya está registrado en la base de datos", Toast.LENGTH_LONG).show();
            }else if(correo.contains(CorUsu)&& usuarios.contains(nomUsu)){
                Toast.makeText(this, "El Correo y el nombre de usuario ya esta registrado", Toast.LENGTH_LONG).show();
            }else if(nomUsu.equals("")||nombreUsu.equals("")||apeUsu.equals("")||CorUsu.equals("")||passwdUsu.equals("")){
                Toast.makeText(this, "Tienes que rellenar todos los campos", Toast.LENGTH_LONG).show();
            }else{
                ContentValues registro = new ContentValues();
                registro.put("NombreUsuario", nomUsu);
                registro.put("Nombre", nombreUsu);
                registro.put("Apellido", apeUsu);
                registro.put("Correo", CorUsu);
                registro.put("contrasenia", passwdUsu);
                bd.insert("Usuarios", null, registro);
                bd.close();
                campoNomUsu.setText("");
                campoNombre.setText("");
                campoApellido.setText("");
                campoCorUsu.setText("");
                campoPasswd.setText("");
                Toast.makeText(this,"Se han guardado los datos", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(v.getContext(), MainActivity.class );
                startActivity(intent);
            }
        }else {
            Toast.makeText(login.this, "ERROR AL CREAR LA BASE DE DATOS", Toast.LENGTH_LONG).show();
        }*/

    }
}