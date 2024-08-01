package com.example.myhealthassistant.presentation.consent.consent

import androidx.compose.runtime.Composable
import com.example.myhealthassistant.domain.model.Consent

/**
 * UI State that represents ConsentScreen
 **/
data class ConsentState(
    val isLoading: Boolean = false,
    val consent: Consent? = null,
    val error: String? = null
)
/**
 * Consent Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class ConsentActions(
    val onGrantConsent: () -> Unit = {},
    val onRevokeConsent: () -> Unit = {},
    val onBack: () -> Unit = {}
)

