package com.example.myhealthassistant.presentation.consent.consent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.example.myhealthassistant.domain.model.ConsentCategory

@Composable
fun ConsentRoute(
    userId: String,
    category: ConsentCategory,
    coordinator: ConsentCoordinator = rememberConsentCoordinator(),
    onConsentGranted: () -> Unit,
    onBack: () -> Unit
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsState()

    // UI Actions
    val actions = rememberConsentActions(coordinator, userId, category, onConsentGranted, onBack)

    // UI Rendering
    ConsentScreen(state = uiState, actions = actions, category = category)

    // Fetch consent when the screen is first displayed
    LaunchedEffect(userId, category) {
        coordinator.viewModel.getConsent(userId, category)
    }
}

@Composable
fun rememberConsentActions(
    coordinator: ConsentCoordinator,
    userId: String,
    category: ConsentCategory,
    onConsentGranted: () -> Unit,
    onBack: () -> Unit
): ConsentActions {
    return remember(coordinator, userId, category) {
        ConsentActions(
            onGrantConsent = {
                coordinator.grantConsent(userId, category)
                onConsentGranted()
            },
            onRevokeConsent = {
                coordinator.revokeConsent(userId, category)
            },
            onBack = onBack
        )
    }
}
