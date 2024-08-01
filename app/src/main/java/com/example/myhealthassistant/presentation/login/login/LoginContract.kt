package com.example.myhealthassistant.presentation.login.login

import androidx.compose.runtime.Composable

/**
 * UI State that represents LoginScreen
 **/
data class LoginState(
    val isLoading: Boolean = false,
    val error: String? = null
)


/**
 * Login Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class LoginActions(
    val onLogin: (String, String) -> Unit = { _, _ -> }
)

