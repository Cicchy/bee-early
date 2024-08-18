package com.beeearly.domain.model

import com.beeearly.presentation.util.UserRole

data class Course(
    val courseId: String ?= null,
    val title: String ?= null,
    val members: MutableMap<String, UserRole> ?= null,
)
