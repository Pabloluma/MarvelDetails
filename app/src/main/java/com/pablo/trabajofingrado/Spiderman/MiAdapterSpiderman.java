package com.pablo.trabajofingrado.Spiderman;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.pablo.trabajofingrado.R;
import com.pablo.trabajofingrado.Thor.DatosThor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MiAdapterSpiderman extends RecyclerView.Adapter<MiAdapterSpiderman.ViewHolder>{
    private ArrayList<DatosSpiderman> peliculasSP;
    private ArrayList<DatosSpiderman> listaOr;
    private ArrayList<DatosSpiderman> listarespaldo = new ArrayList<>();
    public ItemClicListener itemClicListener;
    Context context;

    public interface ItemClicListener{
        void itemClicked(DatosSpiderman datosSpiderman);
    }

    public MiAdapterSpiderman(Context context, ArrayList<DatosSpiderman> peliculasSP, ItemClicListener itemClicListener) {
        this.context = context;
        this.peliculasSP = peliculasSP;
        this.itemClicListener = itemClicListener;


        listaOr = new ArrayList<>();
        listaOr.addAll(peliculasSP);
        listarespaldo.addAll(peliculasSP);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_spiderman, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DatosSpiderman posDsp = peliculasSP.get(position);
        holder.nombre.setText(peliculasSP.get(position).getNombre());
        holder.descripcion.setText(peliculasSP.get(position).getDescripcion());
        holder.logo.setImageResource(peliculasSP.get(position).getLogo());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClicListener.itemClicked(posDsp);
            }
        });
    }

    @Override
    public int getItemCount() {
        return peliculasSP.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView nombre;
        private TextView descripcion;
        private ImageView logo;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.textViewItem1);
            descripcion = itemView.findViewById(R.id.textViewItem2);
            logo = itemView.findViewById(R.id.imageView);
        }
    }
    public void filtrar(String textoBuscado){
        if(textoBuscado.trim().length() == 0){
            peliculasSP.clear();
            peliculasSP.addAll(listaOr);
        }else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<DatosSpiderman> coleccion = peliculasSP.stream().filter(i->i.getNombre()
                        .toLowerCase().contains(textoBuscado.toLowerCase())).collect(Collectors.toList());
                if(coleccion.size() == 0){
                    peliculasSP.clear();
                    peliculasSP.addAll(listarespaldo);

                    Toast.makeText( context,"No hay registros", Toast.LENGTH_SHORT).show();
                }else{
                    peliculasSP.clear();
                    peliculasSP.addAll(coleccion);
                }

            }else {
                for (DatosSpiderman d: peliculasSP) {
                    if(d.getNombre().toLowerCase().contains(textoBuscado.toLowerCase())) {
                        peliculasSP.add(d);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
    public void filtrado(String textoBuscado){
        if(textoBuscado.trim().length() == 0){
            peliculasSP.clear();
            peliculasSP.addAll(listaOr);
        }else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<DatosSpiderman> coleccion = peliculasSP.stream().filter(i->i.getNombre()
                        .toLowerCase().contains(textoBuscado.toLowerCase())).collect(Collectors.toList());
                peliculasSP.clear();
                peliculasSP.addAll(coleccion);

            }else {
                for (DatosSpiderman d: peliculasSP) {
                    if(d.getNombre().toLowerCase().contains(textoBuscado.toLowerCase())) {
                        peliculasSP.add(d);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
}

