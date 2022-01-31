package com.example.bestandroidcode.ui.main

data class MainViewStates(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val imageUrl: String? = null
)
