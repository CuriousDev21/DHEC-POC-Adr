package com.example.myhealthassistant.presentation.login.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhealthassistant.domain.model.User
import com.example.myhealthassistant.domain.usecase.LoginUseCase

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _stateFlow = MutableStateFlow(LoginState())
    val stateFlow: StateFlow<LoginState> = _stateFlow.asStateFlow()

    private val _loginEvent = MutableSharedFlow<User>()
    val loginEvent: SharedFlow<User> = _loginEvent.asSharedFlow()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _stateFlow.update { it.copy(isLoading = true, error = null) }
            loginUseCase(username, password)
                .onSuccess { user ->
                    _stateFlow.update { it.copy(isLoading = false) }
                    _loginEvent.emit(user)
                }
                .onFailure { error ->
                    _stateFlow.update { it.copy(isLoading = false, error = error.message) }
                }
        }
    }
}
