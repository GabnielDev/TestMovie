package com.example.testmovie.screens.view

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testmovie.R
import com.example.testmovie.adapter.MovieAdapter
import com.example.testmovie.data.ResultsItem
import com.example.testmovie.databinding.ActivityViewallBinding
import com.example.testmovie.screens.view.DetailActivity
import com.example.testmovie.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewallActivity : AppCompatActivity(), MovieAdapter.OnItemClickCallback {

    private lateinit var binding: ActivityViewallBinding
    private val movieViewModel: MovieViewModel by viewModels()
    private lateinit var nowplayingAdapter: MovieAdapter
    private lateinit var upcomingAdapter: MovieAdapter
    private lateinit var topratedAdapter: MovieAdapter
    private lateinit var popularAdapter: MovieAdapter

    private var data: MutableList<ResultsItem?>? = ArrayList()

    private var nextPage = true
    private var isEmpty = true
    private var loading = false
    private var kode: Int? = null
    private var page = 1

    companion object {
        const val EXTRA = "path"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewall)

        binding = ActivityViewallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        kode = intent.getIntExtra(EXTRA, 0)

        getData()
    }

    private fun getData() {
        getLoading()
        showPoster()

    }

    private fun getLoading() {
        movieViewModel.getLoading().observe(this) {
            loading = it
            if (loading) binding.progressCircular.visibility = VISIBLE
            else binding.progressCircular.visibility = GONE
        }

        binding.rvViewAllMovie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val countItem = linearLayoutManager.itemCount
                if (countItem.minus(1) == linearLayoutManager.findLastVisibleItemPosition()) {
                    if (!loading && nextPage) {
                        page++
                        getData()
                    }
                }
            }
        })

    }


    private fun showPoster() {
        when (kode) {
            1 -> {
                movieViewModel.getNowPlaying(page).observe(this) {
                    nextPage = !it?.results.isNullOrEmpty()
                    if (page == 0) {
                        data = it?.results
                        isEmpty = it?.results?.isEmpty() ?: true
                    } else {
                        nowplayingAdapter = MovieAdapter(ArrayList(), this)
                        binding.rvViewAllMovie.apply {
                            layoutManager = GridLayoutManager(context, 2)
                            adapter = nowplayingAdapter
                            it?.results?.let { it1 ->
                                data?.addAll(it1)
                            }
                        }
                        data?.let { data -> nowplayingAdapter.setData(data) }
                    }
                }
            }

            2 -> {
                movieViewModel.getUpComing(page).observe(this) {
                    nextPage = !it?.results.isNullOrEmpty()
                    if (page == 0) {
                        data = it?.results
                        isEmpty = it?.results?.isEmpty() ?: true
                    } else {
                        upcomingAdapter = MovieAdapter(ArrayList(), this)
                        binding.rvViewAllMovie.apply {
                            layoutManager = GridLayoutManager(context, 2)
                            adapter = upcomingAdapter
                            it?.results?.let {
                                    it1 -> data?.addAll(it1)
                            }
                        }
                        data?.let { data -> upcomingAdapter.setData(data) }
                    }
                }
            }


            3 -> {
                movieViewModel.getTopRated(page).observe(this) {
                    nextPage = !it?.results.isNullOrEmpty()
                    if (page == 0) {
                        data = it?.results
                        isEmpty = it?.results?.isEmpty() ?: true
                    } else {
                        topratedAdapter = MovieAdapter(ArrayList(), this)
                        binding.rvViewAllMovie.apply {
                            layoutManager = GridLayoutManager(context, 2)
                            adapter = topratedAdapter
                            it?.results?.let {
                                    it1 -> data?.addAll(it1)
                            }
                        }
                        data?.let { data -> topratedAdapter.setData(data) }
                    }
                }
            }

            4 -> {
                movieViewModel.getPopular(page).observe(this) {
                    nextPage = !it?.results.isNullOrEmpty()
                    if (page == 0) {
                        data = it?.results
                        isEmpty = it?.results?.isEmpty() ?: true
                    } else {
                        popularAdapter = MovieAdapter(ArrayList(), this)
                        binding.rvViewAllMovie.apply {
                            layoutManager = GridLayoutManager(context, 2)
                            adapter = popularAdapter
                            it?.results?.let {
                                    it1 -> data?.addAll(it1)
                            }
                        }
                        data?.let { data -> popularAdapter.setData(data) }
                    }
                }
            }

        }
    }

    override fun onItemClick(data: ResultsItem?) {
        startActivity(
            Intent(applicationContext, DetailActivity::class.java)
                .putExtra(DetailActivity.ID, data?.id)
        )
    }
}