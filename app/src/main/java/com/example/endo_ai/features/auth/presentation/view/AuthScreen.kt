package com.example.endo_ai.features.auth.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardDoubleArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.endo_ai.core.sharedComposables.AuthButton
import com.example.endo_ai.core.sharedComposables.AuthTextField
import com.example.endo_ai.features.auth.presentation.event.AuthUiEvent
import com.example.endo_ai.features.auth.presentation.viewmodel.AuthMode
import com.example.endo_ai.features.auth.presentation.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onAuthSuccess: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.uiState.collectLatest { state ->
            if (state.isAuthenticated) {
                onAuthSuccess()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardDoubleArrowUp,
            contentDescription = null,
            tint = Color(255, 182, 193),
            modifier = Modifier
                .size(70.dp)
        )
        Spacer(Modifier.height(15.dp))
        Text(
            text = if (uiState.authMode == AuthMode.LOGIN) "welcome back" else "Create Account",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        if (uiState.authMode == AuthMode.SIGNUP) {
            AuthTextField(
                value = name,
                onValueChange = { name = it },
                label = "Name",
                modifier = Modifier.padding(bottom = 16.dp),
                error = if (name.isBlank()) "Name is required" else null
            )
        }

        AuthTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            keyboardType = KeyboardType.Email,
            modifier = Modifier.padding(bottom = 16.dp),
            error = if (email.isBlank()) "Email is required" else null
        )

        AuthTextField(
            value = password,
            onValueChange = { password = it },
            label = "password",
            isPassword = true,
            modifier = Modifier.padding(bottom = 24.dp),
            error = if (password.isBlank()) "password is required" else null
        )

        AuthButton(
            text = if (uiState.authMode == AuthMode.LOGIN) "Login" else "Sign Up",
            onClick = {
                if (uiState.authMode == AuthMode.LOGIN) {
                    viewModel.onEvent(AuthUiEvent.Login(email, password))
                } else {
                    viewModel.onEvent(AuthUiEvent.Signup(name, email, password))
                }
            },
            isLoading = uiState.isLoading,
            enabled = when (uiState.authMode) {
                AuthMode.LOGIN -> email.isNotBlank() && password.isNotBlank()
                AuthMode.SIGNUP -> email.isNotBlank() && password.isNotBlank() && name.isNotBlank()
            },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextButton(
            onClick = {
                viewModel.onEvent(
                    AuthUiEvent.SwitchAuthMode(
                        if (uiState.authMode == AuthMode.LOGIN) AuthMode.SIGNUP else AuthMode.LOGIN
                    )
                )
            },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = if (uiState.authMode == AuthMode.LOGIN)
                    "Don't have an account? Sign up"
                else
                    "Already have an account? Login",
                style = MaterialTheme.typography.button,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(4.dp)
            )
        }

        uiState.errorMessage?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(top = 8.dp)
            )
        }


    }
}