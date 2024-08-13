package com.beeearly.presentation.course
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beeearly.domain.model.Course
import com.beeearly.domain.model.User
import com.beeearly.domain.repository.CourseRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CourseViewModel(
    private val repository: CourseRepository
): ViewModel(){
    private suspend fun getCourses(user: User?){
        repository.getCourses(user!!)
    }
    private suspend fun getCoursebyID(id: String){
        repository.getCourseByID(id)
    }

    private suspend fun addCourse(course: Course){
        repository.addCourse(course)
    }

    private suspend fun deleteCourse(course: Course){
        repository.deleteCourse(course)
    }

}