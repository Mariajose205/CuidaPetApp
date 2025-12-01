package com.example.cuidapet.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cuidapet.R
import com.example.cuidapet.model.DogBreed

class DogBreedAdapter : RecyclerView.Adapter<DogBreedAdapter.ViewHolder>() {

    private val items = mutableListOf<DogBreed>()

    fun submitList(list: List<DogBreed>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_breed, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val breed = items[position]
        holder.bind(breed)
    }

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(breed: DogBreed) {
            // --- INICIO DE LA MODIFICACIÓN ---
            // Nombre de la raza
            itemView.findViewById<TextView>(R.id.txtBreedName).text = breed.name

            // Imagen de la raza
            val imageView = itemView.findViewById<ImageView>(R.id.imgBreed)
            Glide.with(itemView.context)
                .load(breed.image?.url)
                .placeholder(R.drawable.placeholder_circle) // Usamos un placeholder que sí existe
                .into(imageView)
            // --- FIN DE LA MODIFICACIÓN ---
        }
    }
}
