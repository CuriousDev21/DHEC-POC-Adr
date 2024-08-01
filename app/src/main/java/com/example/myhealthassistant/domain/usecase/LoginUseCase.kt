package com.example.myhealthassistant.domain.usecase

import com.example.myhealthassistant.domain.model.User
import com.example.myhealthassistant.domain.repository.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(username: String, password: String): Result<User> =
        authRepository.login(username, password)
}
