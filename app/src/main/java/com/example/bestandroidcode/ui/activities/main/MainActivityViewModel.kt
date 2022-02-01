package com.example.bestandroidcode.ui.activities.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bestandroidcode.data.db.dao.CatDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {
    var mIsAddedSuccessfully = MutableLiveData<Boolean>()
}