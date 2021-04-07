/**
 *  Autor: Haridian Palacios Gonzalez
 *  Asignatura: PGL
 *
 *  Aplicación Bloc de Notas con Base en SQLite
 *
 */

package com.example.examen;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import java.util.ArrayList;

/**
 *   Pantalla 3: Mostrará las notas guardadas en la base de datos mediante el metodo mostrarDatos()
 *
 *   Metodo consultar(): Filtrara las notas a mostrar segun las fechas que inserte el usuario
 *
 */

public class Pantalla3 extends AppCompatActivity {

    private TextView resultado;
    BaseDatos base_Datos;
    private EditText fechaInicio;
    private EditText fechaFinal;
    private String fechaInicial,fechaFinalizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla3);

        //Cargamos la instancia de la base de datos
        base_Datos = BaseDatos.getInstanciaUnica(this);

        resultado = findViewById(R.id.txtResultados);
        fechaInicio= findViewById(R.id.etFechaInicio);
        fechaFinal= findViewById(R.id.etFechaFin);

        mostrarDatos(); // Ejecutamos mostrarDatos() desde que se abre la pantalla
    }

    // Metodo consultar filtara los datos que se muestran desde la base de datos escogiendo asi solo  los seleccionados
     public void consultar(View v) {
         SQLiteDatabase db = base_Datos.getReadableDatabase(); //Base de datos
         fechaInicial = fechaInicio.getText().toString(); // Fecha inicial introducida por el usuario
         fechaFinalizar = fechaFinal.getText().toString(); // Fecha final introducida por el usuario

         //Filtro en rawQuery que con WHERE fecha BETWEEN que solo mostrara las notas guardadas entr las dos fechas
         Cursor c = db.rawQuery(" SELECT * FROM DATOS WHERE fecha>='" + fechaInicial + "' AND fecha<='" + fechaFinalizar +"'", null);

         //Recorremos los resultados para mostrarlos en pantalla
         resultado.setText("");
         if (c.moveToFirst()) {
             //Recorremos el cursor hasta que no haya más registros
             do {
                 String fecha = c.getString(0);
                 String categoria = c.getString(1);
                 String nota = c.getString(2);

                 // Mostramos el resultado en el Webview
                 resultado.append("<tr><td> " + fecha + " Categoría: " + categoria + "<br><br> Nota: " + nota + "</br></br>"); // Mostrara los resultados
                 WebView mWebView = (WebView) findViewById(R.id.webView);
                 String myHtmlString = "<table border=1>"+
                         "<html>" +
                         "<body>" +
                         "<h2>Notas: </h2>"+
                         resultado.getText()+
                         "</tr>"+
                         "</td>"+
                         "</table>"+
                         "</body></html>";
                 mWebView.loadData(myHtmlString, "text/html", "utf-8");
             } while(c.moveToNext()); // el cursos pasa a la siguiente posicion
         }c.close(); // Cerramos el cursor
     }

    // Metodo mostrarDatos() que recorrera la lista completa de notas guardada en la base de datos
    // y las mostrara en el Textview nada mas crearse la pantalla
    private void mostrarDatos()  {

        ArrayList<String> datos; //Lista a mostrar
        datos = base_Datos.obtenerRegistros(base_Datos.getReadableDatabase()); // Cogiendo los datos de la base de datos

        int i = 1;

        resultado.setText("");
        for (String dato : datos) {
            resultado.append("<tr><td>"+ dato);
            if (i % 4 == 0) //Condicional que hara un salto de linea cada 4 datos
               resultado.append("<br><br>");
            i++;
            //Webview que mostrara todos los datos guardados actualmente en la Base de Datos
            WebView mWebView = (WebView) findViewById(R.id.webView);
            String myHtmlString = "<table border=1>"+
                    "<html>" +
                    "<body>" +
                    "<h2>Notas: </h2>"+
                    resultado.getText()+
                    "</td>"+
                    "</tr>"+
                    "</table>"+
                    "</body></html>";
            mWebView.loadData(myHtmlString, "text/html", "utf-8");

        }

    }



}


