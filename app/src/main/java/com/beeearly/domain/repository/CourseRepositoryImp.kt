package com.beeearly.domain.repository
import com.beeearly.data.Response
import com.beeearly.domain.model.Course
import com.beeearly.domain.model.User
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.app
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CourseRepositoryImp: CourseRepository {
    private val db = FirebaseDatabase.getInstance().getReference("userData")
    override suspend fun getCourses(user: User): Response<List<Course>> {
        val response = Response<List<Course>>()
        try {
            val courseIDs = db.child(user.uid).child("courses").get().await().children.map { snapshot ->
                snapshot.key!!
            }
            val courses = courseIDs.mapNotNull {courseID ->
                val dataSnapshot = db.child("courses").child(courseID).get().await()
                dataSnapshot.getValue(Course::class.java)
            }
            response.data = courses

        } catch (exception: Exception) {
            response.exception = exception
        }
        return response
    }

    override suspend fun getCourseByID(id: String): Response<Course>? {
        val response = Response<Course>()
        try {
            val snapshot = db.child("courses").child(id).get().await().children.map {snapshot ->
                snapshot.getValue(Course::class.java)
            }
        } catch (exception: Exception){
            response.exception = exception
        }
        return response

    }

    override suspend fun addCourse(course: Course) {
        db.child("courses").child(course.courseId!!).setValue(course).addOnCompleteListener {  }
    }

    override suspend fun deleteCourse(course: Course) {
        db.child("courses").child(course.courseId!!).removeValue()
    }

}