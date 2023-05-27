package com.pablo.trabajofingrado.Hulk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pablo.trabajofingrado.R;
import com.pablo.trabajofingrado.Spiderman.DatosSpiderman;
import com.pablo.trabajofingrado.Spiderman.MiAdapterSpiderman;
import com.pablo.trabajofingrado.ironman.DatosIM;
import com.pablo.trabajofingrado.ironman.MiAdapterIM;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class MiAdapterHulk extends RecyclerView.Adapter<MiAdapterHulk.ViewHolder>/* implements View.OnClickListener */{
    private ArrayList<DatosHulk> peliculasHulk;
    //private View.OnClickListener listener;
    private ArrayList<DatosHulk> listaOr;
    private ArrayList<DatosHulk> listarespaldo = new ArrayList<>();
    public ItemClicListener itemClicListener;
    Context context;

    public interface ItemClicListener{
        void itemClicked(DatosHulk datosHulk);
    }
    public MiAdapterHulk(Context context, ArrayList<DatosHulk> peliculasHulk, ItemClicListener itemClicListener) {
        this.context = context;
        this.peliculasHulk = peliculasHulk;
        this.itemClicListener = itemClicListener;

        listaOr = new ArrayList<>();
        listaOr.addAll(peliculasHulk);
        listarespaldo.addAll(peliculasHulk);
    }
    @NonNull
    @Override
    public MiAdapterHulk.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_spiderman, parent, false);
        //view.setOnClickListener(this);
        return new MiAdapterHulk.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MiAdapterHulk.ViewHolder holder, int position) {
        DatosHulk itemModal = peliculasHulk.get(position);
        holder.nombreHulk.setText(peliculasHulk.get(position).getNombreHulk());
        holder.descripcionHulk.setText(peliculasHulk.get(position).getDescripcionHulk());
        holder.logoHulk.setImageResource(peliculasHulk.get(position).getLogoHulk());

        holder.itemView.setOnClickListener(v -> itemClicListener.itemClicked(itemModal));
    }

    @Override
    public int getItemCount() {
        return peliculasHulk.size();
    }
    public void filtrar(String textoBuscado){
        if(textoBuscado.trim().length() == 0){
            peliculasHulk.clear();
            peliculasHulk.addAll(listaOr);
        }else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<DatosHulk> coleccion = peliculasHulk.stream().filter(i -> i.getNombreHulk()
                        .toLowerCase().contains(textoBuscado.toLowerCase())).collect(Collectors.toList());
                if (coleccion.size() == 0) {
                    peliculasHulk.clear();
                    peliculasHulk.addAll(listarespaldo);
                    Toast.makeText(context, "No hay registros", Toast.LENGTH_SHORT).show();
                } else {
                    peliculasHulk.clear();
                    peliculasHulk.addAll(coleccion);
                }
            }else {
                for (DatosHulk d: peliculasHulk) {
                    if(d.getNombreHulk().toLowerCase().contains(textoBuscado.toLowerCase())) {
                        peliculasHulk.add(d);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
    public void filtrado(String textoBuscado){
        if(textoBuscado.trim().length() == 0){
            peliculasHulk.clear();
            peliculasHulk.addAll(listaOr);
        }else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<DatosHulk> coleccion = peliculasHulk.stream().filter(i -> i.getNombreHulk()
                        .toLowerCase().contains(textoBuscado.toLowerCase())).collect(Collectors.toList());
                peliculasHulk.clear();
                peliculasHulk.addAll(coleccion);

            }else {
                for (DatosHulk d: peliculasHulk) {
                    if(d.getNombreHulk().toLowerCase().contains(textoBuscado.toLowerCase())) {
                        peliculasHulk.add(d);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreHulk;
        private TextView descripcionHulk;
        private ImageView logoHulk;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreHulk = itemView.findViewById(R.id.textViewItem1);
            descripcionHulk = itemView.findViewById(R.id.textViewItem2);
            logoHulk = itemView.findViewById(R.id.imageView);
        }
    }
}


