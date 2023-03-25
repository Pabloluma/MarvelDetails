package com.pablo.trabajofingrado;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Objects;

public class account extends AppCompatActivity {
    DatabaseReference db;
    String usename = "";
    String name = "";
    String mail = "";
    String perfil = "";
    String perfil2 = "";
    EditText usuario;
    ImageView imageView;
    EditText nombre;
    EditText correo;
    Button boton;
    int PICK_IMAGE_REQUEST = 1;
    FirebaseStorage mStorage;
    StorageReference folder;
    String nomUsu;
    Query query;
    FirebaseAuth myauth;
    String dataKey;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        db = FirebaseDatabase.getInstance().getReference("Usuarios");
        usuario = findViewById(R.id.usufield);
        nombre = findViewById(R.id.nombrefield);
        correo = findViewById(R.id.correofield);
        imageView = findViewById(R.id.imPerfil);
        boton = findViewById(R.id.botonAcept);
        mStorage = FirebaseStorage.getInstance();
        folder = mStorage.getReference().child("feo");
        myauth = FirebaseAuth.getInstance();
        System.out.println("Hola " + myauth.getUid());
        System.out.println("Holaaaaa " + Objects.requireNonNull(myauth.getCurrentUser()).getUid());
        System.out.println("Holaaaaa " + Objects.requireNonNull(myauth.getCurrentUser()).getEmail());
        System.out.println("verificado: " + Objects.requireNonNull(myauth.getCurrentUser()).isEmailVerified());

        Bundle bundle = getIntent().getExtras();
        /*String*/ nomUsu = bundle.getString("usuario");
        /*Query*/ query = db.orderByChild("username").equalTo(nomUsu);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    usename = dataSnapshot.child("username").getValue().toString();
                    name = dataSnapshot.child("nombre").getValue().toString();
                    mail = dataSnapshot.child("email").getValue().toString();
                    perfil = dataSnapshot.child("perfil").getValue().toString();
                    System.out.println("Valor" + perfil);
                    if(!perfil.equals("")){
                        Glide.with(account.this).load(perfil).into(imageView);
                    }
                    usuario.setText(usename);
                    nombre.setText(name);
                    correo.setText(mail);
                    dataKey = dataSnapshot.getKey();
                    System.out.println("DataSnapshot: " + dataKey);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Seleccionar imagen"), PICK_IMAGE_REQUEST);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null ){
            Uri file_uri = data.getData();
            StorageReference filepath = folder.child("file" + file_uri.getLastPathSegment());
            filepath.putFile(file_uri).addOnSuccessListener(taskSnapshot -> filepath.getDownloadUrl().addOnSuccessListener(uri -> {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("Link", String.valueOf(uri));
                /*DatabaseReference parent =  bd.child(dataKey).child("perfil");
                parent.setValue(file_uri);*/
                db.setValue(hashMap);
                Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
            }));

            /*filepath.putFile(file_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //Error al cargar la imagen
                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();

                }
            });*/
        }

    }
}