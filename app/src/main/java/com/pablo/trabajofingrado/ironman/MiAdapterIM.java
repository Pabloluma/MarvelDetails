package com.pablo.trabajofingrado.ironman;

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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class MiAdapterIM extends RecyclerView.Adapter<MiAdapterIM.ViewHolder>{
    private ArrayList<DatosIM> peliculasIM;
    private ArrayList<DatosIM> listaOr;
    private ArrayList<DatosIM> listarespaldo = new ArrayList<>();
   public ItemClicListener itemClicListener;
    Context context;




    public interface ItemClicListener{
        void itemClicked(DatosIM datosIM);
    }
    public MiAdapterIM(Context context,ArrayList<DatosIM> peliculasIM, ItemClicListener itemClicListener) {
        this.context = context;
        this.peliculasIM = peliculasIM;
        this.itemClicListener = itemClicListener;

        listaOr = new ArrayList<>();
        listaOr.addAll(peliculasIM);
        listarespaldo.addAll(peliculasIM);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_spiderman, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MiAdapterIM.ViewHolder holder, int position) {
        DatosIM itemModal = peliculasIM.get(position);
        holder.nombreIM.setText(peliculasIM.get(position).getNombreIM());
        holder.descripcionIM.setText(peliculasIM.get(position).getDescripcionIM());
        holder.logoIM.setImageResource(peliculasIM.get(position).getLogoIM());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClicListener.itemClicked(itemModal);
            }
        });
    }
    @Override
    public int getItemCount() {
        return peliculasIM.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreIM;
        private TextView descripcionIM;
        private ImageView logoIM;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreIM = itemView.findViewById(R.id.textViewItem1);
            descripcionIM = itemView.findViewById(R.id.textViewItem2);
            logoIM = itemView.findViewById(R.id.imageView);
        }
    }
    public void filtrar(String textoBuscado){
        if(textoBuscado.trim().length() == 0){
            peliculasIM.clear();
            peliculasIM.addAll(listaOr);
        }else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<DatosIM> coleccion = peliculasIM.stream().filter(i -> i.getNombreIM()
                        .toLowerCase().contains(textoBuscado.toLowerCase())).collect(Collectors.toList());
                if (coleccion.size() == 0) {
                    peliculasIM.clear();
                    peliculasIM.addAll(listarespaldo);
                    Toast.makeText(context, "No hay registros", Toast.LENGTH_SHORT).show();
                } else {
                    peliculasIM.clear();
                    peliculasIM.addAll(coleccion);
                }
            }else {
                for (DatosIM d: peliculasIM) {
                    if(d.getNombreIM().toLowerCase().contains(textoBuscado.toLowerCase())) {
                        peliculasIM.add(d);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
    public void filtrado(String textoBuscado){
        if(textoBuscado.trim().length() == 0){
            peliculasIM.clear();
            peliculasIM.addAll(listaOr);
        }else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<DatosIM> coleccion = peliculasIM.stream().filter(i -> i.getNombreIM()
                        .toLowerCase().contains(textoBuscado.toLowerCase())).collect(Collectors.toList());
                peliculasIM.clear();
                peliculasIM.addAll(coleccion);

            }else {
                for (DatosIM d: peliculasIM) {
                    if(d.getNombreIM().toLowerCase().contains(textoBuscado.toLowerCase())) {
                        peliculasIM.add(d);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
}
