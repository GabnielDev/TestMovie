package com.example.testmovie.repository

import com.example.testmovie.network.MovieServiceInstance
import com.example.testmovie.utils.Constants.API_KEY
import javax.inject.Inject

class Repository @Inject constructor(val services: MovieServiceInstance) {

    suspend fun getNowPlayingMovie(page: Int) = services.getNowPlayingMovie(API_KEY, "en-US", page)

    suspend fun getUpComingMovie(page: Int) = services.getUpComingMovie(API_KEY, "en-US", page)

    suspend fun getTopRatedMovie(page: Int) = services.getTopRatedMovie(API_KEY, "en-US", page)

    suspend fun getPopularMovie(page: Int) = services.getPopularMovie(API_KEY, "en-US", page)

    suspend fun getDetailMovie(id: Int) = services.getDetailMovie(id, API_KEY, "en-US")

    suspend fun getTrailerMovie(id: Int) = services.getTrailerMovie(id, API_KEY, "en-US")

    suspend fun getReviewMovie(id: Int, page: Int) =
        services.getReviewMovie(id, API_KEY, "en-US", page)

}