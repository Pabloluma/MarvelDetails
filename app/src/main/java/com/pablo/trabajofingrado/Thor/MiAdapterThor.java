package com.pablo.trabajofingrado.Thor;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pablo.trabajofingrado.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MiAdapterThor extends RecyclerView.Adapter<MiAdapterThor.ViewHolder>{
    private ArrayList<DatosThor> peliculasThor;
    private ArrayList<DatosThor> listaOr;
    private ArrayList<DatosThor> listarespaldo = new ArrayList<>();
    public ItemClicListener itemClicListener;
    Context context;

    public interface ItemClicListener{
        void itemClicked(DatosThor datosThor);
    }

   public MiAdapterThor(Context context,ArrayList<DatosThor> peliculasThor, ItemClicListener itemClicListener) {
        this.context = context;
        this.peliculasThor = peliculasThor;
        this.itemClicListener = itemClicListener;


       listaOr = new ArrayList<>();
       listaOr.addAll(peliculasThor);
       listarespaldo.addAll(peliculasThor);
   }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_spiderman, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MiAdapterThor.ViewHolder holder, int position) {
        DatosThor itemModal = peliculasThor.get(position);
        holder.nombreThor.setText(peliculasThor.get(position).getNombreThor());
        holder.descripcionThor.setText(peliculasThor.get(position).getDescripcionThor());
        holder.logoThor.setImageResource(peliculasThor.get(position).getLogoThor());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClicListener.itemClicked(itemModal);
            }
        });
    }

    @Override
    public int getItemCount() {
        return peliculasThor.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreThor;
        private TextView descripcionThor;
        private ImageView logoThor;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreThor = itemView.findViewById(R.id.textViewItem1);
            descripcionThor = itemView.findViewById(R.id.textViewItem2);
            logoThor = itemView.findViewById(R.id.imageView);
        }
    }

    public void filtrar(String textoBuscado){
       if(textoBuscado.trim().length() == 0){
           peliculasThor.clear();
           peliculasThor.addAll(listaOr);
       }else{
           if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
               List<DatosThor> coleccion = peliculasThor.stream().filter(i -> i.getNombreThor()
                       .toLowerCase().contains(textoBuscado.toLowerCase())).collect(Collectors.toList());
               if (coleccion.size() == 0) {
                   peliculasThor.clear();
                   peliculasThor.addAll(listarespaldo);
                   Toast.makeText(context, "No hay registros", Toast.LENGTH_SHORT).show();
               } else {
                   peliculasThor.clear();
                   peliculasThor.addAll(coleccion);
               }
           }else {
               for (DatosThor d: peliculasThor) {
                   if(d.getNombreThor().toLowerCase().contains(textoBuscado.toLowerCase())) {
                       peliculasThor.add(d);
                   }
               }
           }
       }
       notifyDataSetChanged();
    }
    public void filtrado(String textoBuscado){
        if(textoBuscado.trim().length() == 0){
            peliculasThor.clear();
            peliculasThor.addAll(listaOr);
        }else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<DatosThor> coleccion = peliculasThor.stream().filter(i -> i.getNombreThor()
                        .toLowerCase().contains(textoBuscado.toLowerCase())).collect(Collectors.toList());
                peliculasThor.clear();
                peliculasThor.addAll(coleccion);

            }else {
                for (DatosThor d: peliculasThor) {
                    if(d.getNombreThor().toLowerCase().contains(textoBuscado.toLowerCase())) {
                        peliculasThor.add(d);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
}
