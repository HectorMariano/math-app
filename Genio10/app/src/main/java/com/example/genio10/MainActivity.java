    package com.example.genio10;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

    public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    String cuenta, mensaje;


    int validacion = 0;
    int i = 0;
    FirebaseFirestore db;
    String Aux_correo;
    String unidad;
    String [] titulo_resultado = new String[6];
    int Inicio, notificaciones;
    int aux_noti;

    private final static String ID_Canal = "NOTIFICACION";
    private final static int ID_Notificacion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        mensaje = "No se porque no funciona";

        cuenta = getIntent().getStringExtra("Nombre_Cuenta");
        Inicio = getIntent().getIntExtra("Inicio", 0);
        notificaciones = getIntent().getIntExtra("Notificacion", 0);
        unidad = getIntent().getStringExtra("Unidad");
        aux_noti = getIntent().getIntExtra("IF", 0);

        if(Inicio == 1) {
            SharedPreferences preferences = getSharedPreferences("Nombre_Cuenta", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Nombre_Cuenta", cuenta);
            editor.apply();
            //Toast.makeText(getApplicationContext(), "Brenda", Toast.LENGTH_LONG).show();
        }

        titulo_resultado[0] = "Conceptos de Matemáticas";
        titulo_resultado[1] = "Sumas";
        titulo_resultado[2] = "Restas";
        titulo_resultado[3] = "Conceptos de Matemáticas 2";
        titulo_resultado[4] = "Sumas Avanzadas";
        titulo_resultado[5] = "Restas Avanzadas";

        SharedPreferences sharedPreferences2 = getSharedPreferences("Correo", Context.MODE_PRIVATE);
        Aux_correo = sharedPreferences2.getString("ID", "");

        db = FirebaseFirestore.getInstance();
        //Calificaciones();

        if(notificaciones == 1 && aux_noti == 1){
                CrearCanal();
                CrearNotificacion();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
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
        builder.setSmallIcon(R.drawable.trofeo);
        builder.setContentTitle("Sigue con tu Progreso");
        builder.setContentText("Nuevo Contenido Desbloqueado");
        builder.setStyle(new NotificationCompat.BigTextStyle()
                .bigText("Felicidades por terminar la unidad " + unidad + "\n Ahora puedes iniciar la siguiente unidad." +
                        " Además, revisa la sección de Recompensas para reclamar tu nuevo trofeo"));
        builder.setColor(Color.CYAN);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(ID_Notificacion, builder.build());
    }


    public void Mensaje_Salida(){
        AlertDialog.Builder Info = new AlertDialog.Builder(this);
        Info.setTitle("Cerrando Sesión");
        Info.setIcon(R.drawable.advertencia_info);
        Info.setMessage("Estas a punto de salir de tu sesión y volver al inicio de la aplicación.\n¿Deseas Continuar?");
        Info.setPositiveButton((Html.fromHtml("<font color='#000000'>CONTINUAR</font>")), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent regresar = new Intent(getApplicationContext(), activity_menu.class);
                regresar.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
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