package com.example.endo_ai.features.scan.data.remote

import com.example.endo_ai.features.scan.domain.model.AnalysisData
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @POST("/analyze")
    suspend fun analyzeImage(
        @Part image: MultipartBody.Part
    ): Response<AnalysisData>

    companion object {
        const val BASE_URL = "https://neema.pythonanywhere.com"
    }
}