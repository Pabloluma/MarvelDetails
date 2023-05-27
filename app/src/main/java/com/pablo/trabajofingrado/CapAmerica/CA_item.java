package com.pablo.trabajofingrado.CapAmerica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pablo.trabajofingrado.R;

import java.util.ArrayList;

public class CA_item extends AppCompatActivity implements MiAdapterCA.ItemClicListener{
    DatabaseReference mybd;
    ArrayList<String> nombres = new ArrayList<>();
    ArrayList<String> anios = new ArrayList<>();
    ArrayList<String> sinop = new ArrayList<>();
    ArrayList<Integer> fotos = new ArrayList<>();
    ArrayList<String> duraciones = new ArrayList<>();
    ArrayList<Integer> imagenActor = new ArrayList<>();
    ArrayList <String[]> actoresSep = new ArrayList<>();
    ArrayList <String[]> personajesSep = new ArrayList<>();
    ArrayList<DatosCA> listaPelis = new ArrayList<>();
    static int elementoCA = 0;
    MiAdapterCA adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ca_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setStatusBarColor(getResources().getColor(R.color.redMarvel));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.redMarvel)));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_home_24_white);
        getSupportActionBar().setTitle("Capitan America");

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

    public void nombrePelis(){
        mybd = FirebaseDatabase.getInstance().getReference();
        mybd.child("Personajes").child("2").child("peliculas").addValueEventListener(new ValueEventListener() {
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

                        CA_item.this.actoresSep.add(ac);
                        CA_item.this.personajesSep.add(perso);
                        CA_item.this.nombres.add(nombre);
                        CA_item.this.anios.add(anyo);
                        CA_item.this.sinop.add(descrip);
                        CA_item.this.duraciones.add(duracion);
                    }
                    fotos.add(R.drawable.cartelera_ca1);
                    fotos.add(R.drawable.cartelera_ca2);
                    fotos.add(R.drawable.cartelera_ca3);

                    for (int i = 0; i < nombres.size(); i++) {
                        DatosCA objeto = new DatosCA(nombres.get(i), anios.get(i), fotos.get(i));
                        listaPelis.add(objeto);
                    }
                    RecyclerView recyclerView = findViewById(R.id.recyclerViewCA);
                    adapter = new MiAdapterCA(getApplicationContext(),listaPelis, CA_item.this);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void itemClicked(DatosCA datosCA) {
        int posicion = nombres.indexOf(datosCA.getNombreCA());
        Intent intent = new Intent(getApplicationContext(), CAInfo.class);
        switch (posicion){
            case 0:
                elementoCA = 0;
                intent.putExtra("estado", elementoCA);
                intent.putExtra("nombre", nombres.get(0));
                intent.putExtra("foto", fotos.get(0));
                intent.putExtra("anio", anios.get(0));
                intent.putExtra("sinopsis", sinop.get(0));
                intent.putExtra("duracion", duraciones.get(0));
                intent.putExtra("listaActor", actoresSep.get(0));
                intent.putExtra("listaPer", personajesSep.get(0));
                imagenActor.clear();
                imagenActor.add(R.drawable.chris_evans);
                imagenActor.add(R.drawable.hayley_atwell);
                imagenActor.add(R.drawable.sebastian_stan);
                imagenActor.add(R.drawable.tommy_lee);
                imagenActor.add(R.drawable.hugo_weaving);
                imagenActor.add(R.drawable.dominic_cooper);
                imagenActor.add(R.drawable.richard_armitage);
                imagenActor.add(R.drawable.stanley_tucci);
                intent.putExtra("fotosActores", imagenActor);
                startActivity(intent);
                break;
            case 1:
                elementoCA = 1;
                intent.putExtra("estado", elementoCA);
                intent.putExtra("nombre", nombres.get(1));
                intent.putExtra("foto", fotos.get(1));
                intent.putExtra("anio", anios.get(1));
                intent.putExtra("sinopsis", sinop.get(1));
                intent.putExtra("duracion", duraciones.get(1));
                intent.putExtra("listaActor", actoresSep.get(1));
                intent.putExtra("listaPer", personajesSep.get(1));
                imagenActor.clear();
                imagenActor.add(R.drawable.chris_evans);
                imagenActor.add(R.drawable.scarlett_johansson);
                imagenActor.add(R.drawable.anthonie_mackie);
                imagenActor.add(R.drawable.samuel_ljackson);
                imagenActor.add(R.drawable.cobie_smulders);
                imagenActor.add(R.drawable.frank_grillo);
                imagenActor.add(R.drawable.emily_vancamp);
                imagenActor.add(R.drawable.hayley_atwell);
                intent.putExtra("fotosActores", imagenActor);
                startActivity(intent);
                break;
            case 2:
                elementoCA = 2;
                intent.putExtra("estado", elementoCA);
                intent.putExtra("nombre", nombres.get(2));
                intent.putExtra("foto", fotos.get(2));
                intent.putExtra("anio", anios.get(2));
                intent.putExtra("sinopsis", sinop.get(2));
                intent.putExtra("duracion", duraciones.get(2));
                intent.putExtra("listaActor", actoresSep.get(2));
                intent.putExtra("listaPer", personajesSep.get(2));
                imagenActor.clear();
                imagenActor.add(R.drawable.chris_evans);
                imagenActor.add(R.drawable.robert_downey_jr);
                imagenActor.add(R.drawable.scarlett_johansson);
                imagenActor.add(R.drawable.elisabeth_olsen);
                imagenActor.add(R.drawable.sebastian_stan);
                imagenActor.add(R.drawable.anthonie_mackie);
                imagenActor.add(R.drawable.don_cheadle);
                imagenActor.add(R.drawable.jeremy_renner);
                intent.putExtra("fotosActores", imagenActor);
                startActivity(intent);
                break;
            default:
                break;
        }

    }
}