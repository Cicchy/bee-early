package com.beeearly.data

data class Response<T> (
    var data: T? = null,
    var exception: Exception? = null
){
    val isSuccessful: Boolean
        get() = exception == null && data != null
}