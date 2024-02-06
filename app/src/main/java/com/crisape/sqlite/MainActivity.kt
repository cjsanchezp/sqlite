package com.crisape.sqlite

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.crisape.sqlite.entidades.Libro
import com.crisape.sqlite.modelo.LibroDAO

class MainActivity : AppCompatActivity() {
    private lateinit var txtTitulo: TextView
    private lateinit var txtNombre: EditText
    private lateinit var txtAutor: EditText
    private lateinit var txtAnio: EditText
    private lateinit var btnRegistrar: Button
    private var modificar:Boolean=false
    private var id:Int = 0

    val objLibro = Libro()
    val libroDAO = LibroDAO(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        asignarReferencias()
        recuperarDatos()
    }
    private fun recuperarDatos(){
        if (intent.hasExtra("p_id")){
            modificar = true
            txtTitulo.text = "Modificar Libro"
            btnRegistrar.setText("Guardar Cambios")
            id = intent.getIntExtra("p_id",0)
            txtNombre.setText(intent.getStringExtra("p_nombre"))
            txtAutor.setText(intent.getStringExtra("p_autor"))
            txtAnio.setText(intent.getIntExtra("p_anio", 0).toString())
        }
    }

    private fun asignarReferencias(){
        txtTitulo = findViewById(R.id.txtTitulo)
        txtNombre = findViewById(R.id.txtNombre)
        txtAutor = findViewById(R.id.txtAutor)
        txtAnio = findViewById(R.id.txtAnio)
        btnRegistrar = findViewById(R.id.btnRegistrar)

        btnRegistrar.setOnClickListener {
            if (capturarDatos()){
                var mensaje = ""
                if (modificar == true){
                    mensaje = libroDAO.modificarLibro(objLibro)
                }else{
                    libroDAO.registrarLibro(objLibro)
                }
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
            if (modificar == true){
                objLibro.id = id
            }
            objLibro.autor = autor
            objLibro.nombre = nombre
            objLibro.anio = anio.toInt()
        }
        return valida
    }
}