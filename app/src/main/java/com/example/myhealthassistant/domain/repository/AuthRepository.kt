package com.example.myhealthassistant.domain.repository

import com.example.myhealthassistant.domain.model.User

interface AuthRepository {
    suspend fun login(username: String, password: String): Result<User>
    suspend fun logout()
}