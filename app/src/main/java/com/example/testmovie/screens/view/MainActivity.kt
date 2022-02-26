package com.example.testmovie.screens.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testmovie.R
import com.example.testmovie.adapter.MovieAdapter
import com.example.testmovie.data.ResultsItem
import com.example.testmovie.databinding.ActivityMainBinding
import com.example.testmovie.screens.fragment.MovieFragment
import com.example.testmovie.screens.fragment.TvFragment
import com.example.testmovie.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val fragmentMovie: Fragment = MovieFragment()
    private val fragmentTv: Fragment = TvFragment()

    private val fm: FragmentManager = supportFragmentManager

    var active: Fragment = fragmentMovie

    lateinit var menu: Menu
    lateinit var menuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpBotNav()
    }

    private fun setUpBotNav() {
        fm.beginTransaction().add(R.id.flContainer, fragmentMovie).show(fragmentMovie).commit()
        fm.beginTransaction().add(R.id.flContainer, fragmentTv).hide(fragmentTv).commit()

        menu = binding.botNav.menu
        menuItem = menu.get(0)
        menuItem.isChecked = true

        binding.botNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navMovie -> {
                    callFragment(0, fragmentMovie)
                }
                R.id.navTv -> {
                    callFragment(1, fragmentTv)
                }
            }
            false
        }
    }

    fun callFragment(int: Int, fragment: Fragment) {
        menuItem = menu.getItem(int)
        menuItem.isChecked = true
        fm.beginTransaction().hide(active).show(fragment).commit()
        active = fragment
    }

//    private fun getLoading() {
//        movieViewModel.getLoading().observe(this) {
//            loading = it
//            if (loading) binding.progressCircular.visibility = VISIBLE
//            else binding.progressCircular.visibility = GONE
//        }
//    }
//
//    private fun getData() {
//        movieViewModel.getNowPlaying(page).observe(this) {
//            if (it != null) {
//                nowplayingAdapter.setData(it)
//            }
//            Log.e("playingData", "$it")
//        }
//
//        movieViewModel.getUpComing(page).observe(this) {
//            if (it != null) {
//                upcomingAdapter.setData(it)
//            }
//            Log.e("upComingdata", "$it")
//        }
//
//        movieViewModel.getTopRated(page).observe(this) {
//            if (it != null) {
//                topratedAdapter.setData(it)
//            }
//            Log.e("topratedData", "$it")
//        }
//
//        movieViewModel.getPopular(page).observe(this) {
//            if (it != null) {
//                popularAdapter.setData(it)
//            }
//            Log.e("popularData", "$it")
//        }
//
//    }
//
//    private fun setupView() {
//        nowplayingAdapter = MovieAdapter(ArrayList(), this)
//
//        binding.rvNowPlaying.apply {
//            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//            adapter = nowplayingAdapter
//        }
//
//        upcomingAdapter = MovieAdapter(ArrayList(), this)
//
//        binding.rvUpComing.apply {
//            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//            adapter = upcomingAdapter
//        }
//
//        topratedAdapter = MovieAdapter(ArrayList(), this)
//
//        binding.rvTopRated.apply {
//            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//            adapter = topratedAdapter
//        }
//
//        popularAdapter = MovieAdapter(ArrayList(), this)
//
//        binding.rvPopular.apply {
//            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//            adapter = popularAdapter
//        }
//
//    }
//
//    override fun onItemClick(data: ResultsItem?) {
//        startActivity(
//            Intent(this, DetailActivity::class.java)
//                .putExtra(DetailActivity.ID, data?.id)
//        )
//    }
//
//    private fun onClick() {
//        with(binding) {
//            txtAllNowPlaying.setOnClickListener {
//                startActivity(
//                    Intent(applicationContext, ViewallActivity::class.java)
//                        .putExtra(ViewallActivity.EXTRA, 1)
//                )
//            }
//
//            txtAllUpComing.setOnClickListener {
//                startActivity(
//                    Intent(applicationContext, ViewallActivity::class.java)
//                        .putExtra(ViewallActivity.EXTRA, 2)
//                )
//            }
//
//            txtAllTopRated.setOnClickListener {
//                startActivity(
//                    Intent(applicationContext, ViewallActivity::class.java)
//                        .putExtra(ViewallActivity.EXTRA, 3)
//                )
//            }
//
//            txtAllPopular.setOnClickListener {
//                startActivity(
//                    Intent(applicationContext, ViewallActivity::class.java)
//                        .putExtra(ViewallActivity.EXTRA, 4)
//                )
//            }
//        }
//
//    }


}