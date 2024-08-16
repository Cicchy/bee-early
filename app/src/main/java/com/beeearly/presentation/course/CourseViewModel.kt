package com.beeearly.presentation.course
import androidx.lifecycle.ViewModel
import com.beeearly.data.Response
import com.beeearly.domain.model.Course
import com.beeearly.domain.model.User
import com.beeearly.domain.repository.CourseRepository
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class CourseViewModel(
    private val repository: CourseRepository
): ViewModel(){
    private val db = FirebaseDatabase.getInstance().getReference("userData")
    suspend fun getCourses(user: User){
        repository.getCourses(user.uid)
    }
    suspend fun getCourseByID(id: String){
        repository.getCourseByID(id)
    }

    suspend fun addCourse(course: Course){
        repository.addCourse(course)
    }
    suspend fun deleteCourse(course: Course){
        repository.deleteCourse(course)
    }
    suspend fun Course.getMembers(): Response<List<User>> {
        val course = repository.getCourseByID(this.courseId!!)
        val response = Response<List<User>>()
        if(course!!.data != null){
            val uids = course!!.data!!.members!!.toList()
            val users = uids.mapNotNull { userID ->
                db.child("userData").child(userID).get().await().getValue(User::class.java)
            }
            response.data = users
        }else{
            response.exception  = java.lang.Exception("Unable to retrive $courseId")
        }
        return response
    }
    suspend fun Course.addMember(user: User): Response<Unit> {
        val response = Response<Unit>()
        val course = repository.getCourseByID(this.courseId!!)
        try {
            course!!.data!!.members!!.add(user.uid)
            course.data?.let {
                repository.addCourse(it)
            }
        }catch (e: Exception){
            response.exception = e
        }
        return response
    }
    suspend fun Course.removeMember(user: User): Response<Unit> {
        val response = Response<Unit>()
        val course = repository.getCourseByID(this.courseId!!)
        try {
            course!!.data!!.members!!.remove(user.uid)
            course.data?.let {
                repository.addCourse(it)
            }
        }catch (e: Exception){
            response.exception = e
        }
        return response
    }
    suspend fun Course.setAdmin(user: User): Response<Unit>{
        val response = Response<Unit>()
        val course = repository.getCourseByID(this.courseId!!)
        try {
            course!!.data!!.admin!!.add(user.uid)
            course.data?.let {
                repository.addCourse(it)
            }
        }catch (e: Exception){
            response.exception = e
        }
        return response
    }
    suspend fun Course.removeAdmin(user: User): Response<Unit>{
        val response = Response<Unit>()
        val course = repository.getCourseByID(this.courseId!!)
        try {
            course!!.data!!.admin!!.remove(user.uid)
            course.data?.let {
                repository.addCourse(it)
            }
        }catch (e: Exception){
            response.exception = e
        }
        return response
    }
}