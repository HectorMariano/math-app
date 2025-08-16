package com.example.genio10;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class explicacion_conceptos extends AppCompatActivity {
    int posicion;

    private ImageView img1, img2;
    private TextView txt, txt1, txt2;

    String [] titulo = new String[6];
    String [] info = new String[6];
    String [] info2 = new String[6];
    String image1 [] = new String[6];
    String image2 [] = new String[6];

    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explicacion_conceptos);

        mp = MediaPlayer.create(this, R.raw.musica_fondo01);
        mp.setLooping(true);
        mp.start();

        Bundle datos = this.getIntent().getExtras();
        posicion =  datos.getInt("Posicion");
        Toast.makeText(this, ""+ posicion, Toast.LENGTH_SHORT).show();

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        txt = findViewById(R.id.txtitulo);
        txt1 = findViewById(R.id.txtdescripcion);
        txt2 = findViewById(R.id.txtdescripcion2);

        titulo[0] = "Explicación: " + "Conceptos de Matemáticas";
        titulo[2] = "Explicación: " + "Restas";
        titulo[4] = "Explicación: " + "Sumas Avanzadas";
        titulo[1] = "Explicación: " + "Sumas";
        titulo[3] = "Explicación: " + "Conceptos de Matemáticas 2";
        titulo[5] = "Explicación: " + "Restas Avanzadas";

        info[0] = "La unidad es el elemento entero más pequeño que se puede contar";
        info[2] = "La resta no es más que quitar una cierta cantidad  a otra que ya teniamos";
        info[4] = "Para las sumas con decenas, primero se tienen que separar las unidades de las decenas";
        info[1] = "La suma es juntar 2 o más cosas, ya sean iguales o no, para saber cuántas hay en total";
        info[3] = "La decena es una medida que permite agrupar 10 unidades en una sola colección";
        info[5] = "Para la resta con decenas debemos separas las cantidades en unidades y decenas";

        info2[0] = "Para facilitar su identificación, las podemos representar con \n una figura.";
        info2[2] = "El símbolo que representa que vamos a hacer una resta es el siguiente";
        info2[4] = "Después, se realiza una suma sobre cada grupo de números para al final unirlos en un solo número";
        info2[1] = "Podemos saber que estamos por realizar una suma si vemos este símbolo";
        info2[3] = "La podemos identificar en números de 2 o más dígitos, pues simpre estan a la izquierda de las unidades";
        info2[5] = "A continuación debemos realizar una resta sobre cada grupo de números para conseguir el resultado final";

        image1[0] = "unidades_02";
        image1[2] = "resta";
        image1[4] = "suma_02";
        image1[1] = "unidades_03";
        image1[3] = "decena";
        image1[5] = "img_resta";

        image2[0] = "unidades_01";
        image2[2] = "img_resta";
        image2[4] = "decenas";
        image2[1] = "suma";
        image2[3] = "num_10";
        image2[5] = "resta_02";

        Info();
        Imagenes();
    }


    public void Info(){
        txt.setText(titulo[posicion]);
        txt1.setText(info[posicion]);
        txt2.setText(info2[posicion]);
    }

    public void Imagenes(){
        int id = getResources().getIdentifier(image1[posicion], "drawable", getPackageName());
        int id2 = getResources().getIdentifier(image2[posicion], "drawable", getPackageName());

        img1.setImageResource(id);
        img2.setImageResource(id2);
    }

    public void Atras(View view){
        Mensaje_Salida();
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
    public void onResume() {
        super.onResume();
        mp.start();
    }
    @Override
    public void onPause() {
        super.onPause();
        mp.pause();
    }

    @Override
    public void onBackPressed() {
        Mensaje_Salida();
    }
}