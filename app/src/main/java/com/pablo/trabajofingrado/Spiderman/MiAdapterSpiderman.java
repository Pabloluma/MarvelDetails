package com.pablo.trabajofingrado.Spiderman;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pablo.trabajofingrado.R;

public class MiAdapterSpiderman extends RecyclerView.Adapter<MiAdapterSpiderman.ViewHolder> implements View.OnClickListener {
    private DatosSpiderman[] discos;
    private View.OnClickListener listener;

    public MiAdapterSpiderman(DatosSpiderman[] discos) {
        this.discos = discos;
    }

//Clase ViewHolder
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
// Cierra Clase ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_spiderman, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DatosSpiderman ld = discos[position];
        holder.nombre.setText(discos[position].getNombre());
        holder.descripcion.setText(discos[position].getDescripcion());
        holder.logo.setImageResource(discos[position].getLogo());
    }

    @Override
    public int getItemCount() {
        return discos.length;
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onClick(v);
        }

    }


}

