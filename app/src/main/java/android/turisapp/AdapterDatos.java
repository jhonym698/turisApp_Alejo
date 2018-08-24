package android.turisapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by PC27 on 22/08/2018.
 */

public class AdapterDatos
        extends RecyclerView.Adapter<AdapterDatos.ViewHolderDatos> implements View.OnClickListener {

    ArrayList<Sitios> listaSitios;

    private View.OnClickListener listener;


    public AdapterDatos(ArrayList<Sitios> listaSitios) {
        this.listaSitios = listaSitios;
    }

    @Override
    public ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout=0;
        if (Utilidades.seleccionado== Utilidades.list){
            layout= R.layout.item_sitios_list;
        }else{
            layout= R.layout.item_sitios_grid;
        }
        View view=LayoutInflater.from(parent.getContext()).inflate(layout,null,false);

        view.setOnClickListener(this);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderDatos holder, int position) {

        if (Utilidades.seleccionado== Utilidades.list){
            holder.nombre.setText(listaSitios.get(position).getNombre());
            holder.descripcion.setText(listaSitios.get(position).getDescripcioncorta());
            holder.ubicacion.setText(listaSitios.get(position).getUbicacion());
            holder.imagen.setImageResource(Integer.parseInt(listaSitios.get(position).getFoto()));
        }else{
            holder.nombre.setText(listaSitios.get(position).getNombre());
            holder.imagen.setImageResource(Integer.parseInt(listaSitios.get(position).getFoto()));
        }
    }

    @Override
    public int getItemCount() {
        return listaSitios.size();
    }


    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }


    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {


        TextView nombre,descripcion,ubicacion;
        ImageView imagen;

        public ViewHolderDatos(View itemView) {
            super(itemView);
            if (Utilidades.seleccionado== Utilidades.list){
                nombre=(TextView) itemView.findViewById(R.id.nombre);
                descripcion=(TextView) itemView.findViewById(R.id.descripcion);
                ubicacion=(TextView) itemView.findViewById(R.id.ubicacion);
                imagen=(ImageView) itemView.findViewById(R.id.imagenLista);
            }else{
                nombre=(TextView) itemView.findViewById(R.id.nombre);
                imagen=(ImageView) itemView.findViewById(R.id.imagenLista);
            }


        }
    }
}
