package com.example.genio10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ejercicio_conceptos extends AppCompatActivity {

    private ImageView imgnum1, imgnum2, imgnum3,imgsigno, imgresultado, fondo, imgtitulo, imgsigno2, imgsigno3;
    private TextView txtnum1, txtnum2, txttitulo, txtresultado, txtejercicio, txtpregunta, txtpregunta2, txtdecenas, txtsuma1, txtsuma2;
    private EditText txtrespuesta, txtrespuesta2;
    private Button btnRevisar, btnSiguiente;

    String [] titulo = new String[6];
    private MediaPlayer mp;
    int posicion;

    SoundPool sp1, sp2;
    int sonido1, sonido2;

    int num1, num2, resultado, respuesta, respuesta2, aciertos = 0, aux_intentos = 0;
    int ejercicio = 0;
    int intentos = 2;
    int aux1, aux2;

    String rsp, rsp2;
    String imagen [] = {"num_00", "num_01", "num_02", "num_03", "num_04", "num_05", "num_06", "num_07", "num_08", "num_09"};
    String imagen2 [] = {"","concepto_1", "concepto_2", "concepto_3", "concepto_4", "concepto_5", "concepto_6", "concepto_7", "concepto_8", "concepto_9", "concepto_10"};
    String [] titulo_resultado = new String[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio_conceptos);

        Bundle datos = this.getIntent().getExtras();
        posicion =  datos.getInt("Posicion");

        mp = MediaPlayer.create(this, R.raw.musica_fondo03);
        mp.setLooping(true);
        mp.start();

        sp1 = new SoundPool(1, AudioManager.STREAM_MUSIC,1);
        sonido1 = sp1.load(this, R.raw.acierto,1);
        sp2 = new SoundPool(5, AudioManager.STREAM_MUSIC, 1);
        sonido2 = sp2.load(this, R.raw.error, 1);

        //Toast.makeText(this, ""+ posicion, Toast.LENGTH_SHORT).show();

        imgnum1 = findViewById(R.id.imgnum1);
        imgnum2 = findViewById(R.id.imgnum2);
        imgnum3 = findViewById(R.id.imgnum3);
        imgsigno = findViewById(R.id.imgsigno);
        imgresultado = findViewById(R.id.imgresultado);
        txtnum1 = findViewById(R.id.txtnum1);
        txtnum2 = findViewById(R.id.txtnum2);
        txttitulo = findViewById(R.id.txtitulo2);
        txtresultado = findViewById(R.id.txtresultado);
        txtrespuesta = findViewById(R.id.txtrespuesta);
        fondo = findViewById(R.id.fondo_respuesta);
        txtejercicio = findViewById(R.id.textView17);
        btnRevisar = findViewById(R.id.button3);
        btnSiguiente = findViewById(R.id.btnContinuar);
        txtpregunta = findViewById(R.id.txtnum35);
        txtpregunta2 = findViewById(R.id.txtnum36);
        txtdecenas = findViewById(R.id.textView5);
        txtrespuesta2 = findViewById(R.id.txtrespuesta02);
        imgtitulo = findViewById(R.id.imageView90);
        txtsuma1 = findViewById(R.id.textView90);
        txtsuma2 = findViewById(R.id.textView91);
        imgsigno2 = findViewById(R.id.imageView40);
        imgsigno3 = findViewById(R.id.imageView80);

        titulo[0] = "Ejercicios: \n" + "Conceptos de Matemáticas";
        titulo[1] = "Ejercicios: \n" + "Sumas";
        titulo[2] = "Ejercicios: \n" + "Restas";
        titulo[3] = "Ejercicios: \n" + "Conceptos de Matemáticas 2";
        titulo[4] = "Ejercicios: \n" + "Sumas Avanzadas";
        titulo[5] = "Ejercicios: \n" + "Restas Avanzadas";

        titulo_resultado[0] = "Conceptos de Matemáticas";
        titulo_resultado[1] = "Sumas";
        titulo_resultado[2] = "Restas";
        titulo_resultado[3] = "Conceptos de Matemáticas 2";
        titulo_resultado[4] = "Sumas Avanzadas";
        titulo_resultado[5] = "Restas Avanzadas";


        txttitulo.setText(titulo[posicion]);

        Mensaje_Inicio();

        if(posicion == 0){
            Unidad_1();
            txtpregunta2.setVisibility(View.VISIBLE);
            imgnum3.setVisibility(View.VISIBLE);
        }
        else if(posicion == 1){
            Unidad_2();
            imgsigno.setVisibility(View.VISIBLE);
            imgsigno.setImageResource(R.drawable.ic_agregar);
        }
        else if(posicion == 2){
            Unidad_3();
            imgsigno.setVisibility(View.VISIBLE);
            imgsigno.setImageResource(R.drawable.resta);
        }else if(posicion == 3){
            Unidad_4();
            txtpregunta.setVisibility(View.VISIBLE);
            txtpregunta.setText("¿Cuántas decenas y unidades hay?");
            txtdecenas.setVisibility(View.VISIBLE);
            txtrespuesta2.setVisibility(View.VISIBLE);
            txtrespuesta.setHint("Unidades");
            txtrespuesta2.setHint("Decenas");
        }
        else if(posicion == 4){
            Unidad_5();
            imgtitulo.setVisibility(View.VISIBLE);
            txtsuma1.setVisibility(View.VISIBLE);
            txtsuma2.setVisibility(View.VISIBLE);
            imgsigno2.setVisibility(View.VISIBLE);
            imgsigno3.setVisibility(View.VISIBLE);
        }
        else if(posicion == 5){
            Unidad_6();
            imgtitulo.setVisibility(View.VISIBLE);
            txtsuma1.setVisibility(View.VISIBLE);
            txtsuma2.setVisibility(View.VISIBLE);
            imgsigno2.setVisibility(View.VISIBLE);
            imgsigno2.setImageResource(R.drawable.resta);
            imgsigno3.setVisibility(View.VISIBLE);
        }


        btnSiguiente.setVisibility(View.INVISIBLE);

    }


    public void Revisar(View view){
        rsp = txtrespuesta.getText().toString();
        rsp2 = txtrespuesta2.getText().toString();

        if(rsp.equals("")){
            Mensaje_Error();
            txtrespuesta.setError("Campo Obligatorio");
        }
        else {
            if(posicion == 0){
                Revisar_Unidad1();
            }
            else if(posicion == 2){
                Revisar_Unidad3();
            }
            else if(posicion == 1){
                Revisar_Unidad2();
            }
            else if(posicion == 3){
                if(rsp2.equals("")) {
                    Mensaje_Error();
                    txtrespuesta2.setError("Campo Obligatorio");
                }else{
                    Revisar_Unidad4();
                }
            }
            else if(posicion == 4){
                Revisar_Unidad5();
            }
            else if(posicion == 5){
                Revisar_Unidad6();
            }
        }
    }

    public void Unidad_1() {
        intentos = 2;
        if (ejercicio == 10) {
            Final();
        } else {
            num1 = (int) (Math.random() * 10);
            if (num1 != 0) {

                for (int i = 0; i < imagen2.length; i++) {
                    int id = getResources().getIdentifier(imagen2[i], "drawable", getPackageName());
                    if (num1 == i) {
                        imgnum3.setImageResource(id);
                    }
                }
            } else{
              Unidad_1();
            }
        }
    }

    public void Revisar_Unidad1(){
        txtrespuesta.setText("");
        respuesta = Integer.parseInt(rsp);

        if(respuesta == num1){

            sp1.play(1,1,1,1,0,0);
            ejercicio++;
            txtresultado.setVisibility(View.VISIBLE);
            imgresultado.setVisibility(View.VISIBLE);
            fondo.setVisibility(View.VISIBLE);
            txtejercicio.setText("Ejercicio \n" + ejercicio + " / 10");

            txtresultado.setText("¡CORRECTO! \n La respuesta era: " + num1);
            imgresultado.setImageResource(R.drawable.confirmacion);
            fondo.setBackgroundResource(R.color.bien);

            aciertos++;
            //Unidad_2();
            btnRevisar.setVisibility(View.INVISIBLE);
            btnSiguiente.setVisibility(View.VISIBLE);
            txtrespuesta.setEnabled(false);
        }
        else{
            aux_intentos++;
            intentos--;
            if(intentos != 0){
                Mensaje_Intento();
            }
            if(intentos == 0) {

                sp2.play(1,1,1,1,0,0);
                ejercicio++;
                txtresultado.setVisibility(View.VISIBLE);
                imgresultado.setVisibility(View.VISIBLE);
                fondo.setVisibility(View.VISIBLE);
                txtejercicio.setText("Ejercicio \n" + ejercicio + " / 10");

                txtresultado.setText("Rayos... \n La respuesta era: " + num1);
                imgresultado.setImageResource(R.drawable.advertencia_info);
                fondo.setBackgroundResource(R.color.mal);
                //Unidad_2();
                btnRevisar.setVisibility(View.INVISIBLE);
                btnSiguiente.setVisibility(View.VISIBLE);
                txtrespuesta.setEnabled(false);
            }
        }
    }


    public void Unidad_2(){
        intentos = 2;   

        if(ejercicio == 10){
            Final();
        }
        else {

            num1 = (int) (Math.random() * 10);
            num2 = (int) (Math.random() * 10);
            resultado = num1 + num2;

            if (resultado <= 10) {

                txtnum1.setText("" + num1);
                txtnum2.setText("" + num2);

                for (int i = 0; i < imagen.length; i++) {
                    int id = getResources().getIdentifier(imagen[i], "drawable", getPackageName());
                    if (num1 == i) {
                        imgnum1.setImageResource(id);
                    }
                    if (num2 == i) {
                        imgnum2.setImageResource(id);
                    }
                }

            } else {
                Unidad_2();
            }
        }
    }

    public void Revisar_Unidad2(){
        txtrespuesta.setText("");
        respuesta = Integer.parseInt(rsp);

        if(respuesta == resultado){

            sp1.play(1,1,1,1,0,0);
            ejercicio++;
            txtresultado.setVisibility(View.VISIBLE);
            imgresultado.setVisibility(View.VISIBLE);
            fondo.setVisibility(View.VISIBLE);
            txtejercicio.setText("Ejercicio \n" + ejercicio + " / 10");

            txtresultado.setText("¡CORRECTO! \n La respuesta era: " + resultado);
            imgresultado.setImageResource(R.drawable.confirmacion);
            fondo.setBackgroundResource(R.color.bien);

            aciertos++;
            //Unidad_2();
            btnRevisar.setVisibility(View.INVISIBLE);
            btnSiguiente.setVisibility(View.VISIBLE);
            txtrespuesta.setEnabled(false);
        }
        else{
            aux_intentos++;
            intentos--;
            if(intentos != 0){
                Mensaje_Intento();
            }
            if(intentos == 0) {

                sp2.play(1,1,1,1,0,0);
                ejercicio++;
                txtresultado.setVisibility(View.VISIBLE);
                imgresultado.setVisibility(View.VISIBLE);
                fondo.setVisibility(View.VISIBLE);
                txtejercicio.setText("Ejercicio \n" + ejercicio + " / 10");

                txtresultado.setText("Rayos... \n La respuesta era: " + resultado);
                imgresultado.setImageResource(R.drawable.advertencia_info);
                fondo.setBackgroundResource(R.color.mal);
                //Unidad_2();
                btnRevisar.setVisibility(View.INVISIBLE);
                btnSiguiente.setVisibility(View.VISIBLE);
                txtrespuesta.setEnabled(false);
            }
        }
    }


    public void Unidad_3(){
        intentos = 2;
        if(ejercicio == 10){
            Final();
        }
        else {
            num1 = (int) (Math.random() * 10);
            num2 = (int) (Math.random() * 10);
            resultado = num1 - num2;

            if (num2 <= num1) {

                txtnum1.setText("" + num1);
                txtnum2.setText("" + num2);

                for (int i = 0; i < imagen.length; i++) {
                    int id = getResources().getIdentifier(imagen[i], "drawable", getPackageName());
                    if (num1 == i) {
                        imgnum1.setImageResource(id);
                    }
                    if (num2 == i) {
                        imgnum2.setImageResource(id);
                    }
                }

            } else {
                Unidad_3();
            }
        }
    }

    public void Revisar_Unidad3(){
        txtrespuesta.setText("");
        respuesta = Integer.parseInt(rsp);

        if(respuesta == resultado){

            sp1.play(1,1,1,1,0,0);
            ejercicio++;
            txtresultado.setVisibility(View.VISIBLE);
            imgresultado.setVisibility(View.VISIBLE);
            fondo.setVisibility(View.VISIBLE);
            txtejercicio.setText("Ejercicio \n" + ejercicio + " / 10");

            txtresultado.setText("¡CORRECTO! \n La respuesta era: " + resultado);
            imgresultado.setImageResource(R.drawable.confirmacion);
            fondo.setBackgroundResource(R.color.bien);

            aciertos++;
            //Unidad_3();
            btnRevisar.setVisibility(View.INVISIBLE);
            btnSiguiente.setVisibility(View.VISIBLE);
            txtrespuesta.setEnabled(false);
        }
        else{
            aux_intentos++;
            intentos--;
            if(intentos != 0){
                Mensaje_Intento();
            }
            if(intentos == 0) {

                sp2.play(1,1,1,1,0,0);
                ejercicio++;
                txtresultado.setVisibility(View.VISIBLE);
                imgresultado.setVisibility(View.VISIBLE);
                fondo.setVisibility(View.VISIBLE);
                txtejercicio.setText("Ejercicio \n" + ejercicio + " / 10");

                txtresultado.setText("Rayos... \n La respuesta era: " + resultado);
                imgresultado.setImageResource(R.drawable.advertencia_info);
                fondo.setBackgroundResource(R.color.mal);
                //Unidad_3();
                btnRevisar.setVisibility(View.INVISIBLE);
                btnSiguiente.setVisibility(View.VISIBLE);
                txtrespuesta.setEnabled(false);
            }
        }
    }


    public void Unidad_4(){
        intentos = 2;
        if (ejercicio == 10) {
            Final();
        } else {
            num1 = 1 + (int) (Math.random()*8);
            num2 = (int) (Math.random()* 9);
            txtdecenas.setText(num1 + "" + num2);
            String auxdecenas = txtdecenas.getText().toString();
            if(num1 == num2){
                Unidad_4();
            }
        }
    }

    public void Revisar_Unidad4(){
        txtrespuesta.setText("");
        txtrespuesta2.setText("");
        txtresultado.setTextSize(18);

        respuesta = Integer.parseInt(rsp);
        respuesta2 = Integer.parseInt(rsp2);

        if(respuesta2 == num1 && respuesta == num2){

            sp1.play(1,1,1,1,0,0);
            ejercicio++;
            txtresultado.setVisibility(View.VISIBLE);
            imgresultado.setVisibility(View.VISIBLE);
            fondo.setVisibility(View.VISIBLE);
            txtejercicio.setText("Ejercicio \n" + ejercicio + " / 10");

            txtresultado.setText("¡CORRECTO! \n Las decenas eran: " + num1 + "\n Las unidades eran: "  + num2);
            imgresultado.setImageResource(R.drawable.confirmacion);
            fondo.setBackgroundResource(R.color.bien);

            aciertos++;
            btnRevisar.setVisibility(View.INVISIBLE);
            btnSiguiente.setVisibility(View.VISIBLE);
            txtrespuesta.setEnabled(false);
            txtrespuesta2.setEnabled(false);
        }
        else{
            aux_intentos++;
            intentos--;
            if(intentos != 0){
                Mensaje_Intento();
            }
            if(intentos == 0) {

                sp2.play(1,1,1,1,0,0);
                ejercicio++;
                txtresultado.setVisibility(View.VISIBLE);
                imgresultado.setVisibility(View.VISIBLE);
                fondo.setVisibility(View.VISIBLE);
                txtejercicio.setText("Ejercicio \n" + ejercicio + " / 10");

                txtresultado.setText("Rayos... \n Las decenas eran: " + num1 + "\n Las unidades eran: " + num2);
                imgresultado.setImageResource(R.drawable.advertencia_info);
                fondo.setBackgroundResource(R.color.mal);
                btnRevisar.setVisibility(View.INVISIBLE);
                btnSiguiente.setVisibility(View.VISIBLE);
                txtrespuesta.setEnabled(false);
                txtrespuesta2.setEnabled(false);
            }
        }
    }


    public void Unidad_5(){
        intentos = 2;
        if (ejercicio == 10) {
            Final();
        } else {

            num1 = 10 + (int)(Math.random()*20);
            num2 = 10 + (int)(Math.random()*15);
            resultado = num1 + num2;
            int auxnum1, auxnum2, auxaux1;

            auxnum1 = num1%10;
            auxnum2 = num2%10;
            auxaux1 = auxnum1 + auxnum2;

            if(auxnum1 > auxnum2 && resultado < 50 && auxaux1 < 10){
                txtsuma1.setText(""+num1);
                txtsuma2.setText(""+num2);
            }else{
                Unidad_5();
            }
        }
    }

    public void Revisar_Unidad5(){
        txtrespuesta.setText("");
        respuesta = Integer.parseInt(rsp);

        if(respuesta == resultado){

            sp1.play(1,1,1,1,0,0);
            ejercicio++;
            txtresultado.setVisibility(View.VISIBLE);
            imgresultado.setVisibility(View.VISIBLE);
            fondo.setVisibility(View.VISIBLE);
            txtejercicio.setText("Ejercicio \n" + ejercicio + " / 10");

            txtresultado.setText("¡CORRECTO! \n La respuesta era: " + resultado);
            imgresultado.setImageResource(R.drawable.confirmacion);
            fondo.setBackgroundResource(R.color.bien);

            aciertos++;
            btnRevisar.setVisibility(View.INVISIBLE);
            btnSiguiente.setVisibility(View.VISIBLE);
            txtrespuesta.setEnabled(false);
        }
        else{
            aux_intentos++;
            intentos--;
            if(intentos != 0){
                Mensaje_Intento();
            }
            if(intentos == 0) {

                sp2.play(1,1,1,1,0,0);
                ejercicio++;
                txtresultado.setVisibility(View.VISIBLE);
                imgresultado.setVisibility(View.VISIBLE);
                fondo.setVisibility(View.VISIBLE);
                txtejercicio.setText("Ejercicio \n" + ejercicio + " / 10");

                txtresultado.setText("Rayos... \n La respuesta era: " + resultado);
                imgresultado.setImageResource(R.drawable.advertencia_info);
                fondo.setBackgroundResource(R.color.mal);
                btnRevisar.setVisibility(View.INVISIBLE);
                btnSiguiente.setVisibility(View.VISIBLE);
                txtrespuesta.setEnabled(false);
            }
        }
    }

    public void Unidad_6(){
        intentos = 2;
        if (ejercicio == 10) {
            Final();
        } else {

            num1 = 10 + (int)(Math.random()*31);
            num2 = 10 + (int)(Math.random()*21);
            resultado = num1 - num2;
            int auxnum1, auxnum2, auxaux1, auxnum3, auxnum4, num11, num12;

            num11 = num1;
            num12 = num2;
            auxnum1 = num1%10;
            auxnum2 = num2%10;
            auxaux1 = auxnum1 + auxnum2;
            num1 = num1/10;
            num2 = num2/10;

            auxnum3 = num1%10;
            auxnum4 = num2%10;

            if(auxnum2 <= auxnum1 && num1 > num2 && auxaux1 < 10 && auxnum4 <= auxnum3){
                txtsuma1.setText(""+num11);
                txtsuma2.setText(""+num12);
            }else{
                Unidad_6();
            }
        }
    }

    public void Revisar_Unidad6(){
        txtrespuesta.setText("");
        respuesta = Integer.parseInt(rsp);

        if(respuesta == resultado){

            sp1.play(1,1,1,1,0,0);
            ejercicio++;
            txtresultado.setVisibility(View.VISIBLE);
            imgresultado.setVisibility(View.VISIBLE);
            fondo.setVisibility(View.VISIBLE);
            txtejercicio.setText("Ejercicio \n" + ejercicio + " / 10");

            txtresultado.setText("¡CORRECTO! \n La respuesta era: " + resultado);
            imgresultado.setImageResource(R.drawable.confirmacion);
            fondo.setBackgroundResource(R.color.bien);

            aciertos++;
            btnRevisar.setVisibility(View.INVISIBLE);
            btnSiguiente.setVisibility(View.VISIBLE);
            txtrespuesta.setEnabled(false);
        }
        else{
            aux_intentos++;
            intentos--;
            if(intentos != 0){
                Mensaje_Intento();
            }
            if(intentos == 0) {

                sp2.play(1,1,1,1,0,0);
                ejercicio++;
                txtresultado.setVisibility(View.VISIBLE);
                imgresultado.setVisibility(View.VISIBLE);
                fondo.setVisibility(View.VISIBLE);
                txtejercicio.setText("Ejercicio \n" + ejercicio + " / 10");

                txtresultado.setText("Rayos... \n La respuesta era: " + resultado);
                imgresultado.setImageResource(R.drawable.advertencia_info);
                fondo.setBackgroundResource(R.color.mal);
                btnRevisar.setVisibility(View.INVISIBLE);
                btnSiguiente.setVisibility(View.VISIBLE);
                txtrespuesta.setEnabled(false);
            }
        }
    }

    public void Siguiente(View view){
        txtrespuesta.setEnabled(true);
        txtrespuesta2.setEnabled(true);
        txtresultado.setVisibility(View.INVISIBLE);
        imgresultado.setVisibility(View.INVISIBLE);
        fondo.setVisibility(View.INVISIBLE);

        if(posicion == 0){
            Unidad_1();
        }
        else if(posicion == 1){
            Unidad_2();
        }
        else if(posicion == 2){
            Unidad_3();
        }
        else if(posicion == 3){
            Unidad_4();
        }
        else if(posicion == 4){
            Unidad_5();
        }
        else if(posicion == 5){
            Unidad_6();
        }

        btnRevisar.setVisibility(View.VISIBLE);
        btnSiguiente.setVisibility(View.INVISIBLE);
    }



    public void Final(){
        Mensaje_Fin();
    }

    public void Mensaje_Inicio(){
        AlertDialog.Builder Info = new AlertDialog.Builder(this);
        Info.setTitle("Prueba tus habilidades");
        Info.setIcon(R.drawable.icono_navegacion);
        Info.setMessage("En esta sección pondras a prueba tus conocimientos al resolver diversos ejercicios dependiendo del tema seleccionado. " +
                "Eso si, tendrás que terminar todos los ejercicios para guardar tu progreso");
        Info.setPositiveButton((Html.fromHtml("<font color='#000000'>CONTINUAR</font>")), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = Info.create();
        dialog.show();
    }

    public void Mensaje_Fin(){
        AlertDialog.Builder Info = new AlertDialog.Builder(this);
        Info.setCancelable(false);
        Info.setTitle("Fin de Actividad");
        Info.setIcon(R.drawable.libros);
        Info.setMessage("Felicidades, has terminado todos los ejercicios de la actividad");
        Info.setPositiveButton((Html.fromHtml("<font color='#000000'>CONTINUAR</font>")), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent( getApplicationContext(), resultados.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Aciertos", aciertos);
                intent.putExtra("Unidad", titulo_resultado[posicion]);
                intent.putExtra("Ejercicios", ejercicio);
                intent.putExtra("Intentos", aux_intentos);
                startActivity(intent);


                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = Info.create();
        dialog.show();
    }

    public void Mensaje_Salida(){
        AlertDialog.Builder Info = new AlertDialog.Builder(this);
        Info.setTitle("Espera un momento");
        Info.setCancelable(false);
        Info.setIcon(R.drawable.advertencia_info);
        Info.setMessage("Si sales de la actividad sin terminar todos los ejercicios, perderas tu progreso actual" +
                "\n¿Deseas Continuar?");
        Info.setPositiveButton((Html.fromHtml("<font color='#000000'>CONTINUAR</font>")), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                dialogInterface.cancel();
            }
        });
        Info.setNeutralButton((Html.fromHtml("<font color='#000000'>CANCELAR</font>")), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = Info.create();
        dialog.show();
    }

    public void Mensaje_Error(){
        AlertDialog.Builder Info = new AlertDialog.Builder(this);
        Info.setTitle("Falta de Respuesta");
        Info.setIcon(R.drawable.advertencia_info);
        Info.setMessage("Para poder revisar el ejercicio ingresa un número en el campo solicitado");
        Info.setPositiveButton((Html.fromHtml("<font color='#000000'>ENTENDIDO</font>")), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = Info.create();
        dialog.show();
    }

    public void Mensaje_Intento(){
        AlertDialog.Builder Info = new AlertDialog.Builder(this);
        Info.setTitle("Intentalo de Nuevo");
        Info.setIcon(R.drawable.icono_navegacion);
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
    @Override
    public void onBackPressed() {
        Mensaje_Salida();
    }
}
