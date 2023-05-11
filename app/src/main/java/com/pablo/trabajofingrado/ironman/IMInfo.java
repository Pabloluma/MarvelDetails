package com.pablo.trabajofingrado.ironman;

import static android.os.Environment.DIRECTORY_MOVIES;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pablo.trabajofingrado.AdapterActores;
import com.pablo.trabajofingrado.DatosActores;
import com.pablo.trabajofingrado.R;

import java.util.ArrayList;

public class IMInfo extends AppCompatActivity {
    ArrayList<DatosActores> listaActoresIM = new ArrayList<>();
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageReference reference;
    Button botondesc;
    TextView tit;
    TextView year;
    TextView sinopsis;
    ImageView imagen;
    TextView duracion;
    int REQUEST = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im_info);
        getWindow().setStatusBarColor(getResources().getColor(R.color.redMarvel));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.redMarvel)));
        botondesc = findViewById(R.id.descargas2);
        tit = findViewById(R.id.titulo);
        imagen = findViewById(R.id.imageView5);
        year = findViewById(R.id.anio);
        sinopsis = findViewById(R.id.sinopsis);
        duracion = findViewById(R.id.duracion);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        insertar();
        ActoresIM();

        botondesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                desc();
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
    public void desc(){
        storageReference = firebaseStorage.getInstance().getReference();
        Intent intent = getIntent();
        int elementoCA = intent.getIntExtra("estado", -1);
        if(elementoCA == 0){
            reference = storageReference.child("Iron Man/Iron Man.mp4");
            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    String url = uri.toString();
                    descargar("Iron Man.mp4",url);
                    Toast.makeText(getApplicationContext(), "La descarga ha sido correcta en la carpeta Movies", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }else if(elementoCA == 1) {
            reference = storageReference.child("Iron Man/Iron Man 2.mp4");
            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    String url = uri.toString();
                    descargar("Iron Man 2.mp4", url);
                    Toast.makeText(getApplicationContext(), "La descarga ha sido correcta en la carpeta Movies", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }else if(elementoCA == 2) {
            reference = storageReference.child("Iron Man/Iron Man 3.mp4");
            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    String url = uri.toString();
                    descargar("Iron Man 3.mp4", url);
                    Toast.makeText(getApplicationContext(), "La descarga ha sido correcta en la carpeta Movies", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }else{
            Toast.makeText(this, "No se ha descargado", Toast.LENGTH_SHORT).show();
        }

    }

    public void descargar(String filename, String url){
        int permisoWrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permisoWrite == PackageManager.PERMISSION_GRANTED){
            Uri uri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setDestinationInExternalPublicDir(DIRECTORY_MOVIES, filename);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.allowScanningByMediaScanner();
            DownloadManager manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            manager.enqueue(request);
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST);
            }
        }
    }
    public void insertar(){
        Intent intent = getIntent();
        int elemento = intent.getIntExtra("estado", -1);
        if(elemento >= 0){
            Bundle bundle = getIntent().getExtras();
            String name = bundle.getString("nombre");
            int foto = bundle.getInt("foto");
            String years = bundle.getString("anio");
            String sinop = bundle.getString("sinopsis");
            String duraciones = bundle.getString("duracion");
            tit.setText(name);
            imagen.setImageResource(foto);
            year.setText(years);
            sinopsis.setText(sinop);
            duracion.setText(duraciones);
        }else{
            Toast.makeText(this, "Algo no ha ido como debería", Toast.LENGTH_SHORT).show();
        }
    }
    public void ActoresIM(){
        Intent intent = getIntent();
        int elemento = intent.getIntExtra("estado", -1);
        if(elemento >= 0){
            Bundle bundle = getIntent().getExtras();
            String[] listasActor = bundle.getStringArray("listaActor");
            String[] listasPers = bundle.getStringArray("listaPer");
            ArrayList<Integer> listasPerso = bundle.getIntegerArrayList("fotosActores");

            for (int i = 0; i < listasActor.length; i++) {
                DatosActores objeto = new DatosActores(listasActor[i],listasPers[i], listasPerso.get(i));
                listaActoresIM.add(objeto);
                RecyclerView recyclerView = findViewById(R.id.RecyclerActores);
                AdapterActores adapter = new AdapterActores(listaActoresIM);
                recyclerView.setAdapter(adapter);
            }
        }else{
            Toast.makeText(this, "Algo no ha ido como debería", Toast.LENGTH_SHORT).show();
        }
    }
}