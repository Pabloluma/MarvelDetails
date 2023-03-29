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
import java.util.Objects;

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
        bd = FirebaseDatabase.getInstance().getReference("Usuarios");
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

        if (usu.isEmpty() || nom.isEmpty() || ape.isEmpty() || cor.isEmpty() || con.isEmpty()) {
            Toast.makeText(login.this, "Debes rellenar Todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            if (cor.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(cor).matches()) {
                Toast.makeText(login.this, "Pon una direccion de correo electrónico Válida", Toast.LENGTH_SHORT).show();
            }
            Query queryCor = bd.orderByChild("email").equalTo(cor);
            queryCor.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Toast.makeText(login.this, "El correo ya existe", Toast.LENGTH_SHORT).show();
                    } else {
                        Query queryUser = bd.orderByChild("username").equalTo(usu);
                        queryUser.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    Toast.makeText(login.this, "El Usuario ya existe", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (con.length() < 6) {
                                        Toast.makeText(login.this, "La contraseña debe tener como mínimo 6 caracteres", Toast.LENGTH_SHORT).show();
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
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}