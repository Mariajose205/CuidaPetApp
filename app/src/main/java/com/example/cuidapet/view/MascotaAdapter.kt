package com.example.cuidapet.view

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cuidapet.R
import com.example.cuidapet.model.Mascota

class MascotaAdapter : ListAdapter<Mascota, MascotaAdapter.MascotaViewHolder>(MascotaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MascotaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mascota, parent, false)
        return MascotaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MascotaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MascotaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgMascota: ImageView = itemView.findViewById(R.id.imgMascotaItem)
        private val tvNombre: TextView = itemView.findViewById(R.id.tvNombreMascotaItem)
        private val tvEdad: TextView = itemView.findViewById(R.id.tvEdadMascotaItem)
        private val tvRaza: TextView = itemView.findViewById(R.id.tvRazaMascotaItem)
        private val tvPeso: TextView = itemView.findViewById(R.id.tvPesoMascotaItem)

        fun bind(mascota: Mascota) {
            tvNombre.text = "🐶 Nombre: ${mascota.nombre}"
            tvEdad.text = "📅 Edad: ${mascota.edad}"
            tvRaza.text = "🐾 Raza: ${mascota.raza}"
            tvPeso.text = "⚖️ Peso: ${mascota.peso} kg"

            mascota.fotoUri?.let { uriString ->
                if (uriString.isNotEmpty()) {
                    imgMascota.setImageURI(Uri.parse(uriString))
                }
            }
        }
    }

    class MascotaDiffCallback : DiffUtil.ItemCallback<Mascota>() {
        override fun areItemsTheSame(oldItem: Mascota, newItem: Mascota): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Mascota, newItem: Mascota): Boolean {
            return oldItem == newItem
        }
    }
}
