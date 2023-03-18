package com.pablo.trabajofingrado;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class temasPeliculas extends AppCompatActivity {
    String nombre = "";
    MenuItem item;
    Button botondesc;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temas_peliculas);
        TextView text =findViewById(R.id.textViewpablo);
        ImageView flecha = findViewById(R.id.flechaAtras);
        Toolbar toolbar = findViewById(R.id.toolbar);
        getSupportActionBar().hide();
        // Descargar
        botondesc = findViewById(R.id.descargas3);
        botondesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                desc();
            }
        });

        flecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), temaspelisGoogle.class));
            }
        });



    }

    //Descargar
    public void desc(){
        storageReference = firebaseStorage.getInstance().getReference();
        reference = storageReference.child("videoprueba.mp4");
        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                descargar(temasPeliculas.this,"videoprueba",".mp4",DIRECTORY_DOWNLOADS,url);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void descargar(Context context, String filename, String fileExtension, String destinationDirectory, String url){
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setDestinationInExternalFilesDir(context,destinationDirectory,filename+fileExtension);
        downloadManager.enqueue(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        item = findViewById(R.id.nombre);
        DB admin = new DB(this, "Bases", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Bundle bundle = getIntent().getExtras();
        String str = bundle.getString("nombre");
        Cursor cursor3 = bd.rawQuery("SELECT NombreUsuario FROM Usuarios WHERE Correo = '"+str +"'", null);
        while(cursor3.moveToNext()){
            nombre = cursor3.getString(0);
            CharSequence charSequence = nombre;
            menu.findItem(R.id.nombre).setTitle(charSequence);
        }
        cursor3.close();

        return true;
    }


    //Opcion2

    /*@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        item = findViewById(R.id.nombre);
        DB admin = new DB(this, "Bases", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Bundle bundle = getIntent().getExtras();
        String str = bundle.getString("nombre");
        Cursor cursor3 = bd.rawQuery("SELECT NombreUsuario FROM Usuarios WHERE Correo = '"+str +"'", null);
        while(cursor3.moveToNext()){
            nombre = cursor3.getString(0);
            CharSequence charSequence = nombre;
            menu.findItem(R.id.nombre).setTitle(charSequence);
        }
        cursor3.close();
        return true;
    }*/
}