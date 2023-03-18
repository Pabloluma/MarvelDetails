package com.pablo.trabajofingrado.Spiderman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pablo.trabajofingrado.R;
import com.pablo.trabajofingrado.temaspelisGoogle;

import java.util.ArrayList;

public class spiderman_item extends AppCompatActivity {
    DatabaseReference mybd;
    ArrayList<String> nombres = new ArrayList<>();
    ArrayList<String> anios = new ArrayList<>();
    ArrayList<String> sinop = new ArrayList<>();
    ArrayList<Integer> fotos = new ArrayList<>();
    ArrayList<String> duraciones = new ArrayList<>();
    DatosSpiderman[] listaPelis = new DatosSpiderman[8];
    static int elementoSpiderman = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spiderman_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setStatusBarColor(getResources().getColor(R.color.redMarvel));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.redMarvel)));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_home_24_white);
        getSupportActionBar().setTitle("Spiderman");

        nombrePelis();

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                /*startActivity(new Intent(getApplicationContext(), temaspelisGoogle.class));
//                supportFinishAfterTransition(); */
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void nombrePelis(){
        mybd = FirebaseDatabase.getInstance().getReference();
        mybd.child("Personajes").child("0").child("peliculas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String nombre = ds.child("nombre").getValue().toString();
                        String anyo = ds.child("anio").getValue().toString();
                        String descrip = ds.child("descripcion").getValue().toString();
                        String duracion = ds.child("duracion").getValue().toString();
                        spiderman_item.this.nombres.add(nombre);
                        spiderman_item.this.anios.add(anyo);
                        spiderman_item.this.sinop.add(descrip);
                        spiderman_item.this.duraciones.add(duracion);
                    }
                    fotos.add(R.drawable.cartelera_sp11);
                    fotos.add(R.drawable.cartelera_sp2);
                    fotos.add(R.drawable.cartelera_sp33);
                    fotos.add(R.drawable.cartelera_asp);
                    fotos.add(R.drawable.cartelera_asp2);
                    fotos.add(R.drawable.cartelera_shc);
                    fotos.add(R.drawable.cartelera_sffh);
                    fotos.add(R.drawable.cartelera_snwh);

                    for (int i = 0; i < nombres.size(); i++) {
                        DatosSpiderman objeto = new DatosSpiderman(nombres.get(i), anios.get(i), fotos.get(i));
                        listaPelis[i] = objeto;
                    }
                    RecyclerView recyclerView = findViewById(R.id.recyclerView);
                    MiAdapterSpiderman adapter = new MiAdapterSpiderman(listaPelis);
                    recyclerView.setAdapter(adapter);
                    Intent intent = new Intent(getApplicationContext(), SpidermanInfo.class);

                    adapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (recyclerView.getChildAdapterPosition(v)){
                                case 0:
                                    elementoSpiderman = 0;
                                    intent.putExtra("estado", elementoSpiderman);
                                    intent.putExtra("nombre", nombres.get(0));
                                    intent.putExtra("foto", fotos.get(0));
                                    intent.putExtra("anio", anios.get(0));
                                    intent.putExtra("sinopsis", sinop.get(0));
                                    intent.putExtra("duracion", duraciones.get(0));
                                    startActivity(intent);
                                    break;
                                case 1:
                                    elementoSpiderman = 1;
                                    intent.putExtra("estado", elementoSpiderman);
                                    intent.putExtra("nombre", nombres.get(1));
                                    intent.putExtra("foto", fotos.get(1));
                                    intent.putExtra("anio", anios.get(1));
                                    intent.putExtra("sinopsis", sinop.get(1));
                                    intent.putExtra("duracion", duraciones.get(1));
                                    startActivity(intent);
                                    break;
                                case 2:
                                    elementoSpiderman = 2;
                                    intent.putExtra("estado", elementoSpiderman);
                                    intent.putExtra("nombre", nombres.get(2));
                                    intent.putExtra("foto", fotos.get(2));
                                    intent.putExtra("anio", anios.get(2));
                                    intent.putExtra("sinopsis", sinop.get(2));
                                    intent.putExtra("duracion", duraciones.get(2));
                                    startActivity(intent);
                                    break;
                                case 3:
                                    elementoSpiderman = 3;
                                    intent.putExtra("estado", elementoSpiderman);
                                    intent.putExtra("nombre", nombres.get(3));
                                    intent.putExtra("foto", fotos.get(3));
                                    intent.putExtra("anio", anios.get(3));
                                    intent.putExtra("sinopsis", sinop.get(3));
                                    intent.putExtra("duracion", duraciones.get(3));
                                    startActivity(intent);
                                    break;
                                case 4:
                                    elementoSpiderman = 4;
                                    intent.putExtra("estado", elementoSpiderman);
                                    intent.putExtra("nombre", nombres.get(4));
                                    intent.putExtra("foto", fotos.get(4));
                                    intent.putExtra("anio", anios.get(4));
                                    intent.putExtra("sinopsis", sinop.get(4));
                                    intent.putExtra("duracion", duraciones.get(4));
                                    startActivity(intent);
                                    break;
                                case 5:
                                    elementoSpiderman = 5;
                                    intent.putExtra("estado", elementoSpiderman);
                                    intent.putExtra("nombre", nombres.get(5));
                                    intent.putExtra("foto", fotos.get(5));
                                    intent.putExtra("anio", anios.get(5));
                                    intent.putExtra("sinopsis", sinop.get(5));
                                    intent.putExtra("duracion", duraciones.get(5));
                                    startActivity(intent);
                                    break;
                                case 6:
                                    elementoSpiderman = 6;
                                    intent.putExtra("estado", elementoSpiderman);
                                    intent.putExtra("nombre", nombres.get(6));
                                    intent.putExtra("foto", fotos.get(6));
                                    intent.putExtra("anio", anios.get(6));
                                    intent.putExtra("sinopsis", sinop.get(6));
                                    intent.putExtra("duracion", duraciones.get(6));
                                    startActivity(intent);
                                    break;
                                case 7:
                                    elementoSpiderman = 7;
                                    intent.putExtra("estado", elementoSpiderman);
                                    intent.putExtra("nombre", nombres.get(7));
                                    intent.putExtra("foto", fotos.get(7));
                                    intent.putExtra("anio", anios.get(7));
                                    intent.putExtra("sinopsis", sinop.get(7));
                                    intent.putExtra("duracion", duraciones.get(7));
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