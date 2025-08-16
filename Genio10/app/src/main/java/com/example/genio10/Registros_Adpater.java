package com.example.genio10;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class Registros_Adpater extends FirestoreRecyclerAdapter<Alumno, Registros_Adpater.ViewHolder> {

    Activity activity;
    FirebaseFirestore db;
    String Aux_correo, aux_registros, aux_identificador, aux_nombre;
    int num_registros = 0;
    FirebaseFirestore registrosdb;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Registros_Adpater(@NonNull FirestoreRecyclerOptions<Alumno> options, Activity activity, String Correo) {
        super(options);
        this.activity = activity;
        registrosdb = FirebaseFirestore.getInstance();
        db = FirebaseFirestore.getInstance();
        this.Aux_correo = Correo;
        ObtenerRegistros();
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Alumno alumno) {

        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(viewHolder.getAdapterPosition());

        final String id = documentSnapshot.getId();
        final String Nombre = documentSnapshot.getString("Nombre");
        final String Apellidos = documentSnapshot.getString("Apellidos");
        final String Edad = documentSnapshot.getString("Edad");
        final String Fecha = documentSnapshot.getString("Fecha");

        viewHolder.txtNombre.setText("Nombre: " + alumno.getNombre());
        viewHolder.txtApellidos.setText("Apellidos: " + alumno.getApellidos());
        viewHolder.txtEdad.setText("Edad: " + alumno.getEdad());
        viewHolder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, actualizacion_registro.class);
                intent.putExtra("Cuenta", id);
                intent.putExtra("Nombre", Nombre);
                intent.putExtra("Apellidos", Apellidos);
                intent.putExtra("Edad", Edad);
                intent.putExtra("Fecha", Fecha);
                activity.startActivity(intent);
            }
        });
        viewHolder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder Info = new AlertDialog.Builder(activity);
                Info.setTitle("Borrar Registro");
                Info.setIcon(R.drawable.advertencia_info);
                Info.setMessage("Esta a punto de borrar el registro: " + Nombre + " " + Apellidos + "\n¿Desea Continuar?");
                Info.setPositiveButton((Html.fromHtml("<font color='#000000'>CONTINUAR</font>")), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.collection("Padres").document(Aux_correo).collection("Alumnos").document(id).delete();

                        ReducirRegistros();
                        Mensaje_Info();
                        dialogInterface.cancel();
                    }
                }).setNeutralButton((Html.fromHtml("<font color='#000000'>CANCELAR</font>")), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        AlertDialog dialog = Info.create();
                dialog.show();
            }
        });
        viewHolder.btnActividades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, Criterios.class);
                intent.putExtra("Cuenta", id);
                intent.putExtra("Nombre", Nombre);
                activity.startActivity(intent);
            }
        });
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_datos, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtNombre;
        TextView txtApellidos;
        TextView txtEdad;
        Button btnEditar;
        Button btnEliminar;
        Button btnActividades;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.textView11);
            txtApellidos = itemView.findViewById(R.id.textView12);
            txtEdad = itemView.findViewById(R.id.textView13);
            btnEditar = itemView.findViewById(R.id.btnEditarRegistro);
            btnEliminar = itemView.findViewById(R.id.btnEliminarRegistro);
            btnActividades = itemView.findViewById(R.id.btnActividadesRegistro);

        }
    }

    public void ObtenerRegistros(){
        registrosdb.collection("Padres").document(Aux_correo).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && value.exists()) {
                    aux_registros = value.getString("Registros");
                    num_registros = Integer.parseInt(aux_registros);
                }
            }
        });
    }

    public void ReducirRegistros(){
        num_registros--;
        registrosdb.collection("Padres").document(Aux_correo).update("Registros", String.valueOf(num_registros)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }

    public void Mensaje_Info(){
        AlertDialog.Builder Info = new AlertDialog.Builder(activity);
        Info.setTitle("Operación Finalizada");
        Info.setIcon(R.drawable.icono_bote);
        Info.setMessage("El registro ha sido eliminado exitosamente");
        Info.setPositiveButton((Html.fromHtml("<font color='#000000'>ENTENDIDO</font>")), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = Info.create();
        dialog.show();
    }

}
