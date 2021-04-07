/**
 *  Autor: Haridian Palacios Gonzalez
 *  Asignatura: PGL
 *
 *  Aplicación Bloc de Notas con Base en SQLite
 *
 */

package com.example.examen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *  Base de Datos: Creamos el modelo de la base de datos, la instancia y
 *  los metodos que usaremos para visualizar los registros
 */
public class BaseDatos extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1; // Version de la base de datos
    public static final String DATABASE_NAME = "datos.db"; // Nombre de la base de datos
    private final Context contexto;

    public BaseDatos(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.contexto = context;
    }

    // Metodo para instanciar la base de datos
    private static BaseDatos instanciaUnica;

    // Metodo para sincronizar la base de datos
    public static synchronized BaseDatos getInstanciaUnica(Context context) {
        if (instanciaUnica == null) {
            instanciaUnica = new BaseDatos(context.getApplicationContext());
        }
        return instanciaUnica;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Creacion de la base de datos
        sqLiteDatabase.execSQL("CREATE TABLE " + "DATOS" + " ("
                + " fecha " + "TEXT NOT NULL,"
                + " categoria " + "TEXT NOT NULL,"
                + " nota " + " TEXT NOT NULL"
                + ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i1 > i) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "DATOS");
            onCreate(sqLiteDatabase);
        }
    }

    //Metodo para insertar registros
    public long insertarRegistro(SQLiteDatabase db, String datofecha, String datocategoria, String datonota) {

        ContentValues nuevoRegistro = new ContentValues(); // Creamos la variable que se usara para insertar los registros
        nuevoRegistro.put("fecha", datofecha);
        nuevoRegistro.put("categoria", datocategoria);
        nuevoRegistro.put("nota", datonota);
        //Insertamos el registro en la base de datos
        return db.insert("DATOS", null, nuevoRegistro); // Insertamos registros
    }


    public boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            // no se ha creado la base de datos
        }
        return checkDB != null;
    }

    //Metodo para obtener los registros de la base de datos
    public ArrayList<String> obtenerRegistros(SQLiteDatabase db) {

        String categoria = null, nota, fecha = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//Cambio de formato para la fecha
        String filtroWhereLike = "%";
        String where = "Fecha LIKE ?";
        String[] whereArgs = {filtroWhereLike};

    try{
        Cursor c = null; // Cursor que recorrera la base de datos
        c = db.rawQuery("SELECT * FROM DATOS ORDER BY fecha DESC", null); //rawQuery que nos mostrará todos los registros de la base de datos
        ArrayList<String> datos = new ArrayList<>(); //ArrayList donde se guardaran los registros
        if (c != null && c.moveToFirst()) { //recorrido para el cursor
            do {
                //Recogemos los registros de la Base de datos
                fecha = c.getString(0);
                categoria = "Categoria: " + c.getString(1);
                nota = "Nota: " + c.getString(2);

                //Cambiamos el formato del registro fecha de String a Date
                Date fechaD;
                java.util.Date nfecha = simpleDateFormat.parse(fecha);
                fechaD = new java.sql.Date(nfecha.getTime());

                // Añadimos los datos a la Lista
                datos.add(String.valueOf(fechaD));
                datos.add(categoria);
                datos.add(nota);
                datos.add("\n");
            } while (c.moveToNext());
        }

                    c.close();
                    return datos;
                } catch (Exception e) {
                    return null;
             }
         }

    }


