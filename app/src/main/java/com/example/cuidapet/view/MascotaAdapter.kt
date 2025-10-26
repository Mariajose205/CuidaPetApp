package com.example.cuidapet.view

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cuidapet.R
import com.example.cuidapet.model.Mascota

class MascotaAdapter(private val mascotas: List<Mascota>) : RecyclerView.Adapter<MascotaAdapter.MascotaViewHolder>() {

    // Este método crea una nueva "fila" (ViewHolder) inflando el layout del item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MascotaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mascota, parent, false)
        return MascotaViewHolder(view)
    }

    // Este método devuelve la cantidad total de items en la lista
    override fun getItemCount(): Int = mascotas.size

    // Este método conecta los datos de una mascota específica con una fila (ViewHolder)
    override fun onBindViewHolder(holder: MascotaViewHolder, position: Int) {
        val mascota = mascotas[position]
        holder.bind(mascota)
    }

    // Clase interna que representa una sola fila de la lista y sus vistas
    class MascotaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgFoto: ImageView = itemView.findViewById(R.id.imgFotoMascotaItem)
        private val tvNombre: TextView = itemView.findViewById(R.id.tvNombreMascotaItem)
        private val tvRaza: TextView = itemView.findViewById(R.id.tvRazaMascotaItem)
        private val tvEdad: TextView = itemView.findViewById(R.id.tvEdadMascotaItem)
        private val tvPeso: TextView = itemView.findViewById(R.id.tvPesoMascotaItem)

        fun bind(mascota: Mascota) {
            tvNombre.text = mascota.nombre
            tvRaza.text = "Raza: ${mascota.raza}"
            tvEdad.text = "Edad: ${mascota.edad}"
            tvPeso.text = "Peso: ${mascota.peso}"

            mascota.fotoUri?.let {
                if (it.isNotEmpty()) {
                    imgFoto.setImageURI(Uri.parse(it))
                }
            }
        }
    }
}
