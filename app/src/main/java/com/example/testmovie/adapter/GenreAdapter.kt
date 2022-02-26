package com.example.testmovie.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testmovie.R
import com.example.testmovie.data.GenresItem
import com.example.testmovie.databinding.ItemGenreBinding

class GenreAdapter(private var listData: MutableList<GenresItem?>) :
    RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listData: MutableList<GenresItem?>) {
        this.listData = listData
        notifyDataSetChanged()
    }

    class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemGenreBinding.bind(itemView)

        fun bind(data: GenresItem?) {
            binding.txtGenres.text = data?.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
        return GenreViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size
}