package com.crisape.sqlite.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_NAME = "biblioteca.db"
        private const val DATABASE_VERSION = 2
    }

    override fun onCreate(p0: SQLiteDatabase) {
        val sql = "CREATE TABLE IF NOT EXISTS libros " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL," +
                "autor TEXT NOT NULL, " +
                "anio INTEGER NOT NULL);"
        p0.execSQL(sql)
    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {
        p0.execSQL("DROP TABLE IF EXISTS libros")
        onCreate(p0)
    }
}