package com.example.testmovie.network

import com.example.testmovie.data.DetailMovie
import com.example.testmovie.data.MovieTrailer
import com.example.testmovie.data.ResponseMovie
import com.example.testmovie.data.ReviewMovie
import com.example.testmovie.utils.Constants.URL_DETAIL_MOVIE
import com.example.testmovie.utils.Constants.URL_NOWPLAYING_MOVIE
import com.example.testmovie.utils.Constants.URL_POPULAR_MOVIE
import com.example.testmovie.utils.Constants.URL_REVIEW_MOVIE
import com.example.testmovie.utils.Constants.URL_TOPRATED_MOVIE
import com.example.testmovie.utils.Constants.URL_TRAILER_MOVIE
import com.example.testmovie.utils.Constants.URL_UPCOMING_MOVIE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieServiceInstance {

    @GET(URL_NOWPLAYING_MOVIE)
    suspend fun getNowPlayingMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<ResponseMovie>

    @GET(URL_UPCOMING_MOVIE)
    suspend fun getUpComingMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<ResponseMovie>

    @GET(URL_TOPRATED_MOVIE)
    suspend fun getTopRatedMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<ResponseMovie>

    @GET(URL_POPULAR_MOVIE)
    suspend fun getPopularMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<ResponseMovie>

    @GET(URL_DETAIL_MOVIE)
    suspend fun getDetailMovie(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
    ): Response<DetailMovie>

    @GET(URL_TRAILER_MOVIE)
    suspend fun getTrailerMovie(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
    ): Response<MovieTrailer>

    @GET(URL_REVIEW_MOVIE)
    suspend fun getReviewMovie(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<ReviewMovie>
}