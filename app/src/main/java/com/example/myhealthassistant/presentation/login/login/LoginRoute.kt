package com.example.myhealthassistant.presentation.login.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.example.myhealthassistant.domain.model.User

@Composable
fun LoginRoute(
    coordinator: LoginCoordinator = rememberLoginCoordinator(),
    onLoginSuccess: (User) -> Unit
) {
    val uiState by coordinator.screenStateFlow.collectAsState()
    val actions = rememberLoginActions(coordinator)

    LaunchedEffect(Unit) {
        coordinator.loginEvent.collect { user ->
            onLoginSuccess(user)
        }
    }

    LoginScreen(uiState, actions)
}

@Composable
fun rememberLoginActions(coordinator: LoginCoordinator): LoginActions {
    return remember(coordinator) {
        LoginActions(
            onLogin = { username, password ->
                coordinator.login(username, password)
            }
        )
    }
}