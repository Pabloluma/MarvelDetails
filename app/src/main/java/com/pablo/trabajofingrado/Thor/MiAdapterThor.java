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
import java.util.List;
import java.util.stream.Collectors;

public class MiAdapterThor extends RecyclerView.Adapter<MiAdapterThor.ViewHolder> /*implements View.OnClickListener*/  {
    private ArrayList<DatosThor> peliculasThor;
    //private View.OnClickListener listener;




    private ArrayList<DatosThor> listaOr;
    public ItemClicListener itemClicListener;

    public interface ItemClicListener{
        void itemClicked(DatosThor datosThor);
        //void itemClickedArraylist(ArrayList<DatosThor> datosThors);
    }

   public MiAdapterThor(ArrayList<DatosThor> peliculasThor, ItemClicListener itemClicListener) {
       this.peliculasThor = peliculasThor;
       this.itemClicListener = itemClicListener;


       listaOr = new ArrayList<>();
       listaOr.addAll(peliculasThor);

   }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_spiderman, parent, false);
       // view.setOnClickListener(this);
        return new ViewHolder(view);

    }
    @Override
    public void onBindViewHolder(@NonNull MiAdapterThor.ViewHolder holder, int position) {
        DatosThor itemModal = peliculasThor.get(position);
        holder.nombreThor.setText(peliculasThor.get(position).getNombreThor());
        holder.descripcionThor.setText(peliculasThor.get(position).getDescripcionThor());
        holder.logoThor.setImageResource(peliculasThor.get(position).getLogoThor());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClicListener.itemClicked(itemModal);
            }
        });

    }
    @Override
    public int getItemCount() {
        return peliculasThor.size();
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

    public void filtrar(String textoBuscado){
       if(textoBuscado.trim().length() == 0){
           peliculasThor.clear();
           peliculasThor.addAll(listaOr);
       }else{
           if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
               List<DatosThor> coleccion = peliculasThor.stream().filter(i->i.getNombreThor()
                       .toLowerCase().contains(textoBuscado.toLowerCase())).collect(Collectors.toList());
               peliculasThor.clear();
               peliculasThor.addAll(coleccion);
           }else {
               for (DatosThor d: peliculasThor) {
                   if(d.getNombreThor().toLowerCase().contains(textoBuscado.toLowerCase())) {
                       peliculasThor.add(d);
                   }
               }
           }
       }
       notifyDataSetChanged();
    }




   /* public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onClick(v);
        }

    }*/

}
