package com.example.bestandroidcode.ui.fragments

import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {
    open fun addToFavoriteList(){}
    open fun removeFromFavoriteList(){}
}