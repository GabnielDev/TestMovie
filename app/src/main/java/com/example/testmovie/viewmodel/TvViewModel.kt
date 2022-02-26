package com.example.testmovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testmovie.data.ResponseTv
import com.example.testmovie.data.ResultTv
import com.example.testmovie.repository.TvRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class TvViewModel @Inject constructor(private val repository: TvRepository) : ViewModel() {

    private val loading = MutableLiveData<Boolean>()
    private val status = MutableLiveData<Int>()
    private val message = MutableLiveData<String>()

    //    val airing = MutableLiveData<ArrayList<ResultTv?>?>()
    val airing = MutableLiveData<ResponseTv?>()
    val toprated = MutableLiveData<ResponseTv?>()
    val ontheair = MutableLiveData<ResponseTv?>()
    val popular = MutableLiveData<ResponseTv?>()

    fun getAiringToday(page: Int): LiveData<ResponseTv?> {
        loading.value = true
        viewModelScope.launch {
            repository.getAiringTodayTv(page).let {
                try {
                    val data = it.body()
                    airing.value = data
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
        return airing
    }

    fun getTopRated(page: Int): LiveData<ResponseTv?> {
        loading.value = true
        viewModelScope.launch {
            repository.getTopRatedTv(page).let {
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

    fun getOntheAir(page: Int): LiveData<ResponseTv?> {
        loading.value = true
        viewModelScope.launch {
            repository.getOntheAirTv(page).let {
                try {
                    val data = it.body()
                    ontheair.value = data
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
        return ontheair
    }

    fun getPopular(page: Int): LiveData<ResponseTv?> {
        loading.value = true
        viewModelScope.launch {
            repository.getPopularTv(page).let {
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