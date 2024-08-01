package com.example.myhealthassistant.presentation.login.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.androidx.compose.koinViewModel

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class LoginCoordinator(
    val viewModel: LoginViewModel
) {
    val screenStateFlow = viewModel.stateFlow
    val loginEvent = viewModel.loginEvent

    fun login(username: String, password: String) {
        viewModel.login(username, password)
    }
}

@Composable
fun rememberLoginCoordinator(
    viewModel: LoginViewModel = koinViewModel()
): LoginCoordinator {
    return remember(viewModel) {
        LoginCoordinator(viewModel = viewModel)
    }
}