package com.example.genio10;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class activity_menu extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;
    public static final int Code = 111;

    int validacion = 0;
    String Aux_correo;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private ProgressBar progreso;
    private Button btnInicio;

    RecyclerView cuentas;
    Cuentas_Adapter cuentas_adapter;

    FirebaseFirestore db;

    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

            db = FirebaseFirestore.getInstance();

        SharedPreferences sharedPreferences2 = getSharedPreferences("Correo", Context.MODE_PRIVATE);
        Aux_correo = sharedPreferences2.getString("ID", "");

        validacion = getIntent().getIntExtra("Registro", 0);

        cuentas = findViewById(R.id.Recycler_Inicio);
        cuentas.setLayoutManager(new LinearLayoutManager(this));

        progreso = findViewById(R.id.progreso);
        btnInicio = findViewById(R.id.button10);

        GoogleSignInOptions googleSignInOptions  =new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions).build();

        firebaseAuth = FirebaseAuth.getInstance();

        if(validacion == 0) {
            firebaseAuth.signOut();
        }


        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                   Mensaje_Listo();
                }
            }
        };

        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Inicio_sesion();
            }
        });


        if(!Aux_correo.equals("")) {

            Query query = db.collection("Padres").document(Aux_correo).collection("Alumnos");

            FirestoreRecyclerOptions<Cuenta> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Cuenta>().setQuery(query, Cuenta.class).build();
            cuentas_adapter = new Cuentas_Adapter(firestoreRecyclerOptions, this, Aux_correo);
            cuentas_adapter.notifyDataSetChanged();
            cuentas.setAdapter(cuentas_adapter);
        }
        if(Aux_correo.equals("")){
            Mensaje_Inicio();
        }


        mp = MediaPlayer.create(this, R.raw.musica_fondo04);
        mp.setLooping(true);
        mp.start();
    }

    public void Inicio_sesion(){
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, Code);
    }


    private void Resultado(GoogleSignInResult result) {
        if(result.isSuccess()){
           // Mensaje_Listo();
            Atenticacion_Firebase(result.getSignInAccount());
        }else{
            Mensaje_Error();
        }
    }

    private void Atenticacion_Firebase(GoogleSignInAccount signInAccount) {
        progreso.setVisibility(View.VISIBLE);
        btnInicio.setVisibility(View.INVISIBLE);

        AuthCredential credential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progreso.setVisibility(View.INVISIBLE);
                btnInicio.setVisibility(View.VISIBLE);

                if(!task.isSuccessful()){
                    Mensaje_Error();
                }
            }
        });
    }

    public void Mensaje_Listo(){

        AlertDialog.Builder Info = new AlertDialog.Builder(this);
        Info.setTitle("Todo Listo");
        Info.setCancelable(false);
        Info.setIcon(R.drawable.confirmacion);
        Info.setMessage("Se ha comprobado su información de manera exitosa");
        Info.setPositiveButton((Html.fromHtml("<font color='#000000'>ENTENDIDO</font>")), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                Intent principal = new Intent(getApplicationContext(), activity_googleprueba.class);
                principal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(principal);
            }
        });
        AlertDialog dialog = Info.create();
        dialog.show();
    }

    public void Mensaje_Error(){

        AlertDialog.Builder Info = new AlertDialog.Builder(this);
        Info.setTitle("Error en Registro");
        Info.setIcon(R.drawable.advertencia_info);
        Info.setMessage("Se produjo un error en el registro. Verifique su conexión a internet");
        Info.setPositiveButton((Html.fromHtml("<font color='#000000'>ENTENDIDO</font>")), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        Info.setNeutralButton((Html.fromHtml("<font color='#000000'>REINTENTAR</font>")), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                Inicio_sesion();
            }
        });
        AlertDialog dialog = Info.create();
        dialog.show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
        if(!Aux_correo.equals("")) {
            cuentas_adapter.startListening();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Code){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Resultado(result);
        }

    }

    public void Mensaje_Inicio(){

        AlertDialog.Builder Info = new AlertDialog.Builder(this);
        Info.setTitle("Bienvenido a Genio Matemático");
        Info.setIcon(R.drawable.libros);
        Info.setMessage("Siendo esta su primera vez debe iniciar sesión con su cuenta de Google haciendo click en el boton que indica 'Iniciar Sesión' para continuar");
        Info.setPositiveButton((Html.fromHtml("<font color='#000000'>ENTENDIDO</font>")), new DialogInterface.OnClickListener() {
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
        if(!Aux_correo.equals("")) {
            cuentas_adapter.stopListening();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mp.start();
    }
    @Override
    public void onPause() {
        super.onPause();
        mp.pause();
    }
}