package com.example.genio10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class actualizacion_registro extends AppCompatActivity {

    String Aux_correo;

    String Nombre, Apellidos, Edad;
    String aux_id, aux_nombre, aux_apellidos, aux_edad, aux_fecha;

    private EditText txtNombre, txtApellidos, txtEdad;
    private TextView txtNombre2, txtApellidos2, txtEdad2;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizacion_registro);

        SharedPreferences sharedPreferences = getSharedPreferences("Correo", Context.MODE_PRIVATE);
        Aux_correo = sharedPreferences.getString("ID", "");

        aux_id = getIntent().getStringExtra("Cuenta");
        aux_nombre = getIntent().getStringExtra("Nombre");
        aux_apellidos = getIntent().getStringExtra("Apellidos");
        aux_edad = getIntent().getStringExtra("Edad");
        aux_fecha = getIntent().getStringExtra("Fecha");

        txtNombre = findViewById(R.id.txtNombreActualizado);
        txtApellidos = findViewById(R.id.txtApellidosActualizados);
        txtEdad = findViewById(R.id.txtEdadActualizado);
        txtNombre2 = findViewById(R.id.txtNombreAnterior);
        txtApellidos2 = findViewById(R.id.txtApellidosAnterior);
        txtEdad2 = findViewById(R.id.txtEdadAnterior);

        txtEdad2.setText("Edad: " + aux_edad);
        txtApellidos2.setText("Apellidos: " + aux_apellidos);
        txtNombre2.setText("Nombre: " + aux_nombre);

        db = FirebaseFirestore.getInstance();

    }

    public void Actualizar_Registro(View view) {

        Nombre = txtNombre.getText().toString();
        Apellidos = txtApellidos.getText().toString();
        Edad = txtEdad.getText().toString();

        if (Nombre.equals("") || Apellidos.equals("") || Edad.equals("")) {
            Mensaje_Error();
        } else {

            db.collection("Padres").document(Aux_correo).collection("Alumnos").document(aux_id).update("Nombre", Nombre,
                    "Apellidos", Apellidos, "Edad", Edad).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Mensaje_ConfirmacionActualizacion();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void Regresar (View view){
        Intent intent = new Intent(getApplicationContext(), Prueba.class);
        startActivity(intent);
    }

    public void Mensaje_ConfirmacionActualizacion(){
        AlertDialog.Builder Info = new AlertDialog.Builder(this);
        Info.setTitle("Todo Listo");
        Info.setCancelable(false);
        Info.setIcon(R.drawable.confirmacion);
        Info.setMessage("El registro se ha actualizado exitosamente. Regresando a la pantalla de registro");
        Info.setPositiveButton((Html.fromHtml("<font color='#000000'>ENTENDIDO</font>")), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getApplicationContext(), Prueba.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = Info.create();
        dialog.show();
    }

    public void Mensaje_Error(){
        Nombre = txtNombre.getText().toString();
        Apellidos = txtApellidos.getText().toString();
        Edad = txtEdad.getText().toString();

        if(Nombre.equals("")){
            txtNombre.setError("Campo Obligatorio");
        }
        if(Apellidos.equals("")){
            txtApellidos.setError("Campo Obligatorio");
        }
        if(Edad.equals("")){
            txtEdad.setError("Campo Obligatorio");
        }

        AlertDialog.Builder Info = new AlertDialog.Builder(this);
        Info.setTitle("Falta de Datos");
        Info.setIcon(R.drawable.advertencia_info);
        Info.setMessage("Ingrese los datos faltantes para continuar");
        Info.setPositiveButton((Html.fromHtml("<font color='#000000'>OKAY</font>")), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = Info.create();
        dialog.show();
    }

}