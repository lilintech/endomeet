package com.example.endo_ai.features.scan.presentation.event

import android.net.Uri
import java.io.File

sealed class AnalysisUiEvent {
    data class OnImageSelected(val imageFile: Uri) : AnalysisUiEvent()
    data class ShowError(val message: String): AnalysisUiEvent()
    data object ClearError : AnalysisUiEvent()
    data object RetryAnalysis : AnalysisUiEvent()
}