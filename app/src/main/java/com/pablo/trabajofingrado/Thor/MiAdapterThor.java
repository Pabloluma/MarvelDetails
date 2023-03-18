package com.pablo.trabajofingrado.Thor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pablo.trabajofingrado.CapAmerica.DatosCA;
import com.pablo.trabajofingrado.CapAmerica.MiAdapterCA;
import com.pablo.trabajofingrado.R;

public class MiAdapterThor extends RecyclerView.Adapter<MiAdapterThor.ViewHolder> implements View.OnClickListener  {
    private DatosThor[] discos;
    private View.OnClickListener listener;

    public MiAdapterThor(DatosThor[] discos) {
        this.discos = discos;
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
    // Cierra Clase ViewHolder
    @NonNull
    @Override
    public MiAdapterThor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_spiderman, parent, false);
        view.setOnClickListener(this);
        return new MiAdapterThor.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MiAdapterThor.ViewHolder holder, int position) {
        holder.nombreThor.setText(discos[position].getNombreThor());
        holder.descripcionThor.setText(discos[position].getDescripcionThor());
        holder.logoThor.setImageResource(discos[position].getLogoThor());
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
