package com.example.genio10;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class Actividades_adapter extends FirestoreRecyclerAdapter <Actividad, Actividades_adapter.ViewHolder>{


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Actividades_adapter(@NonNull FirestoreRecyclerOptions<Actividad> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Actividad actividad) {
        viewHolder.actividad_unidad.setText("Unidad: " + actividad.getUnidad());
        viewHolder.actividad_fecha.setText("Fecha de Realización: \n" + actividad.getFecha());
        viewHolder.actividad_num.setText("Ejercicios Realizados: " + actividad.getNum_Ejercicios());
        viewHolder.actividad_aciertos.setText("Aciertos: " + actividad.getAciertos());
        viewHolder.actividad_fallos.setText("Fallos / Reintentos: " + actividad.getFallos());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_actividades, viewGroup, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView actividad_unidad;
        TextView actividad_fecha;
        TextView actividad_num;
        TextView actividad_aciertos;
        TextView actividad_fallos;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            actividad_unidad = itemView.findViewById(R.id.actividad_unidad);
            actividad_fecha = itemView.findViewById(R.id.actividad_fecha);
            actividad_num = itemView.findViewById(R.id.actividad_num);
            actividad_aciertos = itemView.findViewById(R.id.actividad_aciertos);
            actividad_fallos = itemView.findViewById(R.id.actividad_fallos);
        }
    }

}
