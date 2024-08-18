package com.beeearly.domain.repository

import com.beeearly.data.Response
import com.beeearly.domain.model.User
import com.google.firebase.auth.FirebaseAuth

interface AuthRepository{
    suspend fun signIn(email: String, password: String) : Response<Unit>?
    suspend fun createAccount(name: String,email: String, password: String): Response<Unit>?
    suspend fun getCurrentUser(): Response<User>?
    suspend fun getUser(uid: String): Response<User>?
}