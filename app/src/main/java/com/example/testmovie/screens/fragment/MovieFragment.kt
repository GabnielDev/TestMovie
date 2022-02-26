package com.example.testmovie.screens.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testmovie.adapter.MovieAdapter
import com.example.testmovie.data.ResultsItem
import com.example.testmovie.databinding.FragmentMovieBinding
import com.example.testmovie.screens.view.DetailActivity
import com.example.testmovie.screens.view.ViewallActivity
import com.example.testmovie.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieFragment : Fragment(), MovieAdapter.OnItemClickCallback {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private val movieViewModel: MovieViewModel by viewModels()

    private lateinit var nowplayingAdapter: MovieAdapter
    private lateinit var upcomingAdapter: MovieAdapter
    private lateinit var topratedAdapter: MovieAdapter
    private lateinit var popularAdapter: MovieAdapter

    private var page = 1
    private var loading = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        val view = binding.root

        getLoading()
        getData()
        setupView()
        onClick()


        return view
    }

    private fun getLoading() {
        movieViewModel.getLoading().observe(viewLifecycleOwner) {
            loading = it
            if (loading) binding.progressCircular.visibility = View.VISIBLE
            else binding.progressCircular.visibility = View.GONE
        }

        movieViewModel.getStatus().observe(viewLifecycleOwner) {
            if (it >= 400) binding.lineNodata.visibility = View.GONE
        }

        movieViewModel.getMessage().observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) binding.lineNodata.visibility = View.VISIBLE
            binding.rlPlaying.visibility = View.GONE
            binding.rlPopular.visibility = View.GONE
            binding.rlTopRated.visibility = View.GONE
            binding.rlUpComing.visibility = View.GONE

            if (it.isNullOrEmpty()) binding.lineNodata.visibility = View.GONE

        }

    }

    private fun getData() {
        movieViewModel.getNowPlaying(page).observe(viewLifecycleOwner) {
            it?.results?.let { data ->
                nowplayingAdapter.setData(data)
            }
        }

        movieViewModel.getUpComing(page).observe(viewLifecycleOwner) {
            it?.results?.let { data ->
                upcomingAdapter.setData(data)
            }
        }

        movieViewModel.getTopRated(page).observe(viewLifecycleOwner) {
            it?.results?.let { data ->
                topratedAdapter.setData(data)
            }
        }

        movieViewModel.getPopular(page).observe(viewLifecycleOwner) {
            it?.results?.let { data ->
                popularAdapter.setData(data)
            }
        }

    }

    private fun setupView() {
        nowplayingAdapter = MovieAdapter(ArrayList(), this)

        binding.rvNowPlaying.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = nowplayingAdapter
        }

        upcomingAdapter = MovieAdapter(ArrayList(), this)

        binding.rvUpComing.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = upcomingAdapter
        }

        topratedAdapter = MovieAdapter(ArrayList(), this)

        binding.rvTopRated.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = topratedAdapter
        }

        popularAdapter = MovieAdapter(ArrayList(), this)

        binding.rvPopular.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularAdapter
        }

    }

    private fun onClick() {

        with(binding) {
            txtAllNowPlaying.setOnClickListener {
                startActivity(
                    Intent(context, ViewallActivity::class.java)
                        .putExtra(ViewallActivity.EXTRA, 1)
                )
            }

            txtAllUpComing.setOnClickListener {
                startActivity(
                    Intent(context, ViewallActivity::class.java)
                        .putExtra(ViewallActivity.EXTRA, 2)
                )
            }

            txtAllTopRated.setOnClickListener {
                startActivity(
                    Intent(context, ViewallActivity::class.java)
                        .putExtra(ViewallActivity.EXTRA, 3)
                )
            }

            txtAllPopular.setOnClickListener {
                startActivity(
                    Intent(context, ViewallActivity::class.java)
                        .putExtra(ViewallActivity.EXTRA, 4)
                )
            }
        }

    }

    override fun onItemClick(data: ResultsItem?) {
        startActivity(
            Intent(context, DetailActivity::class.java)
                .putExtra(DetailActivity.ID, data?.id)
        )
    }

}