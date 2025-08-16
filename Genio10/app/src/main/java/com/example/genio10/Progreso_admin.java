package com.example.genio10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Progreso_admin extends AppCompatActivity {

    RecyclerView actividades;
    TextView txtTitulo;

    String nombre, Aux_correo, id;
    Actividades_adapter adapter;
    FirebaseFirestore firestore;


    int criterio, rango, valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progreso);

        String [] titulo_resultado = new String[6];

        actividades = findViewById(R.id.vista_actividades);
        txtTitulo = findViewById(R.id.txtNom_actividades);

        Query query = null;

        SharedPreferences sharedPreferences2 = getSharedPreferences("Correo", Context.MODE_PRIVATE);
        Aux_correo = sharedPreferences2.getString("ID", "");

        titulo_resultado[0] = "Conceptos de Matemáticas";
        titulo_resultado[1] = "Sumas";
        titulo_resultado[2] = "Restas";
        titulo_resultado[3] = "Conceptos de Matemáticas 2";
        titulo_resultado[4] = "Sumas Avanzadas";
        titulo_resultado[5] = "Restas Avanzadas";

        id = getIntent().getStringExtra("Cuenta");
        nombre = getIntent().getStringExtra("Nombre");
        criterio = getIntent().getIntExtra("Criterio", 0);
        valor = getIntent().getIntExtra("Valor", 0);
        rango = getIntent().getIntExtra("Rango", 0);

        firestore = FirebaseFirestore.getInstance();

        actividades.setLayoutManager(new LinearLayoutManager(this));

        if(criterio == 0) {
            if(rango == 0) {
                query = firestore.collection("Padres").document(Aux_correo).collection("Alumnos").document(id).
                        collection("Actividades").whereGreaterThanOrEqualTo("Aciertos", valor);
            }else{
                query = firestore.collection("Padres").document(Aux_correo).collection("Alumnos").document(id).
                        collection("Actividades").whereLessThanOrEqualTo("Aciertos", valor);
            }
        }
        else if(criterio == 1){
            if(rango == 0) {
                query = firestore.collection("Padres").document(Aux_correo).collection("Alumnos").document(id).
                        collection("Actividades").whereGreaterThanOrEqualTo("Fallos", valor);
            }else{
                query = firestore.collection("Padres").document(Aux_correo).collection("Alumnos").document(id).
                        collection("Actividades").whereLessThanOrEqualTo("Fallos", valor);
            }
        }
        else if(criterio == 2){
            query = firestore.collection("Padres").document(Aux_correo).collection("Alumnos").document(id).
                    collection("Actividades").whereEqualTo("Unidad", titulo_resultado[valor]);
        }
        else{
            query = firestore.collection("Padres").document(Aux_correo).collection("Alumnos").document(id).
                    collection("Actividades");
        }

        FirestoreRecyclerOptions<Actividad> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Actividad>().setQuery(query, Actividad.class).build();
        adapter = new Actividades_adapter(firestoreRecyclerOptions);
        adapter.notifyDataSetChanged();
        actividades.setAdapter(adapter);
        txtTitulo.setText(txtTitulo.getText() + "\n" + nombre);
    }

    public void Regresar(View view){
        Intent intent = new Intent(this, Prueba.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}