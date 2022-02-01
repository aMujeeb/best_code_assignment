package com.example.bestandroidcode.ui.fragments.main

data class MainViewStates(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val imageUrl: String? = null,
    val isAlreadySaved: Boolean = false,
    val isAdded: Boolean = false,
)
