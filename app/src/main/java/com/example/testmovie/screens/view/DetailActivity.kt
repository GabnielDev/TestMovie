package com.example.testmovie.screens.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.testmovie.R
import com.example.testmovie.adapter.GenreAdapter
import com.example.testmovie.adapter.ReviewAdapter
import com.example.testmovie.data.ReviewItem
import com.example.testmovie.databinding.ActivityDetailBinding
import com.example.testmovie.utils.Constants.BASE_URL_BACKPOSTER
import com.example.testmovie.utils.Constants.BASE_URL_POSTER
import com.example.testmovie.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val detailViewmodel: DetailViewModel by viewModels()
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var reviewAdapter: ReviewAdapter
    private var nextPage = true
    private var isEmpty = true
    private var page = 1
    private var loading = false
    private var data: MutableList<ReviewItem?>? = ArrayList()

    companion object {
        const val ID = "id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        onClick()
        getData()

    }

    private fun setData() {
        getLoading()
        getData()
    }

    private fun getLoading() {
        detailViewmodel.getLoading().observe(this) {
            loading = it
            if (loading) binding.progressCircular.visibility = VISIBLE
            else binding.progressCircular.visibility = GONE
        }

        binding.rvReview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val countItem = linearLayoutManager.itemCount
                if (countItem.minus(1) == linearLayoutManager.findLastVisibleItemPosition()) {
                    if (!loading && nextPage) {
                        page++
                        setData()
                    }
                }
            }
        })
    }


    private fun setupView() {
        genreAdapter = GenreAdapter(ArrayList())

        binding.rvGenres.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = genreAdapter
        }

        reviewAdapter = ReviewAdapter(ArrayList())

        binding.rvReview.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = reviewAdapter
        }
    }

    private fun getData() {

        val id = intent.getIntExtra(ID, 0)
        detailViewmodel.getDetail(id).observe(this) {
            with(binding) {
                txtTitle.text = it?.title
                txtDate.text = it?.releaseDate
                txtRating.text = it?.voteAverage.toString()
                txtOverview.text = it?.overview
                imgPoster.load(BASE_URL_POSTER + it.posterPath)
                imgBackPoster.load(BASE_URL_BACKPOSTER + it.backdropPath)
            }

        }

        detailViewmodel.getGenre(id).observe(this) {
            if (it != null) {
                genreAdapter.setData(it)
            }
            Log.e("genreData", "$it")
        }

        detailViewmodel.getReview(id, page).observe(this) {
            nextPage = !it.isNullOrEmpty()
            if (page == 0) {
                data = it
                if (it != null) {
                    isEmpty = it.isEmpty()
                }
            } else {
                if (it != null) {
                    data?.addAll(it)
                }
            }
            data?.let { it1 -> reviewAdapter.setData(it1) }
        }

    }

    private fun onClick() {
        binding.btnTrailer.setOnClickListener {
            val id = intent.getIntExtra(ID, 0)
            detailViewmodel.getTrailer(id).observe(this) {
                val trailerId = it?.get(0)?.key
                startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$trailerId"))
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            }
        }
    }

}