package com.beeearly.presentation.course

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beeearly.data.Response
import com.beeearly.domain.model.User
import com.beeearly.domain.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
): ViewModel() {
    private val _signInStatus = MutableLiveData<Response<Unit>>()
    val signInStatus : MutableLiveData<Response<Unit>> get() = _signInStatus

    private val _createAccountStatus  = MutableLiveData<Response<Unit>>()
    val createAccountStatus : MutableLiveData<Response<Unit>> get() = _createAccountStatus

    private val _getCurrentUserStatus = MutableLiveData<Response<User>>()
    val getCurrentUserStatus : MutableLiveData<Response<User>> get() = _getCurrentUserStatus

    private val _getUserStatus = MutableLiveData<Response<User>>()
    val getUserStatus : MutableLiveData<Response<User>> get() = _getUserStatus
    fun signIn(email: String, password: String): MutableLiveData<Response<Unit>> {
        viewModelScope.launch{
            val response= repository.signIn(email, password)
            _signInStatus.postValue(response!!)
        }
        return signInStatus
    }
     fun createAccount(name: String, email: String, password: String): MutableLiveData<Response<Unit>> {
        viewModelScope.launch {
            val response = repository.createAccount(name, email, password)
            _createAccountStatus.postValue(response!!)
        }
        return createAccountStatus
    }
    fun getCurrentUser(): MutableLiveData<Response<User>> {
        viewModelScope.launch {
            val response = repository.getCurrentUser()
            _getCurrentUserStatus.postValue(response!!)
        }
        return getCurrentUserStatus
    }
    fun getUser(uid: String): MutableLiveData<Response<User>> {
        viewModelScope.launch{
            val response = repository.getUser(uid)
            _getUserStatus.postValue(response!!)
        }
        return getUserStatus
    }
}