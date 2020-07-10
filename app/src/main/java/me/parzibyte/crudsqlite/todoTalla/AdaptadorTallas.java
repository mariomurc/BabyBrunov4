package me.parzibyte.crudsqlite;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.parzibyte.crudsqlite.modelos.Talla;

public class AdaptadorTallas extends RecyclerView.Adapter<AdaptadorTallas.MyViewHolder> {

    private List<Talla> listaDeTallas;

    public void setListaDeTallas(List<Talla> listaDeTallas) {
        this.listaDeTallas = listaDeTallas;
    }

    public AdaptadorTallas(List<Talla> tallas) {
        this.listaDeTallas = tallas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View filaTalla = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fila_talla, viewGroup, false);
        return new MyViewHolder(filaTalla);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        // Obtener la caca de nuestra lista gracias al índice i
        Talla talla = listaDeTallas.get(i);

        // Obtener los datos de la lista
        float peso = talla.getPeso();
        int altura = talla.getAltura();
        String comentario = talla.getComentario();
        // Y poner a los TextView los datos con setText
        myViewHolder.peso.setText((int) peso);
        myViewHolder.altura.setText((altura));
        myViewHolder.comentario.setText(comentario);
    }

    @Override
    public int getItemCount() {
        return listaDeTallas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView peso, altura, comentario;

        MyViewHolder(View itemView) {
            super(itemView);
            this.peso = itemView.findViewById(R.id.tvPeso);
            this.altura = itemView.findViewById(R.id.tvAltura);
            this.comentario = itemView.findViewById(R.id.tvComentario);
        }
    }
}
