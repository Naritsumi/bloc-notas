/**
 *  Autor: Haridian Palacios Gonzalez
 *  Asignatura: PGL
 *
 *  Aplicación Bloc de Notas con Base en SQLite
 *
 */
package com.example.examen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 *  Primera pantalla: Dos botones que nos llevaran a las siguientes dos pantallas
 *
 *  Boton Nueva Nota: Nos lleva a la pantalla 2 donde se podran crear las notas y guardarlas
 *
 *  Boton Ver datos: Nos lleva a la pantalla 3 donde podremos ver las notas guardadas
 */
public class Pantalla1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnNota = (Button) findViewById(R.id.btnNuevaNota);
        // Metodo para el Boton Nueva Nota
        btnNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Pantalla2.class); // Intent a la pantalla 2
                startActivityForResult(i, 0); // inicia intent i
            }
        });
    }

    // Metodo para el boton ver datos
    public void Datos (View view){
        Intent b = new Intent(this, Pantalla3.class); // Intent a la pantalla 3
        startActivity(b);//inicia intent b
    }
}