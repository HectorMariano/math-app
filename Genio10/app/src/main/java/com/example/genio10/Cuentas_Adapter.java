package com.example.genio10;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Cuentas_Adapter extends FirestoreRecyclerAdapter<Cuenta, Cuentas_Adapter.ViewHolder> {

    Activity activity;
    FirebaseFirestore db;
    String Aux_correo;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Cuentas_Adapter(@NonNull FirestoreRecyclerOptions<Cuenta> options, Activity activity, String Correo) {
        super(options);
        this.activity = activity;
        db = FirebaseFirestore.getInstance();
        this.Aux_correo = Correo;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Cuenta cuenta) {

        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(viewHolder.getAdapterPosition());
        final String id = documentSnapshot.getId();

        viewHolder.txtCuenta.setText(cuenta.getNombre());
        viewHolder.btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, MainActivity.class);
                intent.putExtra("Nombre_Cuenta", id);
                intent.putExtra("Inicio", 1);
                activity.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cuentas, parent , false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtCuenta;
        Button btnIngresar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCuenta  = itemView.findViewById(R.id.txtCuenta);
            btnIngresar = itemView.findViewById(R.id.btnAcceder);
        }
    }

}


