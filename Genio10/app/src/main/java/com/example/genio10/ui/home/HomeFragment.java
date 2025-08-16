package com.example.genio10.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.genio10.Prueba;
import com.example.genio10.R;
import com.example.genio10.ejemplo_conceptos;
import com.example.genio10.ejercicio_conceptos;
import com.example.genio10.explicacion_conceptos;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    int aux = 0, sonido, sonido2;
    private MediaPlayer mp;
    SoundPool sp, sp2;

    int [] validacion = new int[6];
    String [] titulo_resultado = new String[6];

    String cuenta;
    int ultimo1, ultimo2, ultimo3, ultimo4, ultimo5, ultimo6;


    @Override
    public void onAttach(@NonNull Context context) {
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
        ultimo1 = sharedPreferences0.getInt("Ultimo_Intento", 0);
        SharedPreferences sharedPreferences1 = context.getSharedPreferences(nom2, Context.MODE_PRIVATE);
        ultimo2 = sharedPreferences1.getInt("Ultimo_Intento", 0);
        SharedPreferences sharedPreferences2 = context.getSharedPreferences(nom3, Context.MODE_PRIVATE);
        ultimo3 = sharedPreferences2.getInt("Ultimo_Intento", 0);
        SharedPreferences sharedPreferences3 = context.getSharedPreferences(nom4, Context.MODE_PRIVATE);
        ultimo4 = sharedPreferences3.getInt("Ultimo_Intento", 0);
        SharedPreferences sharedPreferences4 = context.getSharedPreferences(nom5, Context.MODE_PRIVATE);
        ultimo5 = sharedPreferences4.getInt("Ultimo_Intento", 0);
        SharedPreferences sharedPreferences5 = context.getSharedPreferences(nom6, Context.MODE_PRIVATE);
        ultimo6 = sharedPreferences5.getInt("Ultimo_Intento", 0);

        super.onAttach(context);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        titulo_resultado[0] = "Conceptos de Matemáticas";
        titulo_resultado[1] = "Restas";
        titulo_resultado[2] = "Sumas Avanzadas";
        titulo_resultado[3] = "Sumas";
        titulo_resultado[4] = "Conceptos de Matemáticas 2";
        titulo_resultado[5] = "Restas Avanzadas";

        final ImageButton btnsiguiente = root.findViewById(R.id.btnSiguiente);
        final ImageButton btnanterior = root.findViewById(R.id.btnAnterior);
        final ImageButton btnejercicio = root.findViewById(R.id.btnejercicios);
        final ImageButton btnejemplos = root.findViewById(R.id.btnejemplos);
        final ImageButton btnexplicacion = root.findViewById(R.id.btnexplicacion);
        final Button btnPrueba = root.findViewById(R.id.button6);

        final ImageView uni1 = root.findViewById(R.id.Unidad_1);

        sp = new SoundPool(1, AudioManager.STREAM_MUSIC,1);
        sonido = sp.load(getContext(), R.raw.sonido_pagina,1);
        sp2 = new SoundPool(5, AudioManager.STREAM_MUSIC, 1);
        sonido2 = sp2.load(getContext(), R.raw.sonido_seccion, 1);


        mp = MediaPlayer.create(getContext(), R.raw.musica_fondo02);
        mp.setLooping(true);
        mp.start();

        SharedPreferences Preferences = this.requireActivity().getSharedPreferences("Validacion_Unidad", Context.MODE_PRIVATE);
        validacion[0] = Preferences.getInt(titulo_resultado[0], 0);
        validacion[1] = Preferences.getInt(titulo_resultado[1], 0);
        validacion[2] = Preferences.getInt(titulo_resultado[2], 0);
        validacion[3] = Preferences.getInt(titulo_resultado[3], 0);
        validacion[4] = Preferences.getInt(titulo_resultado[4], 0);
        validacion[5] = Preferences.getInt(titulo_resultado[5], 0);

        //Toast.makeText(getContext(), "No se " + validacion[3], Toast.LENGTH_SHORT).show();

        btnsiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.play(sonido,3,3,1,0,0);
                ++aux;
                Toast.makeText(getContext(), ""+ aux, Toast.LENGTH_SHORT).show();
                switch (aux){
                    case 1:
                        uni1.setImageResource(R.drawable.unidad_2);
                        btnanterior.setVisibility(View.VISIBLE);
                    break;
                    case 2:
                        uni1.setImageResource(R.drawable.unidad_3);
                    break;
                    case 3:
                        uni1.setImageResource(R.drawable.unidad_4);
                    break;
                    case 4:
                        uni1.setImageResource(R.drawable.unidad_5);
                    break;
                    case 5:
                        uni1.setImageResource(R.drawable.unidad_6);
                        btnsiguiente.setVisibility(View.INVISIBLE);
                    break;
                }
            }
        });
        btnanterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.play(sonido,1,1,1,0,0);
                aux--;
                Toast.makeText(getContext(), ""+ aux, Toast.LENGTH_SHORT).show();
                switch (aux){
                    case 4:
                        uni1.setImageResource(R.drawable.unidad_5);
                        btnsiguiente.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        uni1.setImageResource(R.drawable.unidad_4);
                        break;
                    case 2:
                        uni1.setImageResource(R.drawable.unidad_3);
                        break;
                    case 1:
                        uni1.setImageResource(R.drawable.unidad_2);
                        break;
                    case 0:
                        uni1.setImageResource(R.drawable.unidad_1);
                        btnanterior.setVisibility(View.INVISIBLE);
                    break;
                }
            }
        });


        btnejercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ultimo1 == 0 && aux == 1) {
                    Mensaje();
                } else if (ultimo2 == 0 && aux == 2) {
                    Mensaje();
                } else if (ultimo3 == 0 && aux == 3) {
                    Mensaje();
                } else if (ultimo4 == 0 && aux == 4) {
                    Mensaje();
                } else if (ultimo5 == 0 && aux == 5) {
                    Mensaje();
                } else {
                    try {
                        Intent ejercicio = new Intent(getContext(), ejercicio_conceptos.class);
                        ejercicio.putExtra("Posicion", aux);
                        startActivity(ejercicio);
                    } catch (Exception e) {
                        Mensaje_Error();
                    }
                }
            }
            });
        btnejemplos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ultimo1 == 0 && aux == 1) {
                    Mensaje();
                } else if (ultimo2 == 0 && aux == 2) {
                    Mensaje();
                } else if (ultimo3 == 0 && aux == 3) {
                    Mensaje();
                } else if (ultimo4 == 0 && aux == 4) {
                    Mensaje();
                } else if (ultimo5 == 0 && aux == 5) {
                    Mensaje();
                } else {
                    try {
                        Intent ejemplo = new Intent(getContext(), ejemplo_conceptos.class);
                        ejemplo.putExtra("Posicion", aux);
                        startActivity(ejemplo);
                    } catch (Exception e) {
                        Mensaje_Error();
                    }
                }
            }
        });
        btnexplicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ultimo1 == 0 && aux == 1) {
                    Mensaje();
                } else if (ultimo2 == 0 && aux == 2) {
                    Mensaje();
                } else if (ultimo3 == 0 && aux == 3) {
                    Mensaje();
                } else if (ultimo4 == 0 && aux == 4) {
                    Mensaje();
                } else if (ultimo5 == 0 && aux == 5) {
                    Mensaje();
                } else {
                    try {
                        Intent explicacion = new Intent(getContext(), explicacion_conceptos.class);
                        explicacion.putExtra("Posicion", aux);
                        startActivity(explicacion);
                    } catch (Exception e) {
                        Mensaje_Error();
                    }
                }
            }
        });


        btnPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent siguiente = new Intent(getContext(), Prueba.class);
                startActivity(siguiente);
            }
        });

        return root;
    }

    public void Mensaje_Error(){
        AlertDialog.Builder Info = new AlertDialog.Builder(getContext());
        Info.setTitle("Error al ingresar");
        Info.setIcon(R.drawable.advertencia_info);
        Info.setMessage("Ups, ocurrió un error al entrar a esta sección, intentalo de nuevo o vuelve más tarde");
        Info.setPositiveButton((Html.fromHtml("<font color='#000000'>Entendido</font>")), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = Info.create();
        dialog.show();
    }

    public void Mensaje(){
        AlertDialog.Builder Info = new AlertDialog.Builder(getContext());
        Info.setTitle("Acceso Denegado");
        Info.setIcon(R.drawable.advertencia_info);
        Info.setMessage("Esta unidad solo es accesible terminando la anterior unidad");
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