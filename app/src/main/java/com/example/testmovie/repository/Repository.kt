package com.example.testmovie.repository

import com.example.testmovie.network.MovieServiceInstance
import javax.inject.Inject

class Repository @Inject constructor(private val services: MovieServiceInstance) {

    suspend fun getNowPlayingMovie(page: Int) = services.getNowPlayingMovie(page)

    suspend fun getUpComingMovie(page: Int) = services.getUpComingMovie(page)

    suspend fun getTopRatedMovie(page: Int) = services.getTopRatedMovie(page)

    suspend fun getPopularMovie(page: Int) = services.getPopularMovie(page)

    suspend fun getDetailMovie(id: Int) = services.getDetailMovie(id)

    suspend fun getTrailerMovie(id: Int) = services.getTrailerMovie(id)

    suspend fun getReviewMovie(id: Int, page: Int) = services.getReviewMovie(id, page)

}