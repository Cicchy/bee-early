package com.beeearly.presentation.course
import android.media.MediaDrm.OnEventListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beeearly.data.Response
import com.beeearly.domain.model.Course
import com.beeearly.domain.model.User
import com.beeearly.domain.repository.CourseRepository
import com.beeearly.presentation.util.UserRole
import com.beeearly.presentation.util.UserRole.ADMIN
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CourseViewModel(
    private val repository: CourseRepository
): ViewModel(){
    private val db = FirebaseDatabase.getInstance().getReference("userData")

    private val _getCourseByIdStatus = MutableLiveData<Response<Course>>()
    val getCourseByIdStatus : LiveData<Response<Course>> get() = _getCourseByIdStatus

    private val _getCoursesStatus = MutableLiveData<Response<List<Course>>>()
    val getCoursesStatus : LiveData<Response<List<Course>>> get() = _getCoursesStatus

    private val _addCourseStatus = MutableLiveData<Response<Unit>>()
    val addCourseStatus : LiveData<Response<Unit>> get() = _addCourseStatus

    private val _deleteCourseStatus = MutableLiveData<Response<Unit>>()
    val deleteCourseStatus : LiveData<Response<Unit>> get() = _deleteCourseStatus

    private val _addMemberStatus = MutableLiveData<Response<Unit>>()
    val addMemberStatus : LiveData<Response<Unit>> get() = _addMemberStatus

    private val _deleteMemberStatus = MutableLiveData<Response<Unit>>()
    val deleteMemberStatus : LiveData<Response<Unit>> get() = _deleteMemberStatus

    private val _setAdminStatus = MutableLiveData<Response<Unit>>()
    val setAdminStatus : LiveData<Response<Unit>> get() = setAdminStatus

    private val _deleteAdminStatus = MutableLiveData<Response<Unit>>()
    val deleteAdminStatus : LiveData<Response<Unit>> get() = setAdminStatus

    fun getCourses(user: String): LiveData<Response<List<Course>>> {
        viewModelScope.launch {
            val response = repository.getCourses(user)
            _getCoursesStatus.postValue(response)
        }
        return getCoursesStatus
    }

    fun getCourseByID(id: String): LiveData<Response<Course>> {
        viewModelScope.launch{
            val response = repository.getCourseByID(id)
            _getCourseByIdStatus.postValue(response!!)
        }
        return  getCourseByIdStatus
    }

    suspend fun addCourse(course: Course): LiveData<Response<Unit>> {
        viewModelScope.launch {
            val response = repository.addCourse(course)
            _addCourseStatus.postValue(response!!)
        }
        return addCourseStatus
    }

    suspend fun deleteCourse(user: String, course: Course): LiveData<Response<Unit>> {
        viewModelScope.launch {
            val response = repository.deleteCourse(user, course)
            _deleteCourseStatus.postValue(response!!)
        }
        return deleteCourseStatus
    }

    suspend fun addMember(user: User, target: User, course: String): Response<Unit>? {
        TODO("Not yet implemented")
    }

    suspend fun deleteMember(user: User, target: User): Response<Unit>? {
        TODO("Not yet implemented")
    }

    suspend fun setAdmin(user: User, target: User): Response<Unit> {
        TODO("Not yet implemented")
    }

    suspend fun deleteAdmin(user: User, target: User): Response<Unit>{
         TODO("Provide the return value")
    }

    fun getRole(user: String): UserRole? {
        TODO("Not yet implemented")
    }

}