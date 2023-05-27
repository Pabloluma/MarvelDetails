package com.pablo.trabajofingrado.ironman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pablo.trabajofingrado.R;

import java.util.ArrayList;

public class IM_item extends AppCompatActivity implements MiAdapterIM.ItemClicListener{

    DatabaseReference mybd;
    ArrayList<String> nombres = new ArrayList<>();
    ArrayList<String> anios = new ArrayList<>();
    ArrayList<String> sinop = new ArrayList<>();
    ArrayList<Integer> fotos = new ArrayList<>();
    ArrayList<String> duraciones = new ArrayList<>();
    ArrayList<Integer> imagenActor = new ArrayList<>();
    ArrayList <String[]> actoresSep = new ArrayList<>();
    ArrayList <String[]> personajesSep = new ArrayList<>();
    ArrayList<DatosIM> listaPelis = new ArrayList<>();
    static int elementoIM = 0;
    MiAdapterIM adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setStatusBarColor(getResources().getColor(R.color.redMarvel));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.redMarvel)));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_home_24_white);
        getSupportActionBar().setTitle("Iron Man");
        nombrePelis();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        final MenuItem searchItem = menu.findItem(R.id.search_item);

        final SearchView searchView = (SearchView)searchItem.getActionView();

        //permite modificar el hint que el EditText muestra por defecto
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filtrar(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filtrado(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
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
        mybd.child("Personajes").child("4").child("peliculas").addValueEventListener(new ValueEventListener() {
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

                        IM_item.this.actoresSep.add(ac);
                        IM_item.this.personajesSep.add(perso);
                        IM_item.this.nombres.add(nombre);
                        IM_item.this.anios.add(anyo);
                        IM_item.this.sinop.add(descrip);
                        IM_item.this.duraciones.add(duracion);
                    }
                    fotos.add(R.drawable.cartelera_im1);
                    fotos.add(R.drawable.cartelera_im2);
                    fotos.add(R.drawable.cartelera_im3);

                    for (int i = 0; i < nombres.size(); i++) {
                        DatosIM objeto = new DatosIM(nombres.get(i), anios.get(i), fotos.get(i));
                        listaPelis.add(objeto);
                    }
                    RecyclerView recyclerView = findViewById(R.id.recyclerViewIM);
                    adapter = new MiAdapterIM(getApplicationContext() ,listaPelis, IM_item.this);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void itemClicked(DatosIM datosIM) {
        int posicion = nombres.indexOf(datosIM.getNombreIM());
        Intent intent = new Intent(getApplicationContext(), IMInfo.class);
        switch (posicion){
            case 0:
                elementoIM = 0;
                intent.putExtra("estado", elementoIM);
                intent.putExtra("nombre", nombres.get(0));
                intent.putExtra("foto", fotos.get(0));
                intent.putExtra("anio", anios.get(0));
                intent.putExtra("sinopsis", sinop.get(0));
                intent.putExtra("duracion", duraciones.get(0));
                intent.putExtra("listaActor", actoresSep.get(0));
                intent.putExtra("listaPer", personajesSep.get(0));
                imagenActor.clear();
                imagenActor.add(R.drawable.robert_downey_jr);
                imagenActor.add(R.drawable.terrance_howard);
                imagenActor.add(R.drawable.gwyneth_paltrow);
                imagenActor.add(R.drawable.jeff_bridges);
                imagenActor.add(R.drawable.shaun_toub);
                imagenActor.add(R.drawable.leslie_bibb);
                imagenActor.add(R.drawable.clark_greg);
                imagenActor.add(R.drawable.bill_smitrovich);
                intent.putExtra("fotosActores", imagenActor);
                startActivity(intent);
                break;
            case 1:
                elementoIM = 1;
                intent.putExtra("estado", elementoIM);
                intent.putExtra("nombre", nombres.get(1));
                intent.putExtra("foto", fotos.get(1));
                intent.putExtra("anio", anios.get(1));
                intent.putExtra("sinopsis", sinop.get(1));
                intent.putExtra("duracion", duraciones.get(1));
                intent.putExtra("listaActor", actoresSep.get(1));
                intent.putExtra("listaPer", personajesSep.get(1));
                imagenActor.clear();
                imagenActor.add(R.drawable.robert_downey_jr);
                imagenActor.add(R.drawable.don_cheadle);
                imagenActor.add(R.drawable.scarlett_johansson);
                imagenActor.add(R.drawable.mickey_rourke);
                imagenActor.add(R.drawable.gwyneth_paltrow);
                imagenActor.add(R.drawable.sam_rockwell);
                imagenActor.add(R.drawable.samuel_ljackson);
                imagenActor.add(R.drawable.garry_shandling);
                intent.putExtra("fotosActores", imagenActor);
                startActivity(intent);
                break;
            case 2:
                elementoIM = 2;
                intent.putExtra("estado", elementoIM);
                intent.putExtra("nombre", nombres.get(2));
                intent.putExtra("foto", fotos.get(2));
                intent.putExtra("anio", anios.get(2));
                intent.putExtra("sinopsis", sinop.get(2));
                intent.putExtra("duracion", duraciones.get(2));
                intent.putExtra("listaActor", actoresSep.get(2));
                intent.putExtra("listaPer", personajesSep.get(2));
                imagenActor.clear();
                imagenActor.add(R.drawable.robert_downey_jr);
                imagenActor.add(R.drawable.gwyneth_paltrow);
                imagenActor.add(R.drawable.don_cheadle);
                imagenActor.add(R.drawable.ben_kingsley);
                imagenActor.add(R.drawable.guy_pearce);
                imagenActor.add(R.drawable.james_badge);
                imagenActor.add(R.drawable.rebecca_hall);
                imagenActor.add(R.drawable.jon_favreau);
                intent.putExtra("fotosActores", imagenActor);
                startActivity(intent);
                break;
            default:
                break;
        }

    }
}