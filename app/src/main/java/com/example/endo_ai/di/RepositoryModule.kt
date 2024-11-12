package com.example.endo_ai.di

import com.example.endo_ai.features.scan.data.remote.ApiService
import com.example.endo_ai.features.scan.data.repository.ImageAnalysisRepositoryImpl
import com.example.endo_ai.features.scan.domain.repository.ImageAnalysisRepository
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
}