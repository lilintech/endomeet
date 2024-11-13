package com.example.endo_ai.features.auth.presentation.event

import com.example.endo_ai.features.auth.presentation.viewmodel.AuthMode

sealed class AuthUiEvent {
    data class Login(val email: String, val password: String): AuthUiEvent()
    data class Signup(val name: String, val email: String, val password: String) : AuthUiEvent()
    data class SwitchAuthMode(val mode: AuthMode) : AuthUiEvent()
    data object Logout : AuthUiEvent()
    data object ClearError : AuthUiEvent()
}