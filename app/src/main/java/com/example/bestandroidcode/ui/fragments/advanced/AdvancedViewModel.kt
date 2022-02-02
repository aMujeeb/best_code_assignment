package com.example.bestandroidcode.ui.fragments.advanced

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bestandroidcode.common.Resource
import com.example.bestandroidcode.data.RemoteDataSource
import com.example.bestandroidcode.data.db.dao.CatDao
import com.example.bestandroidcode.model.Cat
import com.example.bestandroidcode.ui.base.BaseViewModel
import com.example.bestandroidcode.ui.fragments.main.MainViewStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdvancedViewModel @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val mCatDao: CatDao
): BaseViewModel() {

    var mAdvanceViewState = MutableLiveData<AdvanceViewState>()
    var mSelectedCategory = 0
    var variableA = 0
    var variableB = 0

    fun generateQuestion() {
        variableA = (0..10).random()
        variableB = (0..10).random()
        mAdvanceViewState.postValue(AdvanceViewState(mGenerateQuestion = "${variableA} + ${variableB} = ?"))
    }

    fun setSelectedCategory(category : Int) {
        mSelectedCategory = category
    }

    fun validateQuestion(answer :String?) {
        if(!answer.isNullOrEmpty()){
            if(answer.toInt() == variableA + variableB) {
                viewModelScope.launch {
                    remoteDataSource.requestCatBasedOnCategory(mSelectedCategory).collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                val response = result.data
                                mSelectedCat = result.data?.get(0)
                                mAdvanceViewState.value = AdvanceViewState(imageUrl = response?.get(0)?.url, isLoading = false)
                                //Generate a new Question on success
                                generateQuestion()
                            }
                            is Resource.Error -> {
                                mAdvanceViewState.value = AdvanceViewState(errorMessage = result.message, isLoading = false)
                            }
                            is Resource.Loading -> {
                                mAdvanceViewState.value = AdvanceViewState(isLoading = true)
                            }

                        }
                    }
                }
            } else {
                mAdvanceViewState.value = AdvanceViewState(mIsAnswerWrong = true)
            }
        } else {
            mAdvanceViewState.value = AdvanceViewState(mIsAnswerWrong = true)
        }
    }

    fun saveCatRecord() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var x = mSelectedCat?.let { mCatDao.insertFavouriteCat(it) }
                mAdvanceViewState.postValue(AdvanceViewState(isAdded = true))
                Log.d("BestApp", "Added:$x")
            } catch (e : SQLiteConstraintException) {
                mAdvanceViewState.postValue(AdvanceViewState(isAlreadySaved = true))
            }
        }
    }
}