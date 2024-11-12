package com.example.endo_ai.features.scan.data.repository

import com.example.endo_ai.core.util.Resource
import com.example.endo_ai.di.IoDispatcher
import com.example.endo_ai.features.scan.data.remote.ApiService
import com.example.endo_ai.features.scan.domain.model.AnalysisData
import com.example.endo_ai.features.scan.domain.model.AnalysisResult
import com.example.endo_ai.features.scan.domain.repository.ImageAnalysisRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ImageAnalysisRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ImageAnalysisRepository {
    override fun analyzeImage(imageFile: File): Flow<Resource<AnalysisResult>> = flow {
        emit(Resource.Loading(true))

        try {
            val requestFile = MultipartBody.Part.createFormData(
                "image",
                imageFile.name,
                imageFile.asRequestBody("image/*".toMediaType())
            )
            val response = apiService.analyzeImage(requestFile)
            when {
                response.isSuccessful -> {
                    response.body()?.let { apiResponse ->
                        try {
                            val data = Json.decodeFromString<AnalysisData>(apiResponse.toString())
                            emit(Resource.Success(data.result))
                        } catch (e: SerializationException) {
                            emit(Resource.Error("Failed to parse analysis result: ${e.message}"))
                        }
                    } ?: emit(Resource.Error("Response body is null"))
                }

                else -> {
                    emit(
                        Resource.Error(
                            response.errorBody()?.string() ?: "Unknown error occurred"
                        )
                    )
                }
            }
        } catch (e: IOException) {
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
        emit(Resource.Loading(false))
    }.flowOn(ioDispatcher)
}


