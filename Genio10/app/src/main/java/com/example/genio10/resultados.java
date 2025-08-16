package com.example.genio10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class resultados extends AppCompatActivity {

    private final static String ID_Canal = "NOTIFICACION";
    private final static int ID_Notificacion = 0;

    int  aciertos, intentos, ejercicios, aux;
    String unidad, Aux_correo, cuenta;

    String puntaje;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);


        SharedPreferences sharedPreferences = getSharedPreferences("Nombre_Cuenta", Context.MODE_PRIVATE);
        cuenta = sharedPreferences.getString("Nombre_Cuenta", "");

        SharedPreferences sharedPreferences2 = getSharedPreferences("Correo", Context.MODE_PRIVATE);
        Aux_correo = sharedPreferences2.getString("ID", "");

        //Toast.makeText(this, ""+ Aux_correo, Toast.LENGTH_SHORT).show();
        db = FirebaseFirestore.getInstance();

        Bundle datos = this.getIntent().getExtras();
        unidad = datos.getString("Unidad");
        aciertos = datos.getInt("Aciertos", 0);
        ejercicios = datos.getInt("Ejercicios", 0);
        intentos = datos.getInt("Intentos", 0);

        TextView txtaciertos = findViewById(R.id.txtaciertos);
        TextView txtintentos = findViewById(R.id.txtintentos);
        TextView txtejercicios = findViewById(R.id.txtejercicios);
        TextView txtunidad = findViewById(R.id.txtunidades);
        TextView txtresultados = findViewById(R.id.txtresultados);
        ImageView img = findViewById(R.id.img_resultados);
        ImageView img2 = findViewById(R.id.imageView7);

        img2.setImageResource(R.drawable.resultados);

        puntaje = unidad + "_" + cuenta;
        //Toast.makeText(this, "" + puntaje, Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences10 = getSharedPreferences(puntaje, Context.MODE_PRIVATE);
        if(sharedPreferences10.getInt("Ultimo_Intento", 0) == 0){
            aux = 1;
        }

        txtaciertos.setText("Aciertos: " + aciertos);
        txtejercicios.setText("Ejercicios: " + ejercicios);
        txtintentos.setText("Fallos : " + intentos);
        txtunidad.setText("Unidad: " + unidad);

        if(aciertos == 10){
            txtresultados.setText("PERFECTO \n Sigue así");
            img.setImageResource(R.drawable.estrella);
        } else if(aciertos > 7){
            txtresultados.setText("Muy bien \n Sigue así");
            img.setImageResource(R.drawable.medalla);
        } else if(aciertos >= 5){
            txtresultados.setText("No esta mal \n Pero puedes mejorar");
            img.setImageResource(R.drawable.icono_navegacion);
        } else{
            txtresultados.setTextSize(24);
            txtresultados.setText("Sigue practicando");
            img.setImageResource(R.drawable.libros);
        }


        SharedPreferences preferences3 = getSharedPreferences(puntaje, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences3.edit();
        editor.putInt("Ultimo_Intento", aciertos);
        editor.apply();

        //Toast.makeText(this, aciertos, Toast.LENGTH_LONG).show();
        RegistroActividad();
        CrearCanal();
        CrearNotificacion();
    }

    private void RegistroActividad(){

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE d MMM yyyy h:mm a");
        String Fecha = sdf.format(date);

        Map<String, Object> usuario = new HashMap<>();
        usuario.put("Aciertos", aciertos);
        usuario.put("Num_Ejercicios", String.valueOf(ejercicios));
        usuario.put("Fallos", intentos);
        usuario.put("Unidad", unidad);
        usuario.put("Fecha", Fecha);

        db.collection("Padres").document(Aux_correo).collection("Alumnos").document(cuenta).collection("Actividades").document(Fecha).set(usuario)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //Toast.makeText(getApplicationContext(), "Listo", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void Regresar(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Notificacion", 1);
        intent.putExtra("Unidad", unidad);
        intent.putExtra("IF", aux);
        startActivity(intent);
    }

    private void CrearCanal() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notificacion";
            NotificationChannel notificationChannel = new NotificationChannel(ID_Canal, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void CrearNotificacion() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), ID_Canal);
        builder.setSmallIcon(R.drawable.confirmacion);
        builder.setContentTitle("Actividad Finalizada");
        builder.setStyle(new NotificationCompat.BigTextStyle()
        .bigText("Felicidades por terminar todos los ejercicios de la unidad: " + unidad +
                "\nSe creo exitosamente un registro de la actividad"));
        builder.setColor(Color.CYAN);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        // builder.setContentIntent(pendingIntent);
        builder.setOnlyAlertOnce(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(ID_Notificacion, builder.build());
    }
}