package com.example.testmovie.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testmovie.data.DetailMovie
import com.example.testmovie.data.GenresItem
import com.example.testmovie.data.ResultTrailer
import com.example.testmovie.data.ReviewItem
import com.example.testmovie.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(val repository: Repository): ViewModel() {

    private val loading = MutableLiveData<Boolean>()
    private val status = MutableLiveData<Int>()
    private val message = MutableLiveData<String>()


    fun getDetail(id: Int): LiveData<DetailMovie> {
        val detail = MutableLiveData<DetailMovie>()
        viewModelScope.launch {
            repository.getDetailMovie(id).let {
                try {
                    val data = it.body()
                    detail.value = data
                } catch (t: Throwable) {

                }
            }
        }
        return detail
    }

    fun getGenre(id: Int): LiveData<ArrayList<GenresItem?>?> {
        val genre = MutableLiveData<ArrayList<GenresItem?>?>()
        viewModelScope.launch {
            repository.getDetailMovie(id).let {
                try {
                    val data = it.body()?.genres
                    genre.value = data

                } catch (t: Throwable) {

                }
            }
        }
        return genre
    }

    fun getTrailer(id: Int): LiveData<ArrayList<ResultTrailer?>?> {
        val trailerMovie = MutableLiveData<ArrayList<ResultTrailer?>?>()
        viewModelScope.launch {
            repository.getTrailerMovie(id).let {
                try {
                    val data = it.body()?.results
                    trailerMovie.value = data
                } catch (t: Throwable) {

                }
            }
        }
        return trailerMovie
    }

    fun getReview(id: Int, page: Int): LiveData<ArrayList<ReviewItem?>?> {
        val reviewMovie = MutableLiveData<ArrayList<ReviewItem?>?>()
        viewModelScope.launch {
            loading.value = true
            repository.getReviewMovie(id, page).let {
                try {
                    val data = it.body()?.results
                    reviewMovie.value = data
                    loading.value = false
                } catch (t: Throwable) {

                }
                loading.value = false
            }
        }
        return reviewMovie
    }

    fun getLoading(): LiveData<Boolean> = loading
    fun getStatus(): LiveData<Int> = status
    fun getMessage(): LiveData<String> = message

}