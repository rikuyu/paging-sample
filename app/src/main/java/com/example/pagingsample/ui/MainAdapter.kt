package com.example.pagingsample.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pagingsample.databinding.ItemBinding
import com.example.pagingsample.model.data.Character

class MainAdapter(
    private val viewLifecycleOwner: LifecycleOwner,
    private val context: Context
) : PagingDataAdapter<Character, MainAdapter.CharacterViewHolder>(DiffCallback) {

    inner class CharacterViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Character, viewLifecycleOwner: LifecycleOwner) {
            binding.apply {
                lifecycleOwner = viewLifecycleOwner
                name.text = item.name
                Glide.with(context).load(item.image).into(avatar)
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        character?.let {
            holder.bind(it, viewLifecycleOwner)
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }
}

