package com.beeearly.domain.repository

import com.beeearly.data.Response
import com.beeearly.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class AuthRepositoryImp: AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().getReference("userData")
    override suspend fun signIn(email: String, password: String): Response<Unit>? {
        val response = Response<Unit>()
        try{
            auth.createUserWithEmailAndPassword(email, password).await()
        }catch (e: Exception){
            response.exception
        }
        return response
    }
    override suspend fun createAccount(name: String, email: String, password: String): Response<Unit> {
        val response = Response<Unit>()
        try{
            val authresult = auth.createUserWithEmailAndPassword(email, password).await()
            val user = authresult.user!!
            db.child(user.uid).setValue(user::class.java).await()

        }catch (e: Exception){
            response.exception = e
        }
        return response
    }
    override suspend fun getCurrentUser(): Response<User>? {
        val response = Response<User>()
        try {
            val user = db.child(auth.currentUser!!.uid).get().await().getValue(User::class.java)
            response.data = user
        }catch (e: Exception){
            response.exception = e
        }

        return response
    }
    override suspend fun getUser(uid: String): Response<User>? {
        val response = Response<User>()
        try {
            val user = db.child(uid).get().await().getValue(User::class.java)
            response.data = user
        }catch (e: Exception){
            response.exception = e
        }

        return response
    }


}