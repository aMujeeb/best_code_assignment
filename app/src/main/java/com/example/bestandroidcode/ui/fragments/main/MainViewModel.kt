package com.example.bestandroidcode.ui.fragments.main

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bestandroidcode.common.Resource
import com.example.bestandroidcode.network.RemoteDataSource
import com.example.bestandroidcode.data.db.dao.CatDao
import com.example.bestandroidcode.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val mCatDao: CatDao
) : BaseViewModel() {

    var mMainViewState = MutableLiveData<MainViewStates>()

    fun requestRandomCatImage() {
        viewModelScope.launch {
            remoteDataSource.requestRandomCat().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val response = result.data
                        mSelectedCat = result.data?.get(0)
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

    fun saveCatRecord() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var x = mSelectedCat?.let { mCatDao.insertFavouriteCat(it) }
                mMainViewState.postValue(MainViewStates(isAdded = true))
                Log.d("BestApp", "Added:$x")
            } catch (e : SQLiteConstraintException) {
                mMainViewState.postValue(MainViewStates(isAlreadySaved = true))
            }
        }
    }
}