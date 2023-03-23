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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class account extends AppCompatActivity {
    DatabaseReference db;
    String usename = "";
    String name = "";
    String mail = "";
    EditText usuario;
    ImageView imageView;
    EditText nombre;
    EditText correo;
    Button boton;
    int PICK_IMAGE_REQUEST = 1;
    FirebaseStorage mStorage;
    StorageReference storageRef;

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
        storageRef = mStorage.getReference().child("FotoUsuario");
        Bundle bundle = getIntent().getExtras();
        String nomUsu = bundle.getString("usuario");
        Query query = db.orderByChild("username").equalTo(nomUsu);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    usename = dataSnapshot.child("username").getValue().toString();
                    name = dataSnapshot.child("nombre").getValue().toString();
                    mail = dataSnapshot.child("email").getValue().toString();
                    usuario.setText(usename);
                    nombre.setText(name);
                    correo.setText(mail);
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
            Uri uri = data.getData();
            StorageReference filepath = storageRef.child(uri.getLastPathSegment());


            filepath.putFile(uri).addOnSuccessListener(taskSnapshot -> filepath.getDownloadUrl().addOnSuccessListener(uri1 -> {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("link",String.valueOf(uri1));
                System.out.println(hashMap);





            }));
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(account.this, "Imagen subida", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //Error al cargar la imagen
                    Toast.makeText(account.this, "error", Toast.LENGTH_SHORT).show();

                }
            });
        }

    }
}