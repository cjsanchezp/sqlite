package com.crisape.sqlite

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.crisape.sqlite.entidades.Libro

class AdaptadorPersonalizado: RecyclerView.Adapter<AdaptadorPersonalizado.MiViewHolder>() {
    class MiViewHolder(var view:View):RecyclerView.ViewHolder(view) {
        private var nombre = view.findViewById<TextView>(R.id.filanombre)
        private var autor = view.findViewById<TextView>(R.id.filaAutor)
        private  var anio = view.findViewById<TextView>(R.id.filaAnio)

         var filaEditar = view.findViewById<ImageButton>(R.id.filaEditar)
         var filaEliminar = view.findViewById<ImageButton>(R.id.filaEliminar)

        fun bindView(libro: Libro){
            nombre.text = libro.nombre
            autor.text = libro.autor
            anio.text = libro.anio.toString()
        }
    }

    private var listaLibros: ArrayList<Libro> = ArrayList()
    fun addItems(items : ArrayList<Libro>){
        this.listaLibros = items
    }

    private lateinit var context: Context
    fun contexto(context: Context){
        this.context = context
    }

    private var onClickDeleteItem:((Libro) -> Unit)? = null
    fun setOnclickDeleteItem(callback:(Libro)->Unit){
        this.onClickDeleteItem=callback
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int) = MiViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.fila,parent,false)
    )

    override fun onBindViewHolder(holder: AdaptadorPersonalizado.MiViewHolder, position: Int) {
        val libroItem = listaLibros[position]
        holder.bindView(libroItem)

        holder.filaEditar.setOnClickListener {
            //Toast.makeText(context,listaLibros[position].nombre,Toast.LENGTH_LONG).show()
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("p_id", listaLibros[position].id)
            intent.putExtra("p_nombre", listaLibros[position].nombre)
            intent.putExtra("p_autor", listaLibros[position].autor)
            intent.putExtra("p_anio", listaLibros[position].anio)
            context.startActivity(intent)
        }

        holder.filaEliminar.setOnClickListener {
            onClickDeleteItem?.invoke(libroItem)
        }
    }

    override fun getItemCount(): Int {
        return listaLibros.size
    }
}