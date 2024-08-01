package com.example.myhealthassistant.presentation.consent.consent

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhealthassistant.domain.model.ConsentCategory
import com.example.myhealthassistant.domain.usecase.consent.GetConsentUseCase
import com.example.myhealthassistant.domain.usecase.consent.GrantConsentUseCase
import com.example.myhealthassistant.domain.usecase.consent.RevokeConsentUseCase
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConsentViewModel(
    private val getConsentUseCase: GetConsentUseCase,
    private val grantConsentUseCase: GrantConsentUseCase,
    private val revokeConsentUseCase: RevokeConsentUseCase
) : ViewModel() {
    private val _stateFlow = MutableStateFlow(ConsentState())
    val stateFlow: StateFlow<ConsentState> = _stateFlow.asStateFlow()

    fun getConsent(userId: String, category: ConsentCategory) {
        viewModelScope.launch {
            _stateFlow.update { it.copy(isLoading = true) }
            getConsentUseCase(userId, category)
                .onSuccess { consent ->
                    _stateFlow.update { it.copy(isLoading = false, consent = consent) }
                }
                .onFailure { error ->
                    _stateFlow.update { it.copy(isLoading = false, error = error.message) }
                }
        }
    }

    fun grantConsent(userId: String, category: ConsentCategory) {
        viewModelScope.launch {
            _stateFlow.update { it.copy(isLoading = true) }
            grantConsentUseCase(userId, category)
                .onSuccess {
                    getConsent(userId, category)
                }
                .onFailure { error ->
                    _stateFlow.update { it.copy(isLoading = false, error = error.message) }
                }
        }
    }

    fun revokeConsent(userId: String, category: ConsentCategory) {
        viewModelScope.launch {
            _stateFlow.update { it.copy(isLoading = true) }
            revokeConsentUseCase(userId, category)
                .onSuccess {
                    getConsent(userId, category)
                }
                .onFailure { error ->
                    _stateFlow.update { it.copy(isLoading = false, error = error.message) }
                }
        }
    }
}