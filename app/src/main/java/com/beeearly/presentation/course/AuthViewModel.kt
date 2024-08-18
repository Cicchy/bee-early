package com.beeearly.presentation.course

import com.beeearly.data.Response
import com.beeearly.domain.model.User
import com.beeearly.domain.repository.AuthRepository

class AuthViewModel(
    private val repository: AuthRepository
) {
    suspend fun signIn(email: String, password: String) {
        repository.signIn(email, password)
    }
    suspend fun createAccount(name: String, email: String, password: String) {
        repository.createAccount(name, email, password)
    }
    suspend fun getCurrentUser() {
        repository.getCurrentUser()
    }
    suspend fun getUser(uid: String) {
        repository.getUser(uid)
    }
}