package com.example.testmovie.repository

import com.example.testmovie.network.TvServiceInstance
import com.example.testmovie.utils.Constants
import javax.inject.Inject

class TvRepository @Inject constructor(private val services: TvServiceInstance) {

    suspend fun getAiringTodayTv(page: Int) =
        services.getAiringTodayTv(page)

    suspend fun getTopRatedTv(page: Int) = services.getTopRatedTv(page)

    suspend fun getOntheAirTv(page: Int) =
        services.getOntheAirTv(page)

    suspend fun getPopularTv(page: Int) =
        services.getPopularTv(page)
}