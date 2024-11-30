package com.mkdevelopment.tmdbapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkdevelopment.tmdbapp.model.MovieDetail
import com.mkdevelopment.tmdbapp.network.ApiService
import com.mkdevelopment.tmdbapp.util.Constants.Companion.TOKEN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    private val _movieDetail = MutableLiveData<MovieDetail>()
    val movieDetail: LiveData<MovieDetail> get() = _movieDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage


    fun getMovieDetail(movieId: Int) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val result = apiService.getMovieDetail(movieId.toString(), TOKEN)
                if (result.isSuccessful) {
                    _movieDetail.postValue(result.body())
                } else {
                    if (result.message().isNullOrEmpty()) {
                        _errorMessage.value = "Bir hata oluştu"
                    } else {
                        _errorMessage.value = result.message()
                    }
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}