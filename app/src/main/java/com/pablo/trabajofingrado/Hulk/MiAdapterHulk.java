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


public class MiAdapterHulk extends RecyclerView.Adapter<MiAdapterHulk.ViewHolder> implements View.OnClickListener {
    private DatosHulk[] discos;
    private View.OnClickListener listener;

    public MiAdapterHulk(DatosHulk[] discos) {
        this.discos = discos;
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
        holder.nombreHulk.setText(discos[position].getNombreHulk());
        holder.descripcionHulk.setText(discos[position].getDescripcionHulk());
        holder.logoHulk.setImageResource(discos[position].getLogoHulk());
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


