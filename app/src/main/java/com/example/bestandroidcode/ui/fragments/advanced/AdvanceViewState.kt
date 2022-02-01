package com.example.bestandroidcode.ui.fragments.advanced

data class AdvanceViewState(
    var mIsAnswerWrong: Boolean = false,
    var mGenerateQuestion: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val imageUrl: String? = null,
    val isAlreadySaved: Boolean = false,
    val isAdded: Boolean = false,
)
