package com.example.endo_ai.features.scan.presentation.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.endo_ai.core.util.Resource
import com.example.endo_ai.features.scan.domain.repository.ImageAnalysisRepository
import com.example.endo_ai.features.scan.presentation.event.AnalysisUiEvent
import com.example.endo_ai.features.scan.presentation.state.ImageAnalysisUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class ImageAnalyzerViewModel @Inject constructor(
    private val imageAnalysisRepository: ImageAnalysisRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(ImageAnalysisUiState())
    val uiState: StateFlow<ImageAnalysisUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<AnalysisUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    private var currentImageFile: File? = null

    fun onEvent(event: AnalysisUiEvent) {
        when (event) {
            is AnalysisUiEvent.OnImageSelected -> {
                handleImageSelection(event.imageFile)
            }

            AnalysisUiEvent.ClearError -> {
                _uiState.update { it.copy(errorMessage = "") }
            }

            AnalysisUiEvent.RetryAnalysis -> {
                currentImageFile?.let { analyzeImage(it) }
            }

            is AnalysisUiEvent.ShowError -> TODO()
        }
    }

    private fun handleImageSelection(imageUri: Uri) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }

                val file = createTempFile()

                context.contentResolver.openInputStream(imageUri)?.use { inputStream ->
                    FileOutputStream(file).use { outPutStream ->
                        inputStream.copyTo(outPutStream)
                    }
                }
                currentImageFile = file
                analyzeImage(file)

                _uiState.update { it.copy(selectedImageUri = imageUri, isLoading = false) }
            } catch (e: IOException) {
                handleError("Failed to process the selected image: ${e.localizedMessage}")
            } catch (e: Exception) {
                handleError("An unexpected error occurred: ${e.localizedMessage}")
            }
        }
    }

    private fun createTempFile(): File {
        val timeStamp = System.currentTimeMillis()
        val storageDir = context.cacheDir
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            deleteOnExit()
        }
    }

    private fun analyzeImage(imageFile: File) {
        viewModelScope.launch {
            imageAnalysisRepository.analyzeImage(imageFile)
                .collectLatest { result ->
                    when (result) {
                        is Resource.Error -> {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                errorMessage = result.message ?: "Unknown error occurred",
                                analysisResult = null,
                                selectedImageUri = null
                            )
                        }

                        is Resource.Loading -> {
                            _uiState.value = _uiState.value.copy(
                                isLoading = true,
                                errorMessage = "",
                                analysisResult = null,
                                selectedImageUri = null
                            )
                        }

                        is Resource.Success -> {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                errorMessage = "",
                                analysisResult = result.data,
                                selectedImageUri = null

                            )
                        }
                    }
                }

        }
    }

    private fun handleError(message: String) {
        _uiState.update {
            it.copy(
                isLoading = false,
                errorMessage = message,
                analysisResult = null
            )
        }
        viewModelScope.launch {
            _uiEvent.send(AnalysisUiEvent.ShowError(message))
        }
    }

    override fun onCleared() {
        super.onCleared()

        currentImageFile?.delete()
    }
}