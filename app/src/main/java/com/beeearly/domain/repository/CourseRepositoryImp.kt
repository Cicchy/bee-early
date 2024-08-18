package com.beeearly.domain.repository
import com.beeearly.data.Response
import com.beeearly.domain.model.Course
import com.beeearly.domain.model.User
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class CourseRepositoryImp: CourseRepository {
    private val db = FirebaseDatabase.getInstance().getReference("userData")
    override suspend fun getCourses(user: String): Response<List<Course>> {
        val response = Response<List<Course>>()
        try {
            val courseIDs = db.child(user).child("courses").get().await().children.map { snapshot ->
                snapshot.key!!
            }
            val courses = courseIDs.mapNotNull { courseID ->
                db.child("courses").child(courseID).get().await().getValue(Course::class.java)
            }
            response.data = courses

        } catch (e: Exception) {
            response.exception = e
        }
        return response
    }
    override suspend fun getCourseByID(id: String): Response<Course>? {
        val response = Response<Course>()
        try {
            val course = db.child("courses").child(id).get().await().getValue(Course::class.java)
            response.data = course
        } catch (e: Exception){
            response.exception = e
        }
        return response

    }
    override suspend fun addCourse(course: Course): Response<Unit> {
        val response = Response<Unit>()
        try {
            db.child("courses").child(course.courseId!!).setValue(course).await()
        }catch (e: Exception)
        {
            response.exception = e
        }
        return response
    }
    override suspend fun deleteCourse(course: Course): Response<Unit> {
        val response = Response<Unit>()
        try {
            db.child("courses").child(course.courseId!!).removeValue().await()
        }catch (e: Exception){
            response.exception = e
        }
        return response

        val user = User("e","e","e")
    }

}