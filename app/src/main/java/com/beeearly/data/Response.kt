package com.beeearly.data

data class Response<T> (
    var data: T? = null,
    var exception: Exception? = null
)