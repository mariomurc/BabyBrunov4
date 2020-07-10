package me.parzibyte.crudsqlite.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import me.parzibyte.crudsqlite.AyudanteBaseDeDatos;
import me.parzibyte.crudsqlite.modelos.Caca;

public class CacasController {
    private AyudanteBaseDeDatos ayudanteBaseDeDatos;
    private String NOMBRE_TABLA = "cacas";

    public CacasController(Context contexto) {
        ayudanteBaseDeDatos = new AyudanteBaseDeDatos(contexto);
    }


    public int eliminarCaca(Caca caca) {

        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        String[] argumentos = {String.valueOf(caca.getId())};
        return baseDeDatos.delete(NOMBRE_TABLA, "id = ?", argumentos);
    }

    public long nuevaCaca(Caca caca) {
        // writable porque vamos a insertar
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        ContentValues valoresParaInsertar = new ContentValues();
        valoresParaInsertar.put("fecha_caca", caca.getFechaCaca());
        valoresParaInsertar.put("hora_caca", caca.getHoraCaca());
        valoresParaInsertar.put("color", caca.getColor());
        valoresParaInsertar.put("comentario", caca.getComentario());
        return baseDeDatos.insert(NOMBRE_TABLA, null, valoresParaInsertar);
    }

    public int guardarCambios(Caca cacaEditada) {
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        ContentValues valoresParaActualizar = new ContentValues();
        valoresParaActualizar.put("fecha_caca", cacaEditada.getFechaCaca());
        valoresParaActualizar.put("hora_caca", cacaEditada.getHoraCaca());
        valoresParaActualizar.put("color", cacaEditada.getColor());
        valoresParaActualizar.put("comentario", cacaEditada.getComentario());
        // where id...
        String campoParaActualizar = "id = ?";
        // ... = idCaca
        String[] argumentosParaActualizar = {String.valueOf(cacaEditada.getId())};
        return baseDeDatos.update(NOMBRE_TABLA, valoresParaActualizar, campoParaActualizar, argumentosParaActualizar);
    }

    public ArrayList<Caca> obtenerCacas() {
        ArrayList<Caca> cacas = new ArrayList<>();
        // readable porque no vamos a modificar, solamente leer
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getReadableDatabase();
        // SELECT nombre, edad, id
        String[] columnasAConsultar = {"fecha_caca", "hora_caca", "id", "color", "comentario"};
        Cursor cursor = baseDeDatos.query(
                NOMBRE_TABLA,//from cacas
                columnasAConsultar,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor == null) {
            /*
                Salimos aquí porque hubo un error, regresar
                lista vacía
             */
            return cacas;

        }
        // Si no hay datos, igualmente regresamos la lista vacía
        if (!cursor.moveToFirst()) return cacas;

        // En caso de que sí haya, iteramos y vamos agregando los
        // datos a la lista de cacas
        do {
            // El 0 es el número de la columna, como seleccionamos
            // nombre, edad,id entonces el nombre es 0, edad 1 e id es 2
            String fechaObtenidaDeBD = cursor.getString(0);
            String horaObtenidaDeBD = cursor.getString(1);
            long idCaca = cursor.getLong(2);
            String colorObtenidoDeBD = cursor.getString(3);
            String comentarioObtenidoDeBD = cursor.getString(4);
            Caca cacaObtenidaDeBD = new Caca(fechaObtenidaDeBD, horaObtenidaDeBD, idCaca, colorObtenidoDeBD, comentarioObtenidoDeBD);
            cacas.add(cacaObtenidaDeBD);
        } while (cursor.moveToNext());

        // Fin del ciclo. Cerramos cursor y regresamos la lista de cacas :)
        cursor.close();
        return cacas;
    }
}
