package com.example.myhealthassistant.presentation.consent.consent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.myhealthassistant.domain.model.ConsentCategory
import org.koin.androidx.compose.koinViewModel

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class ConsentCoordinator(
    val viewModel: ConsentViewModel
) {
    val screenStateFlow = viewModel.stateFlow

    fun grantConsent(userId: String, category: ConsentCategory) {
        viewModel.grantConsent(userId, category)
    }

    fun revokeConsent(userId: String, category: ConsentCategory) {
        viewModel.revokeConsent(userId, category)
    }
}

@Composable
fun rememberConsentCoordinator(
    viewModel: ConsentViewModel = koinViewModel()
): ConsentCoordinator {
    return remember(viewModel) {
        ConsentCoordinator(viewModel = viewModel)
    }
}