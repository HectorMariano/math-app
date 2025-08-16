package com.example.genio10;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;


import java.util.HashMap;
import java.util.Map;

public class Prueba extends AppCompatActivity {

    private EditText txtNombre, txtApellidos, txtEdad, txtNombreBuscar;
    String Nombre, Apellidos, Edad, Buscar, Aux_correo, aux_registros, aux_nombre = null, aux_identificador = null;
    int aux = 1;
    int validacion = 0;
    int num_registros = 0;
  //  SoundPool sp1, sp2;
    int sonido1, sonido2;
    RecyclerView Cuentas;
    Registros_Adpater registros_adpater;

    FirebaseFirestore db;
    FirebaseFirestore cuentasdb;

    int aux_salida = 0;
    FirebaseFirestore registrosdb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);

        txtNombre = findViewById(R.id.txtNombre);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtEdad = findViewById(R.id.txtEdad);
        txtNombreBuscar = findViewById(R.id.txtNombreBuscar);
        Cuentas = findViewById(R.id.Recicler_Cuentas);
        Cuentas.setLayoutManager(new LinearLayoutManager(this));


        /*sp1 = new SoundPool(1, AudioManager.STREAM_MUSIC,1);
        sonido1 = sp1.load(this, R.raw.sonido_correcto,1);
        sp2 = new SoundPool(5, AudioManager.STREAM_MUSIC, 1);
        sonido2 = sp2.load(this, R.raw.sonido_error, 1);*/

        db = FirebaseFirestore.getInstance();
        registrosdb = FirebaseFirestore.getInstance();
        cuentasdb = FirebaseFirestore.getInstance();

        SharedPreferences sharedPreferences = getSharedPreferences("ID", Context.MODE_PRIVATE);
        aux = sharedPreferences.getInt("aux", 01);

        SharedPreferences sharedPreferences2 = getSharedPreferences("Correo", Context.MODE_PRIVATE);
        Aux_correo = sharedPreferences2.getString("ID", "");

        Query query = cuentasdb.collection("Padres").document(Aux_correo).collection("Alumnos");

        FirestoreRecyclerOptions<Alumno> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Alumno>().setQuery(query, Alumno.class).build();
        registros_adpater = new Registros_Adpater(firestoreRecyclerOptions, this, Aux_correo);
        registros_adpater.notifyDataSetChanged();
        Cuentas.setAdapter(registros_adpater);

        validacion = getIntent().getIntExtra("Validación", 0);

        if(validacion == 1){
            Mensaje_Inicio();
        }
        //BuscarNum_Registros();
        registrosdb.collection("Padres").document(Aux_correo).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && value.exists()) {
                    aux_registros = value.getString("Registros");
                    //txtEdad.setText("Hay" + aux_registros);
                    num_registros = Integer.parseInt(aux_registros);
                    //txtNombre.setText("" + num_registros);
                }
            }
        });
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

            //sp2.play(sonido2,1,1,1,0,0);
            AlertDialog.Builder Info = new AlertDialog.Builder(this);
            Info.setTitle("Falta de Datos");
            Info.setIcon(R.drawable.advertencia_info);
            Info.setMessage("Ingrese los datos faltantes para continuar el registro");
            Info.setPositiveButton((Html.fromHtml("<font color='#000000'>OKAY</font>")), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog dialog = Info.create();
            dialog.show();
    }

    public void Mensaje_Confirmacion(){
        //sp1.play(sonido1,1,1,1,0,0);
        AlertDialog.Builder Info = new AlertDialog.Builder(this);
        Info.setTitle("Todo Listo");
        Info.setIcon(R.drawable.confirmacion);
        Info.setMessage("El registro se ha llevado a cabo exitosamente");
        Info.setPositiveButton((Html.fromHtml("<font color='#000000'>OKAY</font>")), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = Info.create();
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Limpiar(View view){
        txtNombre.setText("");
        txtApellidos.setText("");
        txtEdad.setText("");
        txtNombre.getFocusable();
    }

    public void Registro(View view){

       if(num_registros < 2) {

            Nombre = txtNombre.getText().toString();
            Apellidos = txtApellidos.getText().toString();
            Edad = txtEdad.getText().toString();

            if (Nombre.equals("") || Apellidos.equals("") || Edad.equals("")) {
                Mensaje_Error();
            } else {

                long date = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("EEE d MMM yyyy h:mm a");
                String Fecha = sdf.format(date);

                Map<String, Object> usuario = new HashMap<>();
                usuario.put("Nombre", Nombre);
                usuario.put("Apellidos", Apellidos);
                usuario.put("Edad", Edad);
                usuario.put("Fecha", Fecha);

                db.collection("Padres").document(Aux_correo).collection("Alumnos").document().set(usuario)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Mensaje_Confirmacion();
                                    aux_salida = 1;
                                } else {
                                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
               num_registros++;
                ActualizarNum_Registro();
            }
        }else {
            Mensaje_Registros();
        }
    }

    public void Buscar_Registro(View view){

        Buscar = txtNombreBuscar.getText().toString();

        if(Buscar.equals("")){
            txtNombreBuscar.setError("Campo Obligatorio");
        }

        else {
            db.collection("Usuarios").document(Buscar).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                    if (documentSnapshot.exists()) {
                        Nombre = documentSnapshot.getString("Nombre");
                        Apellidos = documentSnapshot.getString("Apellidos");
                        Edad = documentSnapshot.getString("Correo");

                        txtNombre.setText(Nombre);
                        txtApellidos.setText(Apellidos);
                        txtEdad.setText(Edad);

                        txtNombreBuscar.setText("");
                        Mensaje_Busqueda();
                    }
                }
            });
        }
    }

    public void Mensaje_Busqueda(){
        //sp1.play(sonido1,1,1,1,0,0);
        AlertDialog.Builder Info = new AlertDialog.Builder(this);
        Info.setTitle("Todo Listo");
        Info.setIcon(R.drawable.confirmacion);
        Info.setMessage("El registro se ha encontrado exitosamente");
        Info.setPositiveButton((Html.fromHtml("<font color='#000000'>OKAY</font>")), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = Info.create();
        dialog.show();
    }

    private void Actualizar_Registro(){
        Nombre = txtNombre.getText().toString();
        Apellidos = txtApellidos.getText().toString();
        Edad = txtEdad.getText().toString();

        Map<String, Object> usuario = new HashMap<>();
        usuario.put("Nombre", Nombre);
        usuario.put("Apellidos", Apellidos);
        usuario.put("Correo", Edad);

        db.collection("Usuarios").document(Nombre).set(usuario)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Mensaje_ConfirmacionActualizacion();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void Mensaje_ConfirmacionActualizacion(){
        //sp1.play(sonido1,1,1,1,0,0);
        AlertDialog.Builder Info = new AlertDialog.Builder(this);
        Info.setTitle("Todo Listo");
        Info.setIcon(R.drawable.confirmacion);
        Info.setMessage("El registro se ha actualizado exitosamente");
        Info.setPositiveButton((Html.fromHtml("<font color='#000000'>OKAY</font>")), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = Info.create();
        dialog.show();
    }

    public void Borrar_Registro(){
        Nombre = txtNombre.getText().toString();

        if(Nombre.equals("")){
            txtNombre.setError("Campo Obligatorio");
            //sp2.play(sonido2,1,1,1,0,0);

            AlertDialog.Builder Info = new AlertDialog.Builder(this);
            Info.setTitle("Falta de Datos");
            Info.setIcon(R.drawable.advertencia_info);
            Info.setMessage("Ingrese el nombre del registro a borrar");
            Info.setPositiveButton((Html.fromHtml("<font color='#000000'>OKAY</font>")), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog dialog = Info.create();
            dialog.show();

        }else{
            db.collection("Usuarios").document(Nombre).delete();
            //sp1.play(sonido1,1,1,1,0,0);

            AlertDialog.Builder Info = new AlertDialog.Builder(this);
            Info.setTitle("Todo Listo");
            Info.setIcon(R.drawable.confirmacion);
            Info.setMessage("El registro se ha eliminado exitosamente");
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

    public void ActualizarNum_Registro(){
        db.collection("Padres").document(Aux_correo).update("Registros", String.valueOf(num_registros)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }

    public void Mensaje_Inicio(){
        AlertDialog.Builder Info = new AlertDialog.Builder(this);
        Info.setTitle("Un paso más");
        Info.setIcon(R.drawable.icono_navegacion);
        Info.setMessage("Antes de poder usar la aplicación, debe registrar un alumno a su nombre. Por favor llene el siguiente formulario para continuar");
        Info.setPositiveButton((Html.fromHtml("<font color='#000000'>ENTENDIDO</font>")), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = Info.create();
        dialog.show();
    }

    public void Mensaje_Salida(){
        AlertDialog.Builder Info = new AlertDialog.Builder(this);
        Info.setTitle("Registro Inconcluso");
        Info.setIcon(R.drawable.advertencia_info);
        Info.setMessage("Por favor termine el registro de un alumno a su nombre antes de salir o continuar");
        Info.setPositiveButton((Html.fromHtml("<font color='#000000'>ENTENDIDO</font>")), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = Info.create();
        dialog.show();
    }

    public void Mensaje_Registros(){
        AlertDialog.Builder Info = new AlertDialog.Builder(this);
        Info.setTitle("Maximo de Registros Alcanzado");
        Info.setIcon(R.drawable.advertencia_info);
        Info.setMessage("Ha alcanzado el maximo de registros de que la aplicación permite a un usuario, elimine un registro existente para continuar");
        Info.setPositiveButton((Html.fromHtml("<font color='#000000'>ENTENDIDO</font>")), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = Info.create();
        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registros_adpater.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        registros_adpater.stopListening();
    }

    @Override
    public void onBackPressed() {
        if(validacion == 1 && aux_salida == 0){
            Mensaje_Salida();
        }
        else if(validacion == 1){
            Intent intent = new Intent(getApplicationContext(), activity_googleprueba.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(getApplicationContext(), activity_googleprueba.class);
            startActivity(intent);
        }
    }
}

