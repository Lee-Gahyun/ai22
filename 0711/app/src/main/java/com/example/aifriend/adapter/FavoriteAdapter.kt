package com.example.aifriend.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aifriend.databinding.ItemFavoriteBinding

class FavoriteAdapter(val onItemClick: (FavoriteItem) -> Unit) :
    RecyclerView.Adapter<FavoriteViewHolder>() {

    private val favoriteList = mutableListOf<FavoriteItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding =
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favoriteList[position], onItemClick)
    }

    override fun getItemCount(): Int =
        favoriteList.size


    fun addAll(list: List<FavoriteItem>) {
        favoriteList.clear()
        favoriteList.addAll(list)
        notifyDataSetChanged()
    }
}

class FavoriteViewHolder(private val binding: ItemFavoriteBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: FavoriteItem, onItemClick: (FavoriteItem) -> Unit) {
        binding.tvFavorite.text = item.name
        itemView.setOnClickListener { onItemClick(item) }
    }
}


data class FavoriteItem(
    val name: String
)