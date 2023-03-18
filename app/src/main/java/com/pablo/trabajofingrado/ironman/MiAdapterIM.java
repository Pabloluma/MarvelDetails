package com.pablo.trabajofingrado.ironman;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pablo.trabajofingrado.R;


public class MiAdapterIM extends RecyclerView.Adapter<MiAdapterIM.ViewHolder> implements View.OnClickListener {
    private DatosIM[] discos;
    private View.OnClickListener listener;

    public MiAdapterIM(DatosIM[] discos) {
        this.discos = discos;
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
    @NonNull
    @Override
    public MiAdapterIM.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_spiderman, parent, false);
        view.setOnClickListener(this);
        return new MiAdapterIM.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MiAdapterIM.ViewHolder holder, int position) {
        holder.nombreIM.setText(discos[position].getNombreIM());
        holder.descripcionIM.setText(discos[position].getDescripcionIM());
        holder.logoIM.setImageResource(discos[position].getLogoIM());
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
