package com.crisape.sqlite.modelo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.crisape.sqlite.entidades.Libro
import com.crisape.sqlite.util.SqliteHelper

class LibroDAO(context:Context) {
    private var sqliteHelper:SqliteHelper = SqliteHelper(context)

    fun registrarLibro(libro: Libro):String{
        var respuesta = ""
        val db = sqliteHelper.writableDatabase
        try {
            val valores = ContentValues()
            valores.put("nombre", libro.nombre)
            valores.put("autor",libro.autor)
            valores.put("anio",libro.anio)

            var resultado = db.insert("libros", null, valores)
            if (resultado == -1L){
                respuesta= "Error al insertar"
            }else{
                respuesta= "Se registro"
            }
        }catch (e:Exception){
            respuesta = e.message.toString()
        }finally {
            db.close()
        }

        return  respuesta
    }

    fun cargarLibros():ArrayList<Libro>{
        val listaLibros:ArrayList<Libro> = ArrayList()
        val query = "SELECT * FROM libros"
        val db = sqliteHelper.readableDatabase
        val cursor:Cursor

        try {
            cursor = db.rawQuery(query, null)
            if (cursor.count > 0){
                cursor.moveToFirst()
                do {
                    val id:Int = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                    val nombre:String = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                    val autor:String = cursor.getString(cursor.getColumnIndexOrThrow("autor"))
                    val anio:Int = cursor.getInt(cursor.getColumnIndexOrThrow("anio"))

                    val libro = Libro()
                    libro.id = id
                    libro.nombre = nombre
                    libro.autor = autor
                    libro.anio = anio
                    listaLibros.add(libro)
                }while (cursor.moveToNext())
            }
        }catch (e:Exception){

        }finally {
            db.close()
        }

        return listaLibros
    }
}