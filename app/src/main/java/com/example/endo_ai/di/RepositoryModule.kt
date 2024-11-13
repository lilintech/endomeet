package com.example.endo_ai.di

import com.example.endo_ai.features.auth.data.repository.AuthRepositoryImpl
import com.example.endo_ai.features.auth.domain.repository.AuthRepository
import com.example.endo_ai.features.community.data.repository.CommunityRepositoryImpl
import com.example.endo_ai.features.community.domain.repository.CommunityRepository
import com.example.endo_ai.features.scan.data.remote.ApiService
import com.example.endo_ai.features.scan.data.repository.ImageAnalysisRepositoryImpl
import com.example.endo_ai.features.scan.domain.repository.ImageAnalysisRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun bindImageAnalysisRepository(
        apiService: ApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): ImageAnalysisRepository {
        return ImageAnalysisRepositoryImpl(
            apiService = apiService,
            ioDispatcher = ioDispatcher
        )
    }

    @Provides
    @Singleton
    fun bindAuthRepository(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): AuthRepository {
        return AuthRepositoryImpl(
            firebaseAuth = firebaseAuth,
            firestore = firestore
        )
    }

    @Provides
    @Singleton
    fun bindCommunityRepository(
        firestore: FirebaseFirestore,
        storage: FirebaseStorage
    ): CommunityRepository {
        return CommunityRepositoryImpl(
            firestore = firestore,
            storage = storage
        )
    }
}