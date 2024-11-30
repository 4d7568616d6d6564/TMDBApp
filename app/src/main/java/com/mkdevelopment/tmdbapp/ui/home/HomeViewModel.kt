package com.mkdevelopment.tmdbapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkdevelopment.tmdbapp.model.MovieItem
import com.mkdevelopment.tmdbapp.network.ApiService
import com.mkdevelopment.tmdbapp.util.Constants.Companion.TOKEN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject constructor(private val apiService: ApiService) :
    ViewModel() {
    private val _movieList = MutableLiveData<List<MovieItem?>?>()
    val movieList: LiveData<List<MovieItem?>?> get() = _movieList

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading


    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun getMovieList() {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = apiService.getMovieList(TOKEN)
                if (result.isSuccessful) {
                    _movieList.postValue(result.body()?.results)
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