package com.pablo.trabajofingrado.ironman;

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
import com.pablo.trabajofingrado.R;
import com.pablo.trabajofingrado.temaspelisGoogle;

import java.util.ArrayList;

public class IM_item extends AppCompatActivity {

    DatabaseReference mybd;
    ArrayList<String> nombres = new ArrayList<>();
    ArrayList<String> anios = new ArrayList<>();
    ArrayList<String> sinop = new ArrayList<>();
    ArrayList<Integer> fotos = new ArrayList<>();
    ArrayList<String> duraciones = new ArrayList<>();
    DatosIM[] listaPelis = new DatosIM[3];
    static int elementoIM = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        getWindow().setStatusBarColor(getResources().getColor(R.color.redMarvel));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.redMarvel)));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_home_24_white);
        getSupportActionBar().setTitle("Iron Man");
        nombrePelis();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
//                /*startActivity(new Intent(getApplicationContext(), temaspelisGoogle.class));
//                supportFinishAfterTransition(); */
//                startActivity(new Intent(getApplicationContext(), getParent().getClass()));
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
                        listaPelis[i] = objeto;
                    }
                    RecyclerView recyclerView = findViewById(R.id.recyclerViewIM);
                    MiAdapterIM adapter = new MiAdapterIM(listaPelis);
                    recyclerView.setAdapter(adapter);
                    Intent intent = new Intent(getApplicationContext(), IMInfo.class);

                    adapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (recyclerView.getChildAdapterPosition(v)){
                                case 0:
                                    elementoIM = 0;
                                    intent.putExtra("estado", elementoIM);
                                    intent.putExtra("nombre", nombres.get(0));
                                    intent.putExtra("foto", fotos.get(0));
                                    intent.putExtra("anio", anios.get(0));
                                    intent.putExtra("sinopsis", sinop.get(0));
                                    intent.putExtra("duracion", duraciones.get(0));
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