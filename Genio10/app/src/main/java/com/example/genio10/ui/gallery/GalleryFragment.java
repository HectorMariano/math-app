package com.example.genio10.ui.gallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.genio10.R;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private MediaPlayer mp;
    SoundPool sp, sp2;
    int sonido, sonido2;
    String mensaje;
    int aux;

    int ultimo1 = 0, ultimo2 = 0, ultimo3 = 0, ultimo4 = 0, ultimo5 = 0, ultimo6 = 0;
    int mejor1 = 0, mejor2 = 0, mejor3 = 0, mejor4 = 0, mejor5 = 0, mejor6 = 0;
    int menor1 = 0, menor2 = 0, menor3 = 0, menor4 = 0, menor5 = 0, menor6 = 0;

    private String cuenta;

    @Override
    public void onAttach(Context context) {

        String [] titulo_resultado = new String[6];

        titulo_resultado[0] = "Conceptos de Matemáticas";
        titulo_resultado[1] = "Sumas";
        titulo_resultado[2] = "Restas";
        titulo_resultado[3] = "Conceptos de Matemáticas 2";
        titulo_resultado[4] = "Sumas Avanzadas";
        titulo_resultado[5] = "Restas Avanzadas";

        SharedPreferences Preferences = context.getSharedPreferences("Nombre_Cuenta", Context.MODE_PRIVATE);
        cuenta = Preferences.getString("Nombre_Cuenta", "");

        String nom1, nom2, nom3, nom4, nom5, nom6;

        nom1 = titulo_resultado[0]+"_"+cuenta;
        nom2 = titulo_resultado[1]+"_"+cuenta;
        nom3 = titulo_resultado[2]+"_"+cuenta;
        nom4 = titulo_resultado[3]+"_"+cuenta;
        nom5 = titulo_resultado[4]+"_"+cuenta;
        nom6 = titulo_resultado[5]+"_"+cuenta;

        SharedPreferences sharedPreferences0 = context.getSharedPreferences(nom1, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor0 = sharedPreferences0.edit();
        ultimo1 = sharedPreferences0.getInt("Ultimo_Intento", 0);
        mejor1 = sharedPreferences0.getInt("Mejor_Intento", 0);
        menor1 = sharedPreferences0.getInt("Menor_Puntuacion", 0);

        if (ultimo1 >= mejor1) {
            mejor1 = ultimo1;
        }
        if (ultimo1 <= menor1) {
            menor1 = ultimo1;
        }
        if (menor1 == 0) {
            menor1 = ultimo1;
        }

        editor0.putInt("Menor_Puntuacion", menor1);
        editor0.putInt("Mejor_Intento", mejor1);
        editor0.apply();

        SharedPreferences sharedPreferences1 = context.getSharedPreferences(nom2, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
        ultimo2 = sharedPreferences1.getInt("Ultimo_Intento", 0);
        mejor2 = sharedPreferences1.getInt("Mejor_Intento", 0);
        menor2 = sharedPreferences1.getInt("Menor_Puntuacion", 0);

        if (ultimo2 >= mejor2) {
            mejor2 = ultimo2;
        }
        if (ultimo2 <= menor2) {
            menor2 = ultimo2;
        }
        if (menor2 == 0) {
            menor2 = ultimo2;
        }

        editor1.putInt("Menor_Puntuacion", menor2);
        editor1.putInt("Mejor_Intento", mejor2);
        editor1.apply();

        SharedPreferences sharedPreferences2 = context.getSharedPreferences(nom3, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        ultimo3 = sharedPreferences2.getInt("Ultimo_Intento", 0);
        mejor3 = sharedPreferences2.getInt("Mejor_Intento", 0);
        menor3 = sharedPreferences2.getInt("Menor_Puntuacion", 0);

        if(ultimo3 >= mejor3){
            mejor3 = ultimo3;
        }   if(ultimo3 <= menor3){
            menor3 = ultimo3;
        }   if(menor3 == 0){
            menor3 = ultimo3;
        }

        editor2.putInt("Menor_Puntuacion", menor3);
        editor2.putInt("Mejor_Intento", mejor3);
        editor2.apply();

        SharedPreferences sharedPreferences3 = context.getSharedPreferences(nom4, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor3 = sharedPreferences3.edit();
        ultimo4 = sharedPreferences3.getInt("Ultimo_Intento", 0);
        mejor4 = sharedPreferences3.getInt("Mejor_Intento", 0);
        menor4 = sharedPreferences3.getInt("Menor_Puntuacion", 0);

        if (ultimo4 >= mejor4) {
            mejor4 = ultimo4;
        }
        if (ultimo4 <= menor4) {
            menor4 = ultimo4;
        }
        if (menor4 == 0) {
            menor4 = ultimo4;
        }

        editor3.putInt("Menor_Puntuacion", menor4);
        editor3.putInt("Mejor_Intento", mejor4);
        editor3.apply();

        SharedPreferences sharedPreferences4 = context.getSharedPreferences(nom5, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor4 = sharedPreferences4.edit();
        ultimo5 = sharedPreferences4.getInt("Ultimo_Intento", 0);
        mejor5 = sharedPreferences4.getInt("Mejor_Intento", 0);
        menor5 = sharedPreferences4.getInt("Menor_Puntuacion", 0);

        if (ultimo5 >= mejor5) {
            mejor5 = ultimo5;
        }
        if (ultimo5 <= menor5) {
            menor5 = ultimo5;
        }
        if (menor5 == 0) {
            menor5 = ultimo5;
        }

        editor4.putInt("Menor_Puntuacion", menor5);
        editor4.putInt("Mejor_Intento", mejor5);
        editor4.apply();

        SharedPreferences sharedPreferences5 = context.getSharedPreferences(nom6, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor5 = sharedPreferences5.edit();
        ultimo6 = sharedPreferences5.getInt("Ultimo_Intento", 0);
        mejor6 = sharedPreferences5.getInt("Mejor_Intento", 0);
        menor6 = sharedPreferences5.getInt("Menor_Puntuacion", 0);

        if(ultimo6 >= mejor6){
            mejor6 = ultimo6;
        }
        if(ultimo6 <= menor6){
            menor6 = ultimo6;
        }
        if(menor6 == 0){
            menor6 = ultimo6;
        }

        editor5.putInt("Menor_Puntuacion", menor6);
        editor5.putInt("Mejor_Intento", mejor6);
        editor5.apply();

        super.onAttach(context);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_progreso, container, false);

        /*SharedPreferences sharedPreferences = this.requireActivity().getSharedPreferences("Conceptos de Matemáticas" + cuenta, Context.MODE_PRIVATE);
        ultimo1 = sharedPreferences.getInt("Ultimo_Intento", 0);
        mejor1 = sharedPreferences.getInt("Mejor_Intento", 0);
        menor1 = sharedPreferences.getInt("Menor_Puntuacion", 0);*/

        Toast.makeText(getContext(), cuenta, Toast.LENGTH_LONG).show();
        /*SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("Hola", Context.MODE_PRIVATE);
        mensaje = sharedPreferences.getString("Hola", "");*/

        /*if(ultimo1 >= mejor1){
            mejor1 = ultimo1;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("Mejor_Intento", mejor1);
            editor.apply();
        }   if(ultimo1 <= menor1){
            menor1 = ultimo1;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("Menor_Intento", menor1);
            editor.apply();
        }


        SharedPreferences sharedPreferences2 =  this.getActivity().getSharedPreferences(titulo_resultado[1] + "_" + cuenta, Context.MODE_PRIVATE);
        ultimo2 = sharedPreferences2.getInt("Ultimo_Intento", 0);
        mejor2 = sharedPreferences2.getInt("Mejor_Intento", 0);
        menor2 = sharedPreferences2.getInt("Menor_Puntuacion", 0);


       SharedPreferences sharedPreferences3 = this.getActivity().getSharedPreferences("Restas", Context.MODE_PRIVATE);
        ultimo3 = sharedPreferences3.getInt("Ultimo_Intento", 0);
        mejor3 = sharedPreferences3.getInt("Mejor_Intento", 0);
        menor3 = sharedPreferences3.getInt("Menor_Puntuacion", 0);*/


        final Button btn1 = root.findViewById(R.id.btnpagina1);
        final Button btn2 = root.findViewById(R.id.btnpagina2);
        final Button btn3 = root.findViewById(R.id.btnpagina3);

        final TextView txttitulo = root.findViewById(R.id.txtnombre_unidad);
        final TextView txttitulo2 = root.findViewById(R.id.txtnombre_unidad2);

        final TextView txtultimo1 = root.findViewById(R.id.txtultimo1);
        final TextView txtultimo2 = root.findViewById(R.id.txtultimo2);
        final TextView txtmejor1 = root.findViewById(R.id.txtmejor1);
        final TextView txtmejor2 = root.findViewById(R.id.txtmejor2);
        final TextView txtmenor1 = root.findViewById(R.id.txtmenor1);
        final TextView txtmenor2 = root.findViewById(R.id.txtmenor2);

        txttitulo.setText("Conceptos de Matemáticas");
        txttitulo2.setText("Sumas");

        sp = new SoundPool(1, AudioManager.STREAM_MUSIC,1);
        sonido = sp.load(getContext(), R.raw.sonido_pagina,1);
        sp2 = new SoundPool(5, AudioManager.STREAM_MUSIC, 1);
        sonido2 = sp2.load(getContext(), R.raw.sonido_seccion, 1);

        mp = MediaPlayer.create(getContext(), R.raw.musica_fondo02);
        mp.setLooping(true);
        mp.start();



            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sp.play(sonido,1,1,1,0,0);
                    txttitulo.setText("Conceptos de Matemáticas");
                    txttitulo2.setText("Sumas");
                    Toast.makeText(getContext(), "1", Toast.LENGTH_SHORT).show();

                    txtultimo1.setText(String.valueOf(ultimo1));
                    txtmejor1.setText(String.valueOf(mejor1));
                    txtmenor1.setText(String.valueOf(menor1));

                    txtultimo2.setText(String.valueOf(ultimo2));
                    txtmejor2.setText(String.valueOf(mejor2));
                    txtmenor2.setText(String.valueOf(menor2));

                }
            });

            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sp.play(sonido,1,1,1,0,0);
                    txttitulo.setText("Restas");
                    txttitulo2.setText("Conceptos de Matemáticas 2");
                    Toast.makeText(getContext(), "2", Toast.LENGTH_SHORT).show();

                    txtultimo1.setText(String.valueOf(ultimo3));
                    txtmejor1.setText(String.valueOf(mejor3));
                    txtmenor1.setText(String.valueOf(menor3));

                    txtultimo2.setText(String.valueOf(ultimo4));
                    txtmejor2.setText(String.valueOf(mejor4));
                    txtmenor2.setText(String.valueOf(menor4));

                }
            });

            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sp.play(sonido,1,1,1,0,0);
                    txttitulo.setText("Sumas Avanzadas");
                    txttitulo2.setText("Restas Avanzadas");
                    Toast.makeText(getContext(), "3", Toast.LENGTH_SHORT).show();

                    txtultimo1.setText(String.valueOf(ultimo5));
                    txtmejor1.setText(String.valueOf(mejor5));
                    txtmenor1.setText(String.valueOf(menor5));

                    txtultimo2.setText(String.valueOf(ultimo6));
                    txtmejor2.setText(String.valueOf(mejor6));
                    txtmenor2.setText(String.valueOf(menor6));

                }
            });

        txtultimo1.setText("" + ultimo1);
        txtmejor1.setText(""+ mejor1);
        txtmenor1.setText(""+ menor1);

        txtultimo2.setText("" + ultimo2);
        txtmejor2.setText(""+ mejor2);
        txtmenor2.setText(""+ menor2);



        return root;
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