package com.pablo.trabajofingrado.Hulk;

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

public class hulk_item extends AppCompatActivity implements MiAdapterHulk.ItemClicListener {
    DatabaseReference mybd;
    ArrayList<String> nombres = new ArrayList<>();
    ArrayList<String> anios = new ArrayList<>();
    ArrayList<String> sinop = new ArrayList<>();
    ArrayList<Integer> fotos = new ArrayList<>();
    ArrayList<String> duraciones = new ArrayList<>();
    ArrayList <String[]> actoresSep = new ArrayList<>();
    ArrayList <String[]> personajesSep = new ArrayList<>();
    ArrayList<Integer> imagenActor = new ArrayList<>();
    ArrayList<DatosHulk> listaPelis = new ArrayList<>();
    static int elementoHulk = 0;
    MiAdapterHulk adapter;
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
        mybd.child("Personajes").child("1").child("peliculas").addValueEventListener(new ValueEventListener() {
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

                        hulk_item.this.actoresSep.add(ac);
                        hulk_item.this.personajesSep.add(perso);
                       
                        hulk_item.this.nombres.add(nombre);
                        hulk_item.this.anios.add(anyo);
                        hulk_item.this.sinop.add(descrip);
                        hulk_item.this.duraciones.add(duracion);
                    }
                    fotos.add(R.drawable.cartelera_hulk);
                    fotos.add(R.drawable.cartelera_hulk2);

                    for (int i = 0; i < nombres.size(); i++) {
                        DatosHulk objeto = new DatosHulk(nombres.get(i), anios.get(i), fotos.get(i));
                        listaPelis.add(objeto);
                    }
                    RecyclerView recyclerView = findViewById(R.id.recyclerViewHulk);
                    adapter = new MiAdapterHulk(getApplicationContext(),listaPelis, hulk_item.this);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void itemClicked(DatosHulk datosHulk) {
        int posicion = nombres.indexOf(datosHulk.getNombreHulk());
        Intent intent = new Intent(getApplicationContext(), HulkInfo.class);
        switch (posicion){
            case 0:
                elementoHulk = 0;
                intent.putExtra("estado", elementoHulk);
                intent.putExtra("nombre", nombres.get(0));
                intent.putExtra("foto", fotos.get(0));
                intent.putExtra("anio", anios.get(0));
                intent.putExtra("sinopsis", sinop.get(0));
                intent.putExtra("duracion", duraciones.get(0));
                intent.putExtra("listaActor", actoresSep.get(0));
                intent.putExtra("listaPer", personajesSep.get(0));
                imagenActor.clear();
                imagenActor.add(R.drawable.sam_elliot);
                imagenActor.add(R.drawable.eric_bana);
                imagenActor.add(R.drawable.nick_nolte);
                imagenActor.add(R.drawable.jennifer_connelly);
                imagenActor.add(R.drawable.josh_lucas);
                intent.putExtra("fotosActores", imagenActor);
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
                intent.putExtra("listaActor", actoresSep.get(1));
                intent.putExtra("listaPer", personajesSep.get(1));
                imagenActor.clear();
                imagenActor.add(R.drawable.william_hurt);
                imagenActor.add(R.drawable.edwart_noton);
                imagenActor.add(R.drawable.liv_tyler);
                imagenActor.add(R.drawable.tim_roth);
                imagenActor.add(R.drawable.tim_blake);
                imagenActor.add(R.drawable.ty_burrell);
                imagenActor.add(R.drawable.christina_cabot);
                imagenActor.add(R.drawable.peter_mensah);
                imagenActor.add(R.drawable.lou_ferrigno);
                imagenActor.add(R.drawable.paul_soles);
                imagenActor.add(R.drawable.debora_nascimiento);
                intent.putExtra("fotosActores", imagenActor);
                startActivity(intent);
                break;
            default:
                Toast.makeText(this, "Hay que añadir uno nuevo al switch", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}