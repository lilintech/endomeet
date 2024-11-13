package com.example.endo_ai.features.create

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.endo_ai.features.community.presentation.event.CommunityUiEvent
import com.example.endo_ai.features.community.presentation.viewmodel.CommunityViewModel

@Composable
fun CreateCommunityScreen(
    modifier: Modifier = Modifier,
    viewModel: CommunityViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    val (communityTitle, setCommunityTitle) = remember { mutableStateOf("") }
    val (communityDescription, setCommunityDescription) = remember { mutableStateOf("") }
    val (communityImageUri, setCommunityImageUri) = remember { mutableStateOf<Uri?>(null) }
    val (showSuccessDialog, setShowSuccessDialog) = remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> setCommunityImageUri(uri) }
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 80.dp, start = 40.dp, end = 40.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Create Community",
            style = MaterialTheme.typography.h2.copy(
                color = Color(0xFFFFB6C1)
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TitleTextField(
            value = communityTitle,
            onValueChange = setCommunityTitle
        )

        Spacer(modifier = Modifier.height(16.dp))

        DescriptionTextField(
            value = communityDescription,
            onValueChange = setCommunityDescription
        )

        Spacer(modifier = Modifier.height(16.dp))

        ImagePicker(
            imageUri = communityImageUri,
            onImagePicked = { imagePickerLauncher.launch("image/*") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CreateCommunityButton(
            onClick = {
                viewModel.onEvent(
                    CommunityUiEvent.CreateCommunity(
                        title = communityTitle,
                        description = communityDescription,
                        imageUrl = communityImageUri
                    )
                )
                setShowSuccessDialog(true)
            }
        )

        if (uiState.isLoading) {
            LoadingIndicator()
        }

        uiState.error?.let { error ->
            ErrorMessage(
                message = error
            )
        }

        SuccessDialog(
            visible = showSuccessDialog,
            onDismiss = { setShowSuccessDialog(false) },
            onConfirm = {
                setShowSuccessDialog(false)
                navController.popBackStack()
            }
        )
    }
}

@Composable
fun TitleTextField(
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Community Title") },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            backgroundColor = Color(0xFF1565C0),
            focusedIndicatorColor = Color(0xFFFFB6C1),
            unfocusedIndicatorColor = Color(0xFF90CAF9),
            cursorColor = Color(0xFFFFB6C1)
        ),
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
fun DescriptionTextField(
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Community Description") },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            backgroundColor = Color(0xFF1565C0),
            focusedIndicatorColor = Color(0xFFFFB6C1),
            unfocusedIndicatorColor = Color(0xFF90CAF9),
            cursorColor = Color(0xFFFFB6C1)
        ),
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
fun ImagePicker(
    imageUri: Uri?,
    onImagePicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onImagePicked() }
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        if (imageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = "Community Image",
                modifier = Modifier.size(100.dp)
            )
        } else {
            Icon(
                imageVector = Icons.Default.AddAPhoto,
                contentDescription = "Add Image",
                modifier = Modifier.size(50.dp),
                tint = Color(0xFFFFB6C1)
            )
        }
    }
}

@Composable
fun CreateCommunityButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFFFFB6C1),
            contentColor = Color.White
        )
    ) {
        Text("Create Community")
    }
}

@Composable
fun LoadingIndicator() {
    CircularProgressIndicator(
        modifier = Modifier.padding(top = 16.dp),
        color = Color(0xFFFFB6C1)
    )
}

@Composable
fun ErrorMessage(
    message: String
) {
    Text(
        text = message,
        color = Color.Red,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun SuccessDialog(
    visible: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically() + fadeIn(),
        exit = slideOutVertically() + fadeOut()
    ) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Community Created", color = Color(0xFFFFB6C1)) },
            text = { Text("Your community has been created successfully.", color = Color.White) },
            confirmButton = {
                Button(
                    onClick = onConfirm,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFFFB6C1),
                        contentColor = Color.White
                    )
                ) {
                    Text("OK")
                }
            },
            modifier = Modifier.padding(16.dp),
            backgroundColor = Color(0xFF1565C0)
        )
    }
}