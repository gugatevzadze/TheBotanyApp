package com.example.thebotanyapp.presentation.screen.list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thebotanyapp.databinding.ListItemPlantBinding
import com.example.thebotanyapp.presentation.model.plant.PlantModel

class ListRecyclerAdapter (
    private val onItemClick: (PlantModel) -> Unit
) :
    ListAdapter<PlantModel, ListRecyclerAdapter.UserListViewHolder>(UserListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val binding =
            ListItemPlantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        Log.d("ListRecyclerAdapter", "Binding view holder at position $position")
        holder.bind()
    }

    inner class UserListViewHolder(private val binding: ListItemPlantBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var plant: PlantModel
        fun bind() {
            Log.d("ListRecyclerAdapter", "bind() called")
            plant = currentList[adapterPosition]
            Log.d("ListRecyclerAdapter", "Binding plant: $plant")
            binding.apply {
                tvName.text = plant.name
                tvSpecies.text = plant.species
                tvDescription.text = plant.description
                Glide.with(ivImage.context)
                    .load(plant.image)
                    .into(ivImage)
            }

            itemView.setOnClickListener {
                onItemClick.invoke(plant)
            }
        }
    }
    private class UserListDiffCallback : DiffUtil.ItemCallback<PlantModel>() {
        override fun areItemsTheSame(oldItem: PlantModel, newItem: PlantModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PlantModel, newItem: PlantModel): Boolean {
            return oldItem == newItem
        }
    }
}