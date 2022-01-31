package com.example.bestandroidcode.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bestandroidcode.common.Resource
import com.example.bestandroidcode.data.RemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : ViewModel() {

    var mMainViewState = MutableLiveData<MainViewStates>()

    fun requestRandomCatImage() {
        viewModelScope.launch {
            remoteDataSource.requestRandomCat().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val response = result.data
                        mMainViewState.value = MainViewStates(imageUrl = response?.get(0)?.url, isLoading = false)
                    }
                    is Resource.Error -> {
                        mMainViewState.value = MainViewStates(errorMessage = result.message, isLoading = false)
                    }
                    is Resource.Loading -> {
                        mMainViewState.value = MainViewStates(isLoading = true)
                    }
                }
            }
        }
    }
}