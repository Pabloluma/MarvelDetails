package com.pablo.trabajofingrado.CapAmerica;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pablo.trabajofingrado.Hulk.DatosHulk;
import com.pablo.trabajofingrado.Hulk.MiAdapterHulk;
import com.pablo.trabajofingrado.R;

public class MiAdapterCA extends RecyclerView.Adapter<MiAdapterCA.ViewHolder> implements View.OnClickListener {
    private DatosCA[] discos;
    private View.OnClickListener listener;

    public MiAdapterCA(DatosCA[] discos) {
        this.discos = discos;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

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
    // Cierra Clase ViewHolder
    @NonNull
    @Override
    public MiAdapterCA.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_spiderman, parent, false);
        view.setOnClickListener(this);
        return new MiAdapterCA.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MiAdapterCA.ViewHolder holder, int position) {
        holder.nombreCA.setText(discos[position].getNombreCA());
        holder.descripcionCA.setText(discos[position].getDescripcionCA());
        holder.logoCA.setImageResource(discos[position].getLogoCA());
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
