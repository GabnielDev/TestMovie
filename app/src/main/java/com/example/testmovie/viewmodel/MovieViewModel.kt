package com.example.testmovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testmovie.data.ResponseMovie
import com.example.testmovie.data.ResultsItem
import com.example.testmovie.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    private val loading = MutableLiveData<Boolean>()
    private val status = MutableLiveData<Int>()
    private val message = MutableLiveData<String>()

    val playing = MutableLiveData<ResponseMovie?>()
    val upcoming = MutableLiveData<ResponseMovie?>()
    val toprated = MutableLiveData<ResponseMovie?>()
    val popular = MutableLiveData<ResponseMovie?>()



    fun getNowPlaying(page: Int): LiveData<ResponseMovie?> {
        loading.value = true
        viewModelScope.launch {
            repository.getNowPlayingMovie(page).let {
                try {
                    val data = it.body()
                    playing.value = data
                    loading.value = false
                } catch (t: Throwable) {
                    when (t) {
                        is Exception -> message.value = t.message.toString()
                        is HttpException -> message.value = t.message().toString()
                        else -> message.value = "Unknow Error"
                    }
                }
                loading.value = false
            }
        }
        return playing
    }

    fun getUpComing(page: Int): LiveData<ResponseMovie?> {
        loading.value = true
        viewModelScope.launch {
            repository.getUpComingMovie(page).let {
                try {
                    val data = it.body()
                    upcoming.value = data
                    loading.value = false
                } catch (t: Throwable) {
                    when (t) {
                        is Exception -> message.value = t.message.toString()
                        is HttpException -> message.value = t.message().toString()
                        else -> message.value = "Unknow Error"
                    }
                }
                loading.value = false
            }
        }
        return upcoming
    }

    fun getTopRated(page: Int): LiveData<ResponseMovie?> {
        loading.value = true
        viewModelScope.launch {
            repository.getTopRatedMovie(page).let {
                try {
                    val data = it.body()
                    toprated.value = data
                    loading.value = false
                } catch (t: Throwable) {
                    when (t) {
                        is Exception -> message.value = t.message.toString()
                        is HttpException -> message.value = t.message().toString()
                        else -> message.value = "Unknow Error"
                    }
                }
                loading.value = false
            }
        }
        return toprated
    }

    fun getPopular(page: Int): LiveData<ResponseMovie?> {
        loading.value = true
        viewModelScope.launch {
            repository.getPopularMovie(page).let {
                try {
                    val data = it.body()
                    popular.value = data
                    loading.value = false
                } catch (t: Throwable) {
                    when (t) {
                        is Exception -> message.value = t.message.toString()
                        is HttpException -> message.value = t.message().toString()
                        else -> message.value = "Unknow Error"
                    }
                }
                loading.value = false
            }
        }
        return popular
    }

    fun getLoading(): LiveData<Boolean> = loading
    fun getStatus(): LiveData<Int> = status
    fun getMessage(): LiveData<String> = message

}