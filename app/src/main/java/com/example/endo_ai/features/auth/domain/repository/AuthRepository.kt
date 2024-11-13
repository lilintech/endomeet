package com.example.endo_ai.features.auth.domain.repository

import com.example.endo_ai.core.util.Resource
import com.example.endo_ai.features.auth.domain.model.User
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): Flow<Resource<FirebaseUser>>
    suspend fun signUp(name: String, email: String, password: String): Flow<Resource<FirebaseUser>>
    suspend fun getUserData(userId: String): Flow<Resource<User>>
    suspend fun updateUserData(user: User): Flow<Resource<User>>
    fun logOut()
}