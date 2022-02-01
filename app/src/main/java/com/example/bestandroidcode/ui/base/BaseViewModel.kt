package com.example.bestandroidcode.ui.base

import androidx.lifecycle.ViewModel
import com.example.bestandroidcode.model.Cat

open class BaseViewModel : ViewModel() {
    var mSelectedCat : Cat? = null
}