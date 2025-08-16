package com.example.genio10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class ejemplo_conceptos extends AppCompatActivity {
    String [] titulo = new String[6];
    String[] lista = new String [6];
    private TextView txttitulo;
    private VideoView video;
    int posicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejemplo_conceptos);
        Bundle datos = this.getIntent().getExtras();
        posicion =  datos.getInt("Posicion");
        Toast.makeText(this, ""+ posicion, Toast.LENGTH_SHORT).show();
        txttitulo = findViewById(R.id.txtitulo3);
        video = findViewById(R.id.video1);


        titulo[0] = "Ejemplos: " + "Conceptos de Matemáticas";
        titulo[1] = "Ejemplos: " + "Restas";
        titulo[2] = "Ejemplos: " + "Sumas Avanzadas";
        titulo[3] = "Ejemplos: " + "Sumas";
        titulo[4] = "Ejemplos: " + "Conceptos de Matemáticas 2";
        titulo[5] = "Ejemplos: " + "Restas Avanzadas";

        lista[0] = "android.resource://" + getPackageName() + "/" + R.raw.video1;
        lista[1] = "android.resource://" + getPackageName() + "/" + R.raw.video2;
        lista[2] = "android.resource://" + getPackageName() + "/" + R.raw.video3;
        lista[3] = "android.resource://" + getPackageName() + "/" + R.raw.video4;
        lista[4] = "android.resource://" + getPackageName() + "/" + R.raw.video5;
        lista[5] = "android.resource://" + getPackageName() + "/" + R.raw.video6;

        Inicio();
    }

    public void Inicio(){
        txttitulo.setText(titulo[posicion]);
        MediaController menu = new MediaController(this);
        video.setMediaController(menu);
        menu.setAnchorView(video);
        video.setVideoURI(Uri.parse(lista[posicion]));
    }

    public void Regresar (View view){

    }

    public void Mensaje_Salida(){
        AlertDialog.Builder Info = new AlertDialog.Builder(this);
        Info.setTitle("Regresando al Menú");
        Info.setIcon(R.drawable.advertencia_info);
        Info.setMessage("Estas a punto de salir de esta sección de la app.\n¿Deseas Continuar?");
        Info.setPositiveButton((Html.fromHtml("<font color='#000000'>CONTINUAR</font>")), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent regresar = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(regresar);
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

    @Override
    public void onBackPressed() {
        Mensaje_Salida();
    }

}