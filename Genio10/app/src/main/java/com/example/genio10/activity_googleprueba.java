package com.example.genio10;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.GoogleApiAvailabilityCache;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class activity_googleprueba extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private ImageView Cuenta;
    private TextView txtNombre, txtCorreo, txtIdentificador;
    private Button btnContinuar, btnRegresar;
    int aux = 0;

    String Nombre, Correo, Identificador;
    String Aux_correo;
    String registros = "0";

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private GoogleApiClient googleApiClient;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googleprueba);

        txtNombre = findViewById(R.id.txtNombreCorrreo);
        txtCorreo = findViewById(R.id.txtCorrreoGoogle);
        txtIdentificador = findViewById(R.id.txtCodigo);
        Cuenta  = findViewById(R.id.imgCuenta);
        btnContinuar = findViewById(R.id.button11);
        btnRegresar = findViewById(R.id.button7);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    Info_Usuario(user);
                }else{
                    Intent intent = new Intent(getApplicationContext(), activity_menu.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        };

        db = FirebaseFirestore.getInstance();

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("Padres").document(txtCorreo.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            Aux_correo = txtCorreo.getText().toString();

                            SharedPreferences preferences = getSharedPreferences("Correo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("ID", Aux_correo);
                            editor.commit();

                            Mensaje_InfoInicio();

                        }else{
                            Mensaje_Confirmacion();
                        }
                    }
                });
            }
        });
    }

   /* //Método para mostrar los botones
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_navadmin, menu);
        return true;
    }

    //Método acción botones
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        Confimacion_Cuenta();

        if(id == R.id.Inicio){
            if(aux == 1) {
                Intent intent = new Intent(this, activity_googleprueba.class);
                startActivity(intent);
            }
            return true;
        }
        if(id == R.id.Progreso_1){
            if(aux == 1) {
                Intent intent = new Intent(this, Progreso_admin.class);
                startActivity(intent);
            }
            return true;
        }
        if(id == R.id.Admin){
            if(aux == 1) {
                Intent intent = new Intent(this, Prueba.class);
                startActivity(intent);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    private void Info_Usuario(FirebaseUser user) {
        txtNombre.setText(user.getDisplayName());
        txtCorreo.setText(user.getEmail());
        txtIdentificador.setText(user.getUid());

        Glide.with(this).load(user.getPhotoUrl()).into(Cuenta);
    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void Datos(){
        Nombre = txtNombre.getText().toString();
        Correo = txtCorreo.getText().toString();
        Identificador = txtIdentificador.getText().toString();
    }

    public void Salir(){
        firebaseAuth.signOut();
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    Intent intent = new Intent(getApplicationContext(), activity_menu.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "No se pudo cerrar sesión", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void Revocar(View view){
        AlertDialog.Builder Info = new AlertDialog.Builder(this);
        Info.setTitle("Cerrar Sesión");
        Info.setIcon(R.drawable.advertencia_info);
        Info.setMessage("Usted esta a punto de salir de su sesión y volver al menú de inicio ¿Desea continuar?");
        Info.setPositiveButton((Html.fromHtml("<font color='#000000'>CONTINUAR</font>")), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                firebaseAuth.signOut();
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if(status.isSuccess()){
                            Intent intent = new Intent(getApplicationContext(), activity_menu.class);
                            intent.putExtra("Registro", 1);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else {
                            Toast.makeText(getApplicationContext(), "No se pudo revocar acceso", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                dialogInterface.dismiss();
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

    @Override
    protected void onStop() {
        super.onStop();

        if(firebaseAuthListener!=null){
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

    private void Registro(){

        Nombre = txtNombre.getText().toString();
        Correo = txtCorreo.getText().toString();
        Identificador = txtIdentificador.getText().toString();

        Aux_correo = txtCorreo.getText().toString();

        SharedPreferences preferences = getSharedPreferences("Correo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ID", Aux_correo);
        editor.commit();

        Map<String, Object> usuario = new HashMap<>();
        usuario.put("Nombre", Nombre);
        usuario.put("Identificador", Identificador);
        usuario.put("Correo", Correo);
        usuario.put("Registros", registros);

        db.collection("Padres").document(Correo).set(usuario)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Mensaje_Listo();
                        } else{
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void Mensaje_Confirmacion(){
        AlertDialog.Builder Info = new AlertDialog.Builder(this);
        Info.setTitle("Casi Listo");
        Info.setCancelable(false);
        Info.setIcon(R.drawable.confirmacion);
        Info.setMessage("Los datos obtenidos se registrarán en la aplicación");
        Info.setPositiveButton((Html.fromHtml("<font color='#000000'>CONTINUAR</font>")), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                Registro();
            }
        });
        Info.setNeutralButton((Html.fromHtml("<font color='#000000'>REGRESAR</font>")), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = Info.create();
        dialog.show();
    }

    public void Mensaje_Listo(){
        AlertDialog.Builder Info = new AlertDialog.Builder(this);
        Info.setTitle("Todo Listo");
        Info.setCancelable(false);
        Info.setIcon(R.drawable.confirmacion);
        Info.setMessage("El registro se ha completado exitosamente");
        Info.setPositiveButton((Html.fromHtml("<font color='#000000'>ENTENDIDO</font>")), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                Intent intent = new Intent(getApplicationContext(), Prueba.class);
                intent.putExtra("Validación", 1);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        AlertDialog dialog = Info.create();
        dialog.show();
    }


    public void Confimacion_Cuenta(){
        db.collection("Padres").document(txtCorreo.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    aux = 1;
                }else{
                    Mensaje_Info();
                }
            }
        });
    }

    public void Mensaje_Info(){
        AlertDialog.Builder Info = new AlertDialog.Builder(this);
        Info.setTitle("Acceso Restringido");
        Info.setCancelable(false);
        Info.setIcon(R.drawable.advertencia_info);
        Info.setMessage("Complete el registro antes de poder acceder a estas opciones");
        Info.setPositiveButton((Html.fromHtml("<font color='#000000'>ENTENDIDO</font>")), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = Info.create();
        dialog.show();
    }

    public void Mensaje_InfoInicio(){
        AlertDialog.Builder Info = new AlertDialog.Builder(this);
        Info.setTitle("Todo listo");
        Info.setCancelable(false);
        Info.setIcon(R.drawable.confirmacion);
        Info.setMessage("Los registros a su nombre han sido recuperados exitosamente");
        Info.setPositiveButton((Html.fromHtml("<font color='#000000'>ENTENDIDO</font>")), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getApplicationContext(), Prueba.class);
                startActivity(intent);
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = Info.create();
        dialog.show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        firebaseAuth.signOut();
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    Intent intent = new Intent(getApplicationContext(), activity_menu.class);
                    intent.putExtra("Registro", 1);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "No se pudo revocar acceso", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}