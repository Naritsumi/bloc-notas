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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 *  Segunda Pantalla: Cogerá los datos introducidos por el usuario y los guardará en la base de datos
 *
 *  Boton Guardar: Ejecutara el metodo y nos llevara a la pantalla 3
 */
public class Pantalla2 extends AppCompatActivity {

    private EditText txtNotas;
    private EditText txtFecha;
    private RadioButton rbOtra;
    private RadioButton rbTareas;
    private String categoria,grupo;
    BaseDatos base_Datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla2);

        //Cargamos la instancia de la base de datos
        base_Datos =  BaseDatos.getInstanciaUnica(this);

        txtNotas = (EditText) findViewById(R.id.etNuevaNota);
        txtFecha = (EditText) findViewById(R.id.etFecha);
        rbOtra = (RadioButton) findViewById(R.id.Otra);
        rbTareas = (RadioButton) findViewById(R.id.Tareas);
    }

    // Metodo Enviar que guardará los datos introducideos en los cuadros de texto
    // directamente en la Base de Datos
    public void Enviar(View view){

        // Condicional para elegir la categoria
        if(rbTareas.isChecked())
        {
            categoria = rbTareas.getText().toString();
        }else if (rbOtra.isChecked()){

            //rbTareas.setChecked(false);
            categoria = rbOtra.getText().toString();
        }

        // Condicional que de ser cierto y no haber espacios en blanco, guardara lso datos en la Base de Datos
        if (!txtNotas.getText().toString().isEmpty() && !txtFecha.getText().toString().isEmpty()) {

            // Metodo que carga los datos en la Base de Datos
            base_Datos.insertarRegistro(base_Datos.getWritableDatabase(), txtFecha.getText().toString(), categoria, txtNotas.getText().toString());

            //Mensaje que nos avisará de que se han cargado los datos
            Toast.makeText(getApplication(),"Registro Guardado.", Toast.LENGTH_SHORT).show();

            // Intent para pasar a la pantalla 3 y visualizar las notas
            Intent i = new Intent(getApplication(), Pantalla3.class);
            startActivity(i);
            } else {
            // Mensaje que saltara en caso de no haber rellenado todos los cuadros de texto
                Toast.makeText(Pantalla2.this, "Introduzca todos los datos", Toast.LENGTH_LONG).show();
         }
    }
}