package com.beeearly.domain.model

data class Course(
    val courseId: String ?= null,
    val title: String ?= null,
    val authorId: String ?= null,
    val admin: MutableList<String> ?= null
)
