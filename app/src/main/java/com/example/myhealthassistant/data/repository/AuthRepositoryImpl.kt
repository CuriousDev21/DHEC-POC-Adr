package com.example.myhealthassistant.data.repository

import com.example.myhealthassistant.domain.model.User
import com.example.myhealthassistant.domain.repository.AuthRepository

class AuthRepositoryImpl : AuthRepository {
    private val users
        get() = mutableMapOf("test" to User("1", "test"))

    override suspend fun login(username: String, password: String): Result<User> =
        users[username]?.let { Result.success(it) } ?: Result.failure(Exception("Invalid credentials"))

    override suspend fun logout() { /* Simulated logout */ }
}