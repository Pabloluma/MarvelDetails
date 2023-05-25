package com.pablo.trabajofingrado.Hulk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pablo.trabajofingrado.R;
import com.pablo.trabajofingrado.Spiderman.DatosSpiderman;
import com.pablo.trabajofingrado.Spiderman.MiAdapterSpiderman;

import java.util.ArrayList;


public class MiAdapterHulk extends RecyclerView.Adapter<MiAdapterHulk.ViewHolder> implements View.OnClickListener {
    private ArrayList<DatosHulk> peliculasHulk;
    private View.OnClickListener listener;

    public MiAdapterHulk(ArrayList<DatosHulk> peliculasHulk) {
        this.peliculasHulk = peliculasHulk;
    }
    //Clase ViewHolder
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
    // Cierra Clase ViewHolder
    @NonNull
    @Override
    public MiAdapterHulk.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_spiderman, parent, false);
        view.setOnClickListener(this);
        return new MiAdapterHulk.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MiAdapterHulk.ViewHolder holder, int position) {
        holder.nombreHulk.setText(peliculasHulk.get(position).getNombreHulk());
        holder.descripcionHulk.setText(peliculasHulk.get(position).getDescripcionHulk());
        holder.logoHulk.setImageResource(peliculasHulk.get(position).getLogoHulk());
    }

    @Override
    public int getItemCount() {
        return peliculasHulk.size();
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


