package com.example.testmovie.screens.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testmovie.adapter.TvAdapter
import com.example.testmovie.databinding.FragmentTvBinding
import com.example.testmovie.viewmodel.TvViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TvFragment : Fragment() {

    private var _binding: FragmentTvBinding? = null
    private val binding get() = _binding!!

    private val tvViewModel: TvViewModel by viewModels()
    private lateinit var airingtodayAdapter: TvAdapter
    private lateinit var ontheairAdapter: TvAdapter
    private lateinit var topratedAdapter: TvAdapter
    private lateinit var popularAdapter: TvAdapter

    private var page = 1
    private var loading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTvBinding.inflate(inflater, container, false)
        val view = binding.root

        getLoading()
        setupView()
        getData()

        return view
    }

    private fun getLoading() {
        tvViewModel.getLoading().observe(viewLifecycleOwner) {
            loading = it
            if (loading) binding.progressCircular.visibility = View.VISIBLE
            else binding.progressCircular.visibility = View.GONE
        }

        tvViewModel.getStatus().observe(viewLifecycleOwner) {
            if (it >= 400) binding.lineNodata.visibility = View.GONE
        }

        tvViewModel.getMessage().observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) binding.lineNodata.visibility = View.VISIBLE
            binding.rlAiringTv.visibility = View.GONE
            binding.rlOntheAirTv.visibility = View.GONE
            binding.rlPopularTv.visibility = View.GONE
            binding.rlTopRatedTv.visibility = View.GONE

            if (it.isNullOrEmpty()) binding.lineNodata.visibility = View.GONE

        }

    }

    private fun getData() {
        tvViewModel.getAiringToday(page).observe(viewLifecycleOwner) {
            it?.results?.let { data ->
                airingtodayAdapter.setData(data)
            }
        }

        tvViewModel.getOntheAir(page).observe(viewLifecycleOwner) {
            it?.results?.let { data ->
                ontheairAdapter.setData(data)
            }
        }

        tvViewModel.getTopRated(page).observe(viewLifecycleOwner) {
            it?.results?.let { it1 ->
                topratedAdapter.setData(it1)
            }
        }

        tvViewModel.getPopular(page).observe(viewLifecycleOwner) {
            it?.results?.let { it1 ->
                popularAdapter.setData(it1)
            }
        }

    }

    private fun setupView() {
        airingtodayAdapter = TvAdapter(ArrayList())

        binding.rvAiringToday.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = airingtodayAdapter
        }

        ontheairAdapter = TvAdapter(ArrayList())
        binding.rvOntheAirTv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = ontheairAdapter
        }

        topratedAdapter = TvAdapter(ArrayList())
        binding.rvTopRatedTv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = topratedAdapter
        }

        popularAdapter = TvAdapter(ArrayList())
        binding.rvPopularTv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularAdapter
        }

    }

}