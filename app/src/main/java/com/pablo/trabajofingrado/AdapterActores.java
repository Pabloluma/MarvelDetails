package com.pablo.trabajofingrado;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterActores extends RecyclerView.Adapter<AdapterActores.ViewHolder> implements View.OnClickListener{
    private ArrayList<DatosActores> datosActores;
    private View.OnClickListener listener;

    public AdapterActores(ArrayList<DatosActores> datosActores) {
        this.datosActores = datosActores;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView nombre;
        private TextView personaje;
        private ImageView foto;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.textView5);
            personaje = itemView.findViewById(R.id.textView7);
            foto = itemView.findViewById(R.id.imageView4);
        }
    }
    @NonNull
    @Override
    public AdapterActores.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actores, parent, false);
        view.setOnClickListener(this);
        return new AdapterActores.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterActores.ViewHolder holder, int position) {
        holder.nombre.setText(datosActores.get(position).getNombre());
        holder.personaje.setText(datosActores.get(position).getPersonaje());
        holder.foto.setImageResource(datosActores.get(position).getFoto());
    }

    @Override
    public int getItemCount() {
        return datosActores.size();
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
