package com.pablo.trabajofingrado.CapAmerica;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pablo.trabajofingrado.Hulk.DatosHulk;
import com.pablo.trabajofingrado.Hulk.MiAdapterHulk;
import com.pablo.trabajofingrado.R;
import com.pablo.trabajofingrado.ironman.DatosIM;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MiAdapterCA extends RecyclerView.Adapter<MiAdapterCA.ViewHolder> /*implements View.OnClickListener*/ {
    private ArrayList<DatosCA> peliculasCA;
    //private View.OnClickListener listener;
    private ArrayList<DatosCA> listaOr;
    private ArrayList<DatosCA> listarespaldo = new ArrayList<>();
    ItemClicListener itemClicListener;
    Context context;

    public interface ItemClicListener{
        void itemClicked(DatosCA datosCA);
    }

    public MiAdapterCA(Context context,ArrayList<DatosCA> peliculasCA, ItemClicListener itemClicListener) {
        this.context = context;
        this.peliculasCA = peliculasCA;
        this.itemClicListener = itemClicListener;

        listaOr = new ArrayList<>();
        listaOr.addAll(peliculasCA);
        listarespaldo.addAll(peliculasCA);

    }
    @NonNull
    @Override
    public MiAdapterCA.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_spiderman, parent, false);
        return new MiAdapterCA.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MiAdapterCA.ViewHolder holder, int position) {
        DatosCA itemPos = peliculasCA.get(position);
        holder.nombreCA.setText(peliculasCA.get(position).getNombreCA());
        holder.descripcionCA.setText(peliculasCA.get(position).getDescripcionCA());
        holder.logoCA.setImageResource(peliculasCA.get(position).getLogoCA());
        holder.itemView.setOnClickListener(v -> itemClicListener.itemClicked(itemPos));
    }

    @Override
    public int getItemCount() {
        return peliculasCA.size();
    }
    public void filtrar(String textoBuscado){
        if(textoBuscado.trim().length() == 0){
            peliculasCA.clear();
            peliculasCA.addAll(listaOr);
        }else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<DatosCA> coleccion = peliculasCA.stream().filter(i -> i.getNombreCA()
                        .toLowerCase().contains(textoBuscado.toLowerCase())).collect(Collectors.toList());
                if (coleccion.size() == 0) {
                    peliculasCA.clear();
                    peliculasCA.addAll(listarespaldo);
                    Toast.makeText(context, "No hay registros", Toast.LENGTH_SHORT).show();
                } else {
                    peliculasCA.clear();
                    peliculasCA.addAll(coleccion);
                }
            }else {
                for (DatosCA d: peliculasCA) {
                    if(d.getNombreCA().toLowerCase().contains(textoBuscado.toLowerCase())) {
                        peliculasCA.add(d);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
    public void filtrado(String textoBuscado){
        if(textoBuscado.trim().length() == 0){
            peliculasCA.clear();
            peliculasCA.addAll(listaOr);
        }else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<DatosCA> coleccion = peliculasCA.stream().filter(i -> i.getNombreCA()
                        .toLowerCase().contains(textoBuscado.toLowerCase())).collect(Collectors.toList());
                peliculasCA.clear();
                peliculasCA.addAll(coleccion);

            }else {
                for (DatosCA d: peliculasCA) {
                    if(d.getNombreCA().toLowerCase().contains(textoBuscado.toLowerCase())) {
                        peliculasCA.add(d);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nombreCA;
        private TextView descripcionCA;
        private ImageView logoCA;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreCA = itemView.findViewById(R.id.textViewItem1);
            descripcionCA = itemView.findViewById(R.id.textViewItem2);
            logoCA = itemView.findViewById(R.id.imageView);
        }
    }
}
