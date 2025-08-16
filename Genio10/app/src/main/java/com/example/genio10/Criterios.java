package com.example.genio10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class Criterios extends AppCompatActivity {

    private Spinner spinner_criterio, spinner_valor, spinner_rango;
    private EditText txtvalor;
    String id, nombre;
    int aux_criterio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criterios);
        spinner_criterio = findViewById(R.id.spinner2);
        spinner_valor = findViewById(R.id.spinner);
        spinner_rango = findViewById(R.id.spinner3);
        txtvalor = findViewById(R.id.txtValor);

        id = getIntent().getStringExtra("Cuenta");
        nombre = getIntent().getStringExtra("Nombre");

        String criterio [] = {"Número de Aciertos", "Número de Fallos", "Nombre de la Unidad", "Mostrar todas las Actividades"};
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_criterios, criterio);
        spinner_criterio.setAdapter(adapter);

        spinner_criterio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ValorCriterio();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void ValorCriterio(){
        aux_criterio = spinner_criterio.getSelectedItemPosition();
        if(aux_criterio == 0){
            txtvalor.setVisibility(View.VISIBLE);
            txtvalor.setText("");
            txtvalor.setHint("0 a 10");

            spinner_rango.setVisibility(View.VISIBLE);
            String valor [] = {"Mayor o Igual que", "Menor o Igual que"};
            ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_criterios, valor);
            spinner_rango.setAdapter(adapter);

            spinner_valor.setVisibility(View.GONE);
        }
        if(aux_criterio == 1){
            txtvalor.setVisibility(View.VISIBLE);
            txtvalor.setText("");
            txtvalor.setHint("0 a 10");

            spinner_rango.setVisibility(View.VISIBLE);
            String valor [] = {"Mayor o Igual que", "Menor o Igual que"};
            ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_criterios, valor);
            spinner_rango.setAdapter(adapter);
            spinner_valor.setVisibility(View.GONE);
        }
        if(aux_criterio == 2){
            spinner_valor.setVisibility(View.VISIBLE);
            spinner_rango.setVisibility(View.INVISIBLE);
            txtvalor.setVisibility(View.INVISIBLE);

            String [] titulo_resultado = new String[6];
            titulo_resultado[0] = "Conceptos de Matemáticas";
            titulo_resultado[1] = "Sumas";
            titulo_resultado[2] = "Restas";
            titulo_resultado[3] = "Conceptos de Matemáticas 2";
            titulo_resultado[4] = "Sumas Avanzadas";
            titulo_resultado[5] = "Restas Avanzadas";

            ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_criterios, titulo_resultado);
            spinner_valor.setAdapter(adapter);
        }
        if(aux_criterio == 3){
            spinner_rango.setVisibility(View.INVISIBLE);
            txtvalor.setVisibility(View.INVISIBLE);
            spinner_valor.setVisibility(View.VISIBLE);

            String valor [] = {"No Aplica"};
            ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_criterios, valor);
            spinner_valor.setAdapter(adapter);
        }
    }

    public void Continuar(View view){

        if(aux_criterio == 0 || aux_criterio == 1){
            if(!txtvalor.getText().equals("")){

                int aux_rango = spinner_rango.getSelectedItemPosition();
                String auxvalor = String.valueOf(txtvalor.getText());
                int aux_valor = Integer.parseInt(auxvalor);

                Intent intent = new Intent(getApplicationContext(), Progreso_admin.class);
                intent.putExtra("Cuenta", id);
                intent.putExtra("Nombre", nombre);
                intent.putExtra("Criterio", aux_criterio);
                intent.putExtra("Valor", aux_valor);
                intent.putExtra("Rango", aux_rango);
                startActivity(intent);
            }
            else{
                //Error por falta de datos
                txtvalor.setError("Ingrese un valor entre 0 y 10");
            }
        }
        if(aux_criterio == 2){
            int aux_unidad = spinner_valor.getSelectedItemPosition();

            Intent intent = new Intent(getApplicationContext(), Progreso_admin.class);
            intent.putExtra("Cuenta", id);
            intent.putExtra("Nombre", nombre);
            intent.putExtra("Criterio", aux_criterio);
            intent.putExtra("Valor", aux_unidad);
            startActivity(intent);
        }
        if(aux_criterio == 3){
            Intent intent = new Intent(getApplicationContext(), Progreso_admin.class);
            intent.putExtra("Cuenta", id);
            intent.putExtra("Nombre", nombre);
            startActivity(intent);
        }
    }

    public void Regresar(View view){
        Intent intent = new Intent(getApplicationContext(), Prueba.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Prueba.class);
        startActivity(intent);
    }
}