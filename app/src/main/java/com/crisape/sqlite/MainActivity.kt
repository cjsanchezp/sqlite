package com.crisape.sqlite

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.crisape.sqlite.entidades.Libro
import com.crisape.sqlite.modelo.LibroDAO

class MainActivity : AppCompatActivity() {
    private lateinit var txtNombre: EditText
    private lateinit var txtAutor: EditText
    private lateinit var txtAnio: EditText
    private lateinit var btnRegistrar: Button

    val objLibro = Libro()
    val libroDAO = LibroDAO(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        asignarReferencias()
    }

    private fun asignarReferencias(){
        txtNombre = findViewById(R.id.txtNombre)
        txtAutor = findViewById(R.id.txtAutor)
        txtAnio = findViewById(R.id.txtAnio)
        btnRegistrar = findViewById(R.id.btnRegistrar)

        btnRegistrar.setOnClickListener {
            if (capturarDatos()){
                val mensaje = libroDAO.registrarLibro(objLibro)
                mostrarMensaje(mensaje)
            }
        }
    }

    private fun mostrarMensaje(mensaje:String){
        val ventana = AlertDialog.Builder(this)
        ventana.setTitle("Mensaje Informativo")
        ventana.setMessage(mensaje)
        ventana.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialogInterface, i ->
            val intent = Intent(this, ListarActivity::class.java)
            startActivity(intent)
        })
        ventana.create().show()
    }

    private fun capturarDatos():Boolean{
        val nombre = txtNombre.text.toString()
        val autor = txtAutor.text.toString()
        val anio = txtAnio.text.toString()
        var valida = true

        if (nombre.isEmpty()){
            txtNombre.setError("Nombre obligatorio")
            valida = false
        }
        if (autor.isEmpty()){
            txtAutor.setError("Autor obligatorio")
            valida = false
        }
        if (anio.isEmpty()){
            txtAnio.setError("Año obligatorio")
            valida = false
        }

        if (valida){
            objLibro.autor = autor
            objLibro.nombre = nombre
            objLibro.anio = anio.toInt()
        }
        return valida
    }
}