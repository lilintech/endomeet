package com.example.endo_ai.features.scan.domain.repository

import com.example.endo_ai.core.util.Resource
import com.example.endo_ai.features.scan.domain.model.AnalysisResult
import kotlinx.coroutines.flow.Flow
import java.io.File

interface ImageAnalysisRepository {
    fun analyzeImage(imageFile: File): Flow<Resource<AnalysisResult>>
}