package com.pablo.trabajofingrado.Thor;

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
import com.pablo.trabajofingrado.CapAmerica.CAInfo;
import com.pablo.trabajofingrado.CapAmerica.CA_item;
import com.pablo.trabajofingrado.CapAmerica.DatosCA;
import com.pablo.trabajofingrado.CapAmerica.MiAdapterCA;
import com.pablo.trabajofingrado.MainActivity;
import com.pablo.trabajofingrado.R;
import com.pablo.trabajofingrado.temaspelisGoogle;

import java.util.ArrayList;

public class Thor_item extends AppCompatActivity {
    DatabaseReference mybd;
    ArrayList<String> nombres = new ArrayList<>();
    ArrayList<Integer> imagenActor = new ArrayList<>();
    ArrayList <String[]> actoresSep = new ArrayList<>();
    ArrayList <String[]> personajesSep = new ArrayList<>();
    ArrayList<String> anios = new ArrayList<>();
    ArrayList<String> sinop = new ArrayList<>();
    ArrayList<Integer> fotos = new ArrayList<>();
    ArrayList<String> duraciones = new ArrayList<>();
    ArrayList<DatosThor> listaPelis = new ArrayList<>();
    static int elementoThor = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thor_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setStatusBarColor(getResources().getColor(R.color.redMarvel));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.redMarvel)));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_home_24_white);
        getSupportActionBar().setTitle("Thor");

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
        mybd.child("Personajes").child("3").child("peliculas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String nombre = ds.child("nombre").getValue().toString();
                        String anyo = ds.child("anio").getValue().toString();
                        String descrip = ds.child("descripcion").getValue().toString();
                        String duracion = ds.child("duracion").getValue().toString();
                        String actor = ds.child("actor").getValue().toString();
                        String[] ac = actor.split(",");
                        String personaje = ds.child("personaje").getValue().toString();
                        String[] perso = personaje.split(",");

                        Thor_item.this.actoresSep.add(ac);
                        Thor_item.this.personajesSep.add(perso);
                        Thor_item.this.nombres.add(nombre);
                        Thor_item.this.anios.add(anyo);
                        Thor_item.this.sinop.add(descrip);
                        Thor_item.this.duraciones.add(duracion);
                    }

                    fotos.add(R.drawable.cartelera_thor1);
                    fotos.add(R.drawable.cartelera_thor2);
                    fotos.add(R.drawable.cartelera_thor3);
                    fotos.add(R.drawable.cartelera_thor4);

                    for (int i = 0; i < nombres.size(); i++) {
                        DatosThor objeto = new DatosThor(nombres.get(i), anios.get(i), fotos.get(i));
                        listaPelis.add(objeto);
                    }

                    RecyclerView recyclerView = findViewById(R.id.recyclerViewThor);
                    MiAdapterThor adapter = new MiAdapterThor(listaPelis);
                    recyclerView.setAdapter(adapter);
                    Intent intent = new Intent(getApplicationContext(), ThorInfo.class);

                    adapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            switch (recyclerView.getChildAdapterPosition(v)){
                                case 0:
                                    elementoThor = 0;
                                    intent.putExtra("estado", elementoThor);
                                    intent.putExtra("nombre", nombres.get(0));
                                    intent.putExtra("foto", fotos.get(0));
                                    intent.putExtra("anio", anios.get(0));
                                    intent.putExtra("sinopsis", sinop.get(0));
                                    intent.putExtra("duracion", duraciones.get(0));
                                    intent.putExtra("listaActor", actoresSep.get(0));
                                    intent.putExtra("listaPer", personajesSep.get(0));
                                    imagenActor.clear();
                                    imagenActor.add(R.drawable.chris_hemsworth);
                                    imagenActor.add(R.drawable.natalie_portman);
                                    imagenActor.add(R.drawable.anthony_hopkins);
                                    imagenActor.add(R.drawable.tom_hiddleston);
                                    imagenActor.add(R.drawable.stellan_skarsgard);
                                    imagenActor.add(R.drawable.colm_feore);
                                    imagenActor.add(R.drawable.idris_elba);
                                    imagenActor.add(R.drawable.ray_stevenson);
                                    intent.putExtra("fotosActores", imagenActor);
                                    startActivity(intent);
                                    break;
                                case 1:
                                    elementoThor = 1;
                                    intent.putExtra("estado", elementoThor);
                                    intent.putExtra("nombre", nombres.get(1));
                                    intent.putExtra("foto", fotos.get(1));
                                    intent.putExtra("anio", anios.get(1));
                                    intent.putExtra("sinopsis", sinop.get(1));
                                    intent.putExtra("duracion", duraciones.get(1));
                                    intent.putExtra("listaActor", actoresSep.get(1));
                                    intent.putExtra("listaPer", personajesSep.get(1));
                                    imagenActor.clear();
                                    imagenActor.add(R.drawable.chris_hemsworth);
                                    imagenActor.add(R.drawable.natalie_portman);
                                    imagenActor.add(R.drawable.tom_hiddleston);
                                    imagenActor.add(R.drawable.stellan_skarsgard);
                                    imagenActor.add(R.drawable.idris_elba);
                                    imagenActor.add(R.drawable.christopher_eccleston);
                                    imagenActor.add(R.drawable.adewale_akinnuoye);
                                    imagenActor.add(R.drawable.kat_dennings);
                                    intent.putExtra("fotosActores", imagenActor);
                                    startActivity(intent);
                                    break;
                                case 2:
                                    elementoThor = 2;
                                    intent.putExtra("estado", elementoThor);
                                    intent.putExtra("nombre", nombres.get(2));
                                    intent.putExtra("foto", fotos.get(2));
                                    intent.putExtra("anio", anios.get(2));
                                    intent.putExtra("sinopsis", sinop.get(2));
                                    intent.putExtra("duracion", duraciones.get(2));
                                    intent.putExtra("listaActor", actoresSep.get(2));
                                    intent.putExtra("listaPer", personajesSep.get(2));
                                    imagenActor.clear();
                                    imagenActor.add(R.drawable.chris_hemsworth);
                                    imagenActor.add(R.drawable.tom_hiddleston);
                                    imagenActor.add(R.drawable.cate_blanchet);
                                    imagenActor.add(R.drawable.idris_elba);
                                    imagenActor.add(R.drawable.jeff_goldblum);
                                    imagenActor.add(R.drawable.tessa_thomson);
                                    imagenActor.add(R.drawable.karl_urban);
                                    imagenActor.add(R.drawable.mark_rufalo);
                                    intent.putExtra("fotosActores", imagenActor);
                                    startActivity(intent);
                                    break;
                                case 3:
                                    elementoThor = 3;
                                    intent.putExtra("estado", elementoThor);
                                    intent.putExtra("nombre", nombres.get(3));
                                    intent.putExtra("foto", fotos.get(3));
                                    intent.putExtra("anio", anios.get(3));
                                    intent.putExtra("sinopsis", sinop.get(3));
                                    intent.putExtra("duracion", duraciones.get(3));
                                    intent.putExtra("listaActor", actoresSep.get(3));
                                    intent.putExtra("listaPer", personajesSep.get(3));
                                    imagenActor.clear();
                                    imagenActor.add(R.drawable.chris_hemsworth);
                                    imagenActor.add(R.drawable.tom_hiddleston);
                                    imagenActor.add(R.drawable.christian_bale);
                                    imagenActor.add(R.drawable.tessa_thomson);
                                    imagenActor.add(R.drawable.russel_crowe);
                                    imagenActor.add(R.drawable.jaimie_alexander);
                                    imagenActor.add(R.drawable.chris_prat);
                                    imagenActor.add(R.drawable.dave_bautista);
                                    intent.putExtra("fotosActores", imagenActor);
                                    startActivity(intent);
                                    break;
                            }
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