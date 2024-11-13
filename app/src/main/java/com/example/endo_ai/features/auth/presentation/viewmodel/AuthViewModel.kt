package com.example.endo_ai.features.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.endo_ai.core.util.Resource
import com.example.endo_ai.features.auth.domain.repository.AuthRepository
import com.example.endo_ai.features.auth.presentation.event.AuthUiEvent
import com.example.endo_ai.features.auth.presentation.state.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    init {
        authRepository.currentUser?.let { user ->
            _uiState.update {
                it.copy(
                    isAuthenticated = true,
                    currentUser = user,
                    authMode = AuthMode.LOGIN
                )
            }
        }
    }

    fun onEvent(event: AuthUiEvent) {
        when(event) {
            AuthUiEvent.ClearError -> clearError()
            is AuthUiEvent.Login -> handleLogin(event.email, event.password)
            AuthUiEvent.Logout -> handleLogOut()
            is AuthUiEvent.Signup -> handleSignup(event.name, event.email, event.password)
            is AuthUiEvent.SwitchAuthMode -> switchAuthMode(event.mode)
        }
    }

    private fun handleLogin(email: String, password: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = "") }

            authRepository.login(email, password).collect { result ->
                _uiState.update { state ->
                    when (result) {
                        is Resource.Success -> state.copy(
                            isLoading = false,
                            isAuthenticated = true,
                            currentUser = result.data,
                            errorMessage = ""
                        )

                        is Resource.Error -> state.copy(
                            isLoading = false,
                            errorMessage = result.message ?: "An unexpected error occurred"
                        )

                        is Resource.Loading -> state.copy(
                            isLoading = result.isLoading
                        )
                    }
                }
            }
        }
    }

    private fun handleSignup(name: String, email: String, password: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = "") }

            authRepository.signUp(name, email, password).collect { result ->
                _uiState.update { state ->
                    when (result) {
                        is Resource.Success -> state.copy(
                            isLoading = false,
                            isAuthenticated = true,
                            currentUser = result.data,
                            errorMessage = ""
                        )

                        is Resource.Error -> state.copy(
                            isLoading = false,
                            errorMessage = result.message ?: "An unexpected error occurred"
                        )

                        is Resource.Loading -> state.copy(
                            isLoading = result.isLoading
                        )
                    }
                }
            }
        }
    }

    private fun handleLogOut() {
        authRepository.logOut()
        _uiState.update {
            AuthUiState(authMode = AuthMode.LOGIN)
        }
    }

    private fun clearError() {
        _uiState.update { it.copy(errorMessage = "") }
    }

    private fun switchAuthMode(mode: AuthMode) {
        _uiState.update { it.copy(authMode = mode, errorMessage = "") }
    }
}