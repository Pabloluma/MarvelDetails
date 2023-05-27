package com.pablo.trabajofingrado;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomList extends ArrayAdapter<String> {
    private final Activity contexto;
    private final ArrayList<String> grupo;
    private final int[] logo;

        public CustomList(Activity contexto, ArrayList<String> grupo,int[] logo) {
            super(contexto,R.layout.cardview_temas,grupo);
            this.contexto = contexto;
            this.grupo = grupo;
            this.logo = logo;
        }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = contexto.getLayoutInflater();
        View fila = inflater.inflate(R.layout.cardview_temas,null,true);

        TextView grupos = fila.findViewById(R.id.textView3);
        ImageView logos = fila.findViewById(R.id.imageView3);

        grupos.setText(grupo.get(position));
        logos.setImageResource(logo[position]);

        return fila;
    }

}
