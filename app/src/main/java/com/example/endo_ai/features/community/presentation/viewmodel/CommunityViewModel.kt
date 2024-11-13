package com.example.endo_ai.features.community.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.endo_ai.core.util.Resource
import com.example.endo_ai.features.auth.domain.repository.AuthRepository
import com.example.endo_ai.features.community.domain.model.Community
import com.example.endo_ai.features.community.domain.repository.CommunityRepository
import com.example.endo_ai.features.community.presentation.event.CommunityUiEvent
import com.example.endo_ai.features.community.presentation.state.CommunityState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val repository: CommunityRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CommunityState())
    val uiState: StateFlow<CommunityState> = _uiState.asStateFlow()


    init {
        getAllCommunities()
    }

    fun onEvent(event: CommunityUiEvent) {
        when (event) {
            is CommunityUiEvent.CreateCommunity -> createCommunity(
                event.title,
                event.description,
                event.imageUrl
            )

            is CommunityUiEvent.JoinCommunity -> joinCommunity(event.communityId)
            is CommunityUiEvent.LeaveCommunity -> leaveCommunity(event.communityId)
            CommunityUiEvent.ClearError -> clearError()
        }
    }

    private fun createCommunity(title: String, description: String, imageUri: Uri?) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val currentUser = authRepository.currentUser
                if (currentUser == null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "User must be logged in to create a community"
                        )
                    }
                    return@launch
                }
                val community = Community(
                    title = title,
                    description = description,
                    creatorId = currentUser.uid,
                    creatorName = currentUser.displayName ?: "Anonymous",
                    members = listOf(currentUser.uid)
                )

                when (val result = repository.createCommunity(community)) {
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = null,
                                successMessage = "Community created successfully"
                            )
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error occurred"
                    )
                }
            }
        }
    }

    private fun getAllCommunities() {
        viewModelScope.launch {
            repository.getAllCommunities().collect { result ->
                _uiState.update { state ->
                    when (result) {
                        is Resource.Success -> state.copy(
                            communities = result.data ?: emptyList(),
                            isLoading = false
                        )

                        is Resource.Error -> state.copy(
                            error = result.message,
                            isLoading = false
                        )

                        is Resource.Loading -> state.copy(isLoading = true)
                    }
                }
            }
        }
    }

    private fun joinCommunity(communityId: String) {
        viewModelScope.launch {
            val userId = authRepository.currentUser?.uid ?: return@launch
            when (val result = repository.joinCommunity(communityId, userId)) {
                is Resource.Success -> {
                    _uiState.update { it.copy(successMessage = "Joined community successfully") }
                }

                is Resource.Error -> {
                    _uiState.update { it.copy(error = result.message) }
                }

                is Resource.Loading -> {
                    _uiState.update { it.copy(isLoading = result.isLoading) }

                }
            }
        }
    }

    private fun leaveCommunity(communityId: String) = viewModelScope.launch {
        val userId = authRepository.currentUser?.uid ?: return@launch
        when (val result = repository.leaveCommunity(communityId, userId)) {
            is Resource.Success -> {
                _uiState.update { it.copy(successMessage = "Left community successfully") }
            }

            is Resource.Error -> {
                _uiState.update { it.copy(error = result.message) }
            }

            is Resource.Loading -> {
                _uiState.update { it.copy(isLoading = result.isLoading) }

            }
        }
    }

    private fun clearError() {
        _uiState.update { it.copy(error = null, successMessage = null) }
    }

}