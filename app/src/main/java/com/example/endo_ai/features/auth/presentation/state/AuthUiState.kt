package com.example.endo_ai.features.auth.presentation.state

import com.example.endo_ai.features.auth.presentation.viewmodel.AuthMode
import com.google.firebase.auth.FirebaseUser

data class AuthUiState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val currentUser: FirebaseUser? = null,
    val errorMessage: String = "",
    val authMode: AuthMode = AuthMode.LOGIN
)
