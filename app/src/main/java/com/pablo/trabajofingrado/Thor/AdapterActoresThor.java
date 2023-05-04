package com.pablo.trabajofingrado.Thor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pablo.trabajofingrado.R;

import java.util.ArrayList;

public class AdapterActoresThor extends RecyclerView.Adapter<AdapterActoresThor.ViewHolder> implements View.OnClickListener {
    private ArrayList<DatosActoresThor> datosActores;
    private View.OnClickListener listener;

    public AdapterActoresThor(ArrayList<DatosActoresThor> datosActores) {
        this.datosActores = datosActores;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreActorThor;
        private TextView personajeThor;
        private ImageView fotoThor;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreActorThor = itemView.findViewById(R.id.textView5);
            personajeThor = itemView.findViewById(R.id.textView7);
            fotoThor = itemView.findViewById(R.id.imageView4);
        }
    }
    @NonNull
    @Override
    public AdapterActoresThor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actores, parent, false);
        view.setOnClickListener(this);
        return new AdapterActoresThor.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterActoresThor.ViewHolder holder, int position) {
        holder.nombreActorThor.setText(datosActores.get(position).getNombreActorThor());
        holder.personajeThor.setText(datosActores.get(position).getPersonajeThor());
        holder.fotoThor.setImageResource(datosActores.get(position).getFotoThor());
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

