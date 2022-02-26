package com.example.testmovie.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.testmovie.R
import com.example.testmovie.data.ResultTv
import com.example.testmovie.databinding.ItemPosterBinding
import com.example.testmovie.utils.Constants.BASE_URL_POSTER

class TvAdapter(private var listData: MutableList<ResultTv?>): RecyclerView.Adapter<TvAdapter.TvViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listData: MutableList<ResultTv?>) {
        this.listData = listData
        notifyDataSetChanged()
    }

    class TvViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemPosterBinding.bind(itemView)
        fun bind(data: ResultTv?) {
            with(binding) {
                binding.txtJudul.text = data?.name
                binding.txtRating.text = data?.voteAverage.toString()
                imgPoster.load(BASE_URL_POSTER + data?.posterPath) {
                    crossfade(true)
                    crossfade(1000)
                    placeholder(android.R.color.darker_gray)
                    error(R.drawable.ic_launcher_background)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_poster, parent, false)
        return TvViewHolder(view)
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount() : Int = listData.size
}