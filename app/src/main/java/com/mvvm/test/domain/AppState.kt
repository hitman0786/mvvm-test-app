package com.mvvm.test.domain

sealed class AppState<out T: Any?> {
    data class Loading(var state : Boolean = false) : AppState<Boolean>()
    data class Success<out T : Any?>(val data: T) : AppState<T>()
    data class Error(val msg: String) : AppState<Nothing>()
}

