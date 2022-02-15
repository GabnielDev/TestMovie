package com.example.testmovie.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.testmovie.R
import com.example.testmovie.data.ReviewItem
import com.example.testmovie.databinding.ItemReviewBinding
import com.example.testmovie.utils.Constants.BASE_URL_POSTER

class ReviewAdapter(
    private var listData: MutableList<ReviewItem?>
) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listData: MutableList<ReviewItem?>) {
        this.listData = listData
        notifyDataSetChanged()
    }

    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemReviewBinding.bind(itemView)

        fun bind(data: ReviewItem) {
            with(binding) {
                binding.imgReviewer.load(BASE_URL_POSTER + data.authorDetails?.avatarPath) {
                    error(R.drawable.ic_launcher_background)
                    placeholder(R.drawable.ic_launcher_foreground)
                }
                binding.txtNameReviewer.text = data.author
                binding.txtContentReviewe.text = data.content
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(listData[position]!!)
    }

    override fun getItemCount(): Int = listData.size
}