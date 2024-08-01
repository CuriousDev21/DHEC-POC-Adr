package com.example.myhealthassistant.domain.repository

import com.example.myhealthassistant.domain.model.Consent
import com.example.myhealthassistant.domain.model.ConsentCategory

interface ConsentRepository {
    suspend fun getConsent(userId: String, category: ConsentCategory): Result<Consent>
    suspend fun grantConsent(userId: String, category: ConsentCategory): Result<Consent>
    suspend fun revokeConsent(userId: String, category: ConsentCategory): Result<Unit>
}