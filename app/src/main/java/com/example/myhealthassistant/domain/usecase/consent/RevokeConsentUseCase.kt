package com.example.myhealthassistant.domain.usecase.consent

import com.example.myhealthassistant.domain.model.ConsentCategory
import com.example.myhealthassistant.domain.repository.ConsentRepository

class RevokeConsentUseCase(private val consentRepository: ConsentRepository) {
    suspend operator fun invoke(userId: String, category: ConsentCategory): Result<Unit> =
        consentRepository.revokeConsent(userId, category)
}
