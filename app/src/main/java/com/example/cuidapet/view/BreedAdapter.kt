package com.example.cuidapet.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cuidapet.R

class BreedAdapter : ListAdapter<String, BreedAdapter.BreedViewHolder>(BreedDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_breed, parent, false)
        return BreedViewHolder(view)
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BreedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtBreed: TextView = itemView.findViewById(R.id.txtBreed)

        fun bind(breed: String) {
            txtBreed.text = breed.replaceFirstChar { it.uppercase() }
        }
    }

    class BreedDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}
