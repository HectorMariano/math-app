package com.example.genio10.ui.slideshow;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.genio10.R;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    SoundPool sp, sp2;
    private MediaPlayer mp;
    int sonido, sonido2;
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
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recompensas, container, false);
        final ImageButton logro01 = root.findViewById(R.id.logro01);
        final ImageButton logro02 = root.findViewById(R.id.logro02);
        final ImageButton logro03 = root.findViewById(R.id.logro03);
        final ImageButton logro04 = root.findViewById(R.id.logro04);
        final ImageButton logro05 = root.findViewById(R.id.logro05);
        final ImageButton logro06 = root.findViewById(R.id.logro06);
        final ImageButton logro07 = root.findViewById(R.id.logro07);

        if(ultimo1 == 0){
            logro01.setImageResource(R.drawable.icono_recompesas);
        }
        if(ultimo2 == 0){
            logro02.setImageResource(R.drawable.icono_recompesas);
        }
        if(ultimo3 == 0){
            logro03.setImageResource(R.drawable.icono_recompesas);
        }
        if(ultimo4 == 0){
            logro04.setImageResource(R.drawable.icono_recompesas);
        }
        if(ultimo5 == 0){
            logro05.setImageResource(R.drawable.icono_recompesas);
        }
        if(ultimo6 == 0){
            logro06.setImageResource(R.drawable.icono_recompesas);
        }

        if(ultimo1 != 0 && ultimo2 != 0 && ultimo3 != 0 && ultimo4 != 0 && ultimo5 != 0 && ultimo6 != 0){

        }else{
            logro07.setImageResource(R.drawable.icono_recompesas);
        }

        Toast.makeText(getContext(), "Hola", Toast.LENGTH_SHORT).show();
        sp = new SoundPool(5, AudioManager.STREAM_MUSIC,1);
        sonido = sp.load(getContext(), R.raw.sonido_recompensa,1);
        sp2 = new SoundPool(5, AudioManager.STREAM_MUSIC, 1);
        sonido2 = sp2.load(getContext(), R.raw.sonido_seccion, 1);

        mp = MediaPlayer.create(getContext(), R.raw.musica_fondo01);
        mp.setLooping(true);
        mp.start();

        logro01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ultimo1 != 0) {

                    AlertDialog.Builder Info = new AlertDialog.Builder(getActivity());
                    Info.setTitle("Trofeo Desbloqueado");
                    Info.setIcon(R.drawable.medalla);
                    Info.setMessage("¡Terminaste la Unidad 1! \nFelicidades por tu progreso");
                    sp.play(sonido, 1, 1, 1, 0, 0);
                    Info.setPositiveButton((Html.fromHtml("<font color='#000000'>OKAY</font>")), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog dialog = Info.create();
                    dialog.show();
                }else {
                    AlertDialog.Builder Info = new AlertDialog.Builder(getActivity());
                    Info.setTitle("Logro Secreto");
                    Info.setIcon(R.drawable.advertencia_info);
                    Info.setMessage("Sigue prácticando para conseguir este trofeo");
                    Info.setPositiveButton((Html.fromHtml("<font color='#000000'>Entendido</font>")), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog dialog = Info.create();
                    dialog.show();
                }

            }
        });

        logro02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ultimo2 != 0) {
                    AlertDialog.Builder Info = new AlertDialog.Builder(getActivity());
                    Info.setTitle("Trofeo Desbloqueado");
                    Info.setIcon(R.drawable.medalla);
                    Info.setMessage("¡Terminaste la Unidad 2! \nFelicidades por tu progreso");
                    sp.play(sonido, 1, 1, 1, 0, 0);
                    Info.setPositiveButton((Html.fromHtml("<font color='#000000'>OKAY</font>")), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog dialog = Info.create();
                    dialog.show();
                }else {
                    AlertDialog.Builder Info = new AlertDialog.Builder(getActivity());
                    Info.setTitle("Logro Secreto");
                    Info.setIcon(R.drawable.advertencia_info);
                    Info.setMessage("Sigue prácticando para conseguir este trofeo");
                    Info.setPositiveButton((Html.fromHtml("<font color='#000000'>Entendido</font>")), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog dialog = Info.create();
                    dialog.show();
                }
            }
        });

        logro03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ultimo3 != 0) {
                    AlertDialog.Builder Info = new AlertDialog.Builder(getActivity());
                    Info.setTitle("Trofeo Desbloqueado");
                    Info.setIcon(R.drawable.medalla);
                    Info.setMessage("¡Terminaste la Unidad 3! \nFelicidades por tu progreso");
                    sp.play(sonido, 1, 1, 1, 0, 0);
                    Info.setPositiveButton((Html.fromHtml("<font color='#000000'>OKAY</font>")), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog dialog = Info.create();
                    dialog.show();
                }else {
                    AlertDialog.Builder Info = new AlertDialog.Builder(getActivity());
                    Info.setTitle("Logro Secreto");
                    Info.setIcon(R.drawable.advertencia_info);
                    Info.setMessage("Sigue prácticando para conseguir este trofeo");
                    Info.setPositiveButton((Html.fromHtml("<font color='#000000'>Entendido</font>")), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog dialog = Info.create();
                    dialog.show();
                }
            }
        });

        logro04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ultimo4 != 0) {
                    AlertDialog.Builder Info = new AlertDialog.Builder(getActivity());
                    Info.setTitle("Trofeo Desbloqueado");
                    Info.setIcon(R.drawable.medalla);
                    Info.setMessage("¡Terminaste la Unidad 4! \nFelicidades por tu progreso");
                    sp.play(sonido, 1, 1, 1, 0, 0);
                    Info.setPositiveButton((Html.fromHtml("<font color='#000000'>OKAY</font>")), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog dialog = Info.create();
                    dialog.show();
                }else {
                    AlertDialog.Builder Info = new AlertDialog.Builder(getActivity());
                    Info.setTitle("Logro Secreto");
                    Info.setIcon(R.drawable.advertencia_info);
                    Info.setMessage("Sigue prácticando para conseguir este trofeo");
                    Info.setPositiveButton((Html.fromHtml("<font color='#000000'>Entendido</font>")), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog dialog = Info.create();
                    dialog.show();
                }
            }
        });

        logro05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ultimo5 != 0) {
                    AlertDialog.Builder Info = new AlertDialog.Builder(getActivity());
                    Info.setTitle("Trofeo Desbloqueado");;
                    Info.setIcon(R.drawable.medalla);
                    Info.setMessage("¡Terminaste la Unidad 5! \nFelicidades por tu progreso");
                    sp.play(sonido, 1, 1, 1, 0, 0);
                    Info.setPositiveButton((Html.fromHtml("<font color='#000000'>OKAY</font>")), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog dialog = Info.create();
                    dialog.show();
                }else {
                    AlertDialog.Builder Info = new AlertDialog.Builder(getActivity());
                    Info.setTitle("Logro Secreto");
                    Info.setIcon(R.drawable.advertencia_info);
                    Info.setMessage("Sigue prácticando para conseguir este trofeo");
                    Info.setPositiveButton((Html.fromHtml("<font color='#000000'>Entendido</font>")), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog dialog = Info.create();
                    dialog.show();
                }

            }
        });

        logro06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ultimo6 != 0) {
                    AlertDialog.Builder Info = new AlertDialog.Builder(getActivity());
                    Info.setTitle("Trofeo Desbloqueado");
                    Info.setIcon(R.drawable.medalla);
                    Info.setMessage("¡Terminaste la Unidad 6! \nFelicidades por tu progreso");
                    sp.play(sonido, 1, 1, 1, 0, 0);
                    Info.setPositiveButton((Html.fromHtml("<font color='#000000'>OKAY</font>")), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog dialog = Info.create();
                    dialog.show();
                }else{
                    AlertDialog.Builder Info = new AlertDialog.Builder(getActivity());
                    Info.setTitle("Logro Secreto");
                    Info.setIcon(R.drawable.advertencia_info);
                    Info.setMessage("Sigue prácticando para conseguir este trofeo");
                    Info.setPositiveButton((Html.fromHtml("<font color='#000000'>Entendido</font>")), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog dialog = Info.create();
                    dialog.show();
                }

            }
        });

        logro07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ultimo1 != 0 && ultimo2 != 0 && ultimo3 != 0 && ultimo4 != 0 && ultimo5 != 0 && ultimo6 != 0){
                    AlertDialog.Builder Info = new AlertDialog.Builder(getActivity());
                    Info.setTitle("Maestro de la Matemáticas");
                    Info.setIcon(R.drawable.medalla);
                    Info.setMessage("¡Conseguiste terminar todas las unidades de la aplicación! \n ¡Ahora eres todo un experto en los temas basicos de matemáticas!");
                    sp.play(sonido, 1, 1, 1, 0, 0);
                    Info.setPositiveButton((Html.fromHtml("<font color='#000000'>OKAY</font>")), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog dialog = Info.create();
                    dialog.show();
                }else {
                    AlertDialog.Builder Info = new AlertDialog.Builder(getActivity());
                    Info.setTitle("Logro Secreto");
                    Info.setIcon(R.drawable.advertencia_info);
                    Info.setMessage("Sigue prácticando para conseguir este trofeo");
                    Info.setPositiveButton((Html.fromHtml("<font color='#000000'>Entendido</font>")), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog dialog = Info.create();
                    dialog.show();
                }

            }
        });

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