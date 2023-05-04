package com.pablo.trabajofingrado.Hulk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pablo.trabajofingrado.R;
import com.pablo.trabajofingrado.Thor.AdapterActoresThor;
import com.pablo.trabajofingrado.Thor.DatosActoresThor;

import java.util.ArrayList;


public class AdaptadorActoresHulk extends RecyclerView.Adapter<AdaptadorActoresHulk.ViewHolder> implements View.OnClickListener{
    private ArrayList<DatosActoresHulk> datosActores;
    private View.OnClickListener listener;

    public AdaptadorActoresHulk(ArrayList<DatosActoresHulk> datosActores) {
        this.datosActores = datosActores;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreActorHulk;
        private TextView personajeHulk;
        private ImageView fotoHulk;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreActorHulk = itemView.findViewById(R.id.textView5);
            personajeHulk = itemView.findViewById(R.id.textView7);
            fotoHulk = itemView.findViewById(R.id.imageView4);
        }
    }
    @NonNull
    @Override
    public AdaptadorActoresHulk.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actores, parent, false);
        view.setOnClickListener(this);
        return new AdaptadorActoresHulk.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorActoresHulk.ViewHolder holder, int position) {
        holder.nombreActorHulk.setText(datosActores.get(position).getNombreActorHulk());
        holder.personajeHulk.setText(datosActores.get(position).getPersonajeHulk());
        holder.fotoHulk.setImageResource(datosActores.get(position).getFotoHulk());
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
