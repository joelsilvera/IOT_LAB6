package com.example.labiot5.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.labiot5.Entity.Actividad;
import com.example.labiot5.R;

import java.util.ArrayList;

public class ActividadAdapter extends RecyclerView.Adapter<ActividadAdapter.ViewHolder> {
    private ArrayList<Actividad> listaActividades;
    Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView imageView;
        private final TextView nombreActividad;
        private final TextView horaActividad;
        private final TextView descripcionActividad;
        private final Button editarBtn;
        private final Button eliminarBtn;


        public ViewHolder(View view){
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imagenAct);
            nombreActividad = (TextView) view.findViewById(R.id.nombreAct_tv);
            horaActividad = (TextView) view.findViewById(R.id.horaAct_tv);
            descripcionActividad = (TextView) view.findViewById(R.id.descripcionAct_tv);
            editarBtn = (Button) view.findViewById(R.id.editar_btn);
            eliminarBtn = (Button) view.findViewById(R.id.eliminar_btn);
        }
        public TextView getTextView(){
            return getTextView();
        }

    }

    public ActividadAdapter(ArrayList<Actividad> dataSet){
        listaActividades=dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_actividad,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Actividad actividad = listaActividades.get(position);

        String urlImage = actividad.getImagenUrl();
        String mostrarNombre = actividad.getTitulo();
        String mostrarHorario = "Fecha: "+ actividad.getFecha() + " Hora: " + actividad.getHoraInicio()+"-"+actividad.getHoraFin();
        String mostrarDescri = actividad.getDescripcion();


        ImageView imageView = holder.imageView.findViewById(R.id.imagenAct);
        TextView nombreActividad = holder.itemView.findViewById(R.id.nombreAct_tv);
        TextView horaActividad = holder.itemView.findViewById(R.id.horaAct_tv);
        TextView descripcionActividad = holder.itemView.findViewById(R.id.descripcionAct_tv);

        nombreActividad.setText(mostrarNombre);
        horaActividad.setText(mostrarHorario);
        descripcionActividad.setText(mostrarDescri);

        Glide.with(imageView).load(urlImage).into(imageView);

    }

    @Override
    public int getItemCount() {
        return listaActividades.size();
    }

}
