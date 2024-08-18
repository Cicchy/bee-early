package com.beeearly.domain.model

import com.beeearly.presentation.util.UserRole
import java.util.UUID

data class Course(
    val courseId: String ?= UUID.randomUUID().toString(),
    val title: String ?= null,
    val members: MutableMap<String, UserRole> ?= null,
)
