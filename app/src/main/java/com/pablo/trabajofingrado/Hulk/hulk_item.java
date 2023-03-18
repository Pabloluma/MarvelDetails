package com.pablo.trabajofingrado.Hulk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pablo.trabajofingrado.R;

import com.pablo.trabajofingrado.Hulk.MiAdapterHulk;
import com.pablo.trabajofingrado.Hulk.HulkInfo;
import com.pablo.trabajofingrado.temaspelisGoogle;


import java.util.ArrayList;

public class hulk_item extends AppCompatActivity {
    DatabaseReference mybd;
    ArrayList<String> nombres = new ArrayList<>();
    ArrayList<String> anios = new ArrayList<>();
    ArrayList<String> sinop = new ArrayList<>();
    ArrayList<Integer> fotos = new ArrayList<>();
    ArrayList<String> duraciones = new ArrayList<>();
    DatosHulk[] listaPelis = new DatosHulk[2];
    static int elementoHulk = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hulk_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setStatusBarColor(getResources().getColor(R.color.redMarvel));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.redMarvel)));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_home_24_white);
        getSupportActionBar().setTitle("Hulk");

        nombrePelis();
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
    public void nombrePelis(){
        mybd = FirebaseDatabase.getInstance().getReference();
        mybd.child("Personajes").child("1").child("peliculas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String nombre = ds.child("nombre").getValue().toString();
                        String anyo = ds.child("anio").getValue().toString();
                        String descrip = ds.child("descripcion").getValue().toString();
                        String duracion = ds.child("duracion").getValue().toString();
                        hulk_item.this.nombres.add(nombre);
                        hulk_item.this.anios.add(anyo);
                        hulk_item.this.sinop.add(descrip);
                        hulk_item.this.duraciones.add(duracion);
                    }
                    fotos.add(R.drawable.cartelera_hulk);
                    fotos.add(R.drawable.cartelera_hulk2);

                    for (int i = 0; i < nombres.size(); i++) {
                        DatosHulk objeto = new DatosHulk(nombres.get(i), anios.get(i), fotos.get(i));
                        listaPelis[i] = objeto;
                    }
                    RecyclerView recyclerView = findViewById(R.id.recyclerViewHulk);
                    MiAdapterHulk adapter = new MiAdapterHulk(listaPelis);
                    recyclerView.setAdapter(adapter);
                    Intent intent = new Intent(getApplicationContext(), HulkInfo.class);

                    adapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (recyclerView.getChildAdapterPosition(v)){
                                case 0:
                                    elementoHulk = 0;
                                    intent.putExtra("estado", elementoHulk);
                                    intent.putExtra("nombre", nombres.get(0));
                                    intent.putExtra("foto", fotos.get(0));
                                    intent.putExtra("anio", anios.get(0));
                                    intent.putExtra("sinopsis", sinop.get(0));
                                    intent.putExtra("duracion", duraciones.get(0));
                                    startActivity(intent);
                                    break;
                                case 1:
                                    elementoHulk = 1;
                                    intent.putExtra("estado", elementoHulk);
                                    intent.putExtra("nombre", nombres.get(1));
                                    intent.putExtra("foto", fotos.get(1));
                                    intent.putExtra("anio", anios.get(1));
                                    intent.putExtra("sinopsis", sinop.get(1));
                                    intent.putExtra("duracion", duraciones.get(1));
                                    startActivity(intent);
                                    break;

                            }
                            /*System.out.println(recyclerView.getChildAdapterPosition(v));
                            Toast.makeText(getApplicationContext(),"Seleccion: " + listaPelis[recyclerView.getChildAdapterPosition(v)].getNombre(),Toast.LENGTH_LONG).show();*/
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}