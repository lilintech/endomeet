package com.example.endo_ai.features.scan.presentation.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.endo_ai.core.sharedComposables.LoadingState
import com.example.endo_ai.features.scan.domain.model.AnalysisResult
import com.example.endo_ai.features.scan.presentation.event.AnalysisUiEvent
import com.example.endo_ai.features.scan.presentation.viewmodel.ImageAnalyzerViewModel
import kotlinx.coroutines.delay

@Composable
fun ScanDiseaseScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ImageAnalyzerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedImageUrl by remember { mutableStateOf<Uri?>(null) }
    var scope = rememberCoroutineScope()
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUrl = it
            viewModel.onEvent(AnalysisUiEvent.OnImageSelected(it))
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .height(200.dp)
                .width(240.dp)
                .clickable {
                    photoPickerLauncher.launch("image/*")
                },
            backgroundColor = Color(255, 182, 193)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CloudUpload,
                    contentDescription = null,
                    tint = Color.Blue,
                    modifier = Modifier.size(70.dp)
                )
                Text(
                    text = "Upload Image"
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        AnimatedVisibility(
            visible = uiState.isLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LoadingAnimation()
        }

        AnimatedVisibility(
            visible = uiState.analysisResult != null,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ) + fadeIn(),
            exit = slideOutVertically() + fadeOut()
        ) {
            uiState.analysisResult?.let { result ->
                AnalysisResultCard(result)
            }
        }

        AnimatedVisibility(
            visible = !uiState.errorMessage.isNullOrEmpty(),
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ) + fadeIn(),
            exit = slideOutVertically() + fadeOut()
        ) {
            ErrorCard(
                errorMessage = uiState.errorMessage ?: "",
                onDismiss = { viewModel.onEvent(AnalysisUiEvent.ClearError) }
            )
        }
    }
}

@Composable
fun LoadingAnimation() {
    var rotationState by remember { mutableFloatStateOf(0f) }
    val rotation = rememberInfiniteTransition(label = "")
    val rotationAnimation = rotation.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )
    LoadingState()
}

@Composable
fun AnalysisResultCard(result: AnalysisResult) {
    var visibleCharacters by remember { mutableIntStateOf(0) }
    val resultFindings = result.findings
    val resultSummary = result.summary
    val resultRecommendation = result.recommendation

    LaunchedEffect(key1 = resultFindings, key2 = resultSummary, key3 = resultRecommendation) {
        for (i in resultFindings.indices) {
            visibleCharacters = i + 1
            delay(30)
        }

        for (i in resultSummary.indices) {
            visibleCharacters = i + 1
            delay(30)
        }
        for (i in resultRecommendation.indices) {
            visibleCharacters = i + 1
            delay(30)
        }

    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Analysis Result",
                style = MaterialTheme.typography.h6.copy(
                    color = MaterialTheme.colors.primary
                ),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = resultFindings,
                style = MaterialTheme.typography.body1
            )
            Text(
                text = "Analysis Summary",
                style = MaterialTheme.typography.h6.copy(
                    color = MaterialTheme.colors.primary
                ),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = resultSummary,
                style = MaterialTheme.typography.body1
            )
            Text(
                text = "Analysis Recommendation",
                style = MaterialTheme.typography.h6.copy(
                    color = MaterialTheme.colors.primary
                ),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = resultRecommendation,
                style = MaterialTheme.typography.body1
            )

        }
    }
}

@Composable
fun ErrorCard(
    errorMessage: String,
    onDismiss: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        backgroundColor = Color(0xFFFFEBEE),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .clickable(onClick = onDismiss),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier.size(35.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = errorMessage,
                style = MaterialTheme.typography.body1,
                color = Color.Red
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Tap to dismiss",
                style = MaterialTheme.typography.caption,
                color = Color.Gray
            )
        }
    }
}