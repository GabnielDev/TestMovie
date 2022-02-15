package com.example.testmovie.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.testmovie.R
import com.example.testmovie.data.ResultsItem
import com.example.testmovie.databinding.ItemPosterBinding
import com.example.testmovie.utils.Constants.BASE_URL_POSTER

class MovieAdapter(
    private var listData: MutableList<ResultsItem?>,
    private var onItemClickCallback: OnItemClickCallback
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listData: MutableList<ResultsItem?>) {
        this.listData = listData
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemPosterBinding.bind(itemView)

        fun bind(data: ResultsItem) {
            with(binding) {
                binding.txtJudul.text = data.title
                binding.txtRating.text = data.voteAverage.toString()
                imgPoster.load(BASE_URL_POSTER + data.posterPath) {
                    crossfade(true)
                    crossfade(1000)
                    placeholder(android.R.color.darker_gray)
                    error(R.drawable.ic_launcher_background)
                }

                itemView.setOnClickListener {
                    onItemClickCallback.onItemClick(data)
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_poster, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(listData[position]!!)
    }

    override fun getItemCount(): Int = listData.size

    interface OnItemClickCallback {
        fun onItemClick(data: ResultsItem?)
    }
}