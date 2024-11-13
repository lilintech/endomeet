package com.example.endo_ai.features.auth.data.repository

import com.example.endo_ai.core.util.Resource
import com.example.endo_ai.features.auth.domain.model.User
import com.example.endo_ai.features.auth.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    companion object {
        private const val USERS_COLLECTION = "users"
    }

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String): Flow<Resource<FirebaseUser>> =
        flow {
            emit(Resource.Loading(true))
            try {
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                emit(Resource.Loading(false))
                result.user?.let { user ->
                    emit(Resource.Success(user))
                } ?: emit(Resource.Error("User not found"))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }

    override suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): Flow<Resource<FirebaseUser>> = flow {
        emit(Resource.Loading(true))
        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()

            result.user?.let { firebaseUser ->
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build()
                firebaseUser.updateProfile(profileUpdates).await()

                val user = User(
                    uid = firebaseUser.uid,
                    name = name,
                    email = email
                )
                firestore.collection(USERS_COLLECTION)
                    .document(firebaseUser.uid)
                    .set(user.toMap())
                    .await()
                emit(Resource.Loading(false))
                emit(Resource.Success(firebaseUser))
            } ?: emit(Resource.Error("Failed to create user"))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    override suspend fun getUserData(userId: String): Flow<Resource<User>> = flow {
        emit(Resource.Loading(true))
        try {
            val documentSnapshot = firestore.collection(USERS_COLLECTION)
                .document(userId)
                .get()
                .await()
            val user = documentSnapshot.toObject(User::class.java)

            if (user != null) {
                emit(Resource.Success(user))
            } else {
                emit(Resource.Error("User not found"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } finally {
            emit(Resource.Loading(false))
        }
    }

    override suspend fun updateUserData(user: User): Flow<Resource<User>> = flow {
        emit(Resource.Loading(true))
        try {
            val updates = user.copy(updateAt = System.currentTimeMillis())
            firestore.collection(USERS_COLLECTION)
                .document(user.uid)
                .set(updates.toMap())
                .await()

            emit(Resource.Success(updates))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } finally {
            emit(Resource.Loading(false))
        }
    }

    override fun logOut() {
        firebaseAuth.signOut()
    }

}