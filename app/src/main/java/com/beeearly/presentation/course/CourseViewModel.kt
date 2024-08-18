package com.beeearly.presentation.course
import androidx.lifecycle.ViewModel
import com.beeearly.data.Response
import com.beeearly.domain.model.Course
import com.beeearly.domain.model.User
import com.beeearly.domain.repository.CourseRepository
import com.beeearly.presentation.util.UserRole
import com.beeearly.presentation.util.UserRole.ADMIN
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class CourseViewModel(
    private val repository: CourseRepository
): ViewModel(){
    private val db = FirebaseDatabase.getInstance().getReference("userData")
    suspend fun User.getCourses(){
        repository.getCourses(this.uid)
    }
    suspend fun getCourseByID(id: String){
        repository.getCourseByID(id)
    }
    suspend fun User.addCourse(course: Course){
        course.members?.set(this.uid, ADMIN)
        repository.addCourse(course)
    }
    suspend fun Course.deleteCourse(user: User): Response<Unit>{
        val response = Response<Unit>()
        try {
            if (this.getRole(user) == ADMIN){
                repository.deleteCourse(this)
            }else{
                response.exception = java.lang.Exception("${user.uid} does not have admin privileges")
            }
        }catch (e:Exception){
            response.exception = e
        }

        return response
    }
    suspend fun Course.getMembers(): Response<List<User>> {
        val response = Response<List<User>>()
        try {
            val uids = this.members!!.toList()
            val users = uids.mapNotNull { user ->
                db.child("userData").child(user.first).get().await().getValue(User::class.java)
            }
            response.data = users

        }catch (e: Exception){
            response.exception = e
        }
        return response
    }
    suspend fun Course.addMember(user: User): Response<Unit> {
        val response = Response<Unit>()
        try {
            this.members!![user.uid] = UserRole.MEMBER
            this.let {
                repository.addCourse(it)
            }
        }catch (e: Exception){
            response.exception = e
        }
        return response
    }
    suspend fun Course.removeMember(user: User, target: User): Response<Unit> {
        val response = Response<Unit>()
        try {
            if (this.getRole(user) == ADMIN){
                this.members!!.remove(target.uid)
                this.let {
                    repository.addCourse(it)
                }
            }else{
                response.exception = java.lang.Exception("${user.uid} does not have admin privileges")
            }
        }catch (e: Exception){
            response.exception = e
        }

        return response
    }
    suspend fun Course.setAdmin(user: User, target: User): Response<Unit>{
        val response = Response<Unit>()
        try {
            if(this.getRole(user) == ADMIN){
                this.members!![target.uid] = ADMIN
                this.let {
                    repository.addCourse(it)
                }
            }else{
                response.exception = java.lang.Exception("${user.uid} does not have admin privileges")
            }
        }catch (e: Exception){
            response.exception = e
        }
        return response
    }
    suspend fun Course.removeAdmin(user: User): Response<Unit>{
        val response = Response<Unit>()
        try {
            this.members!![user.uid] = UserRole.MEMBER
            this.let {
                repository.addCourse(it)
            }
        }catch (e: Exception){
            response.exception = e
        }
        return response
    }

    private fun Course.getRole(user: User): UserRole? {
        return this.members!![user.uid]
    }
}