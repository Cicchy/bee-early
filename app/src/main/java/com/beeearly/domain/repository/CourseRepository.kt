package com.beeearly.domain.repository

import com.beeearly.data.Response
import com.beeearly.domain.model.Course
import com.beeearly.domain.model.User
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    // To manage courses
    suspend fun getCourses(user: User) : Response<List<Course>>
    suspend fun getCourseByID(id: String) : Response<Course>?
    suspend fun addCourse(course: Course) : Response<Unit>?
    suspend fun deleteCourse(course: Course) : Response<Unit>?
}