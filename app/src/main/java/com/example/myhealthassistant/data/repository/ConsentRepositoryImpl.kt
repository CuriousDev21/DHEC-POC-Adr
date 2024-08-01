package com.example.myhealthassistant.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.myhealthassistant.domain.model.Consent
import com.example.myhealthassistant.domain.model.ConsentCategory
import com.example.myhealthassistant.domain.model.ConsentStatus
import com.example.myhealthassistant.domain.model.fihrmodels.Coding
import com.example.myhealthassistant.domain.model.fihrmodels.Provision
import com.example.myhealthassistant.domain.model.fihrmodels.ProvisionType
import com.example.myhealthassistant.domain.model.fihrmodels.Reference
import com.example.myhealthassistant.domain.repository.ConsentRepository
import java.time.LocalDateTime
import java.util.UUID

class ConsentRepositoryImpl : ConsentRepository {
    private val consents = mutableMapOf<Pair<String, ConsentCategory>, Consent>()

    override suspend fun getConsent(userId: String, category: ConsentCategory): Result<Consent> {
        return consents[Pair(userId, category)]?.let { Result.success(it) }
            ?: Result.failure(Exception("Consent not found"))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun grantConsent(userId: String, category: ConsentCategory): Result<Consent> {
        val consent = Consent(
            id = UUID.randomUUID().toString(),
            status = ConsentStatus.ACTIVE,
            scope = Coding("http://terminology.hl7.org/CodeSystem/consentscope", "patient-privacy", display = null),
            category = listOf(Coding("http://loinc.org", category.name, display = null)),
            patient = Reference("Patient/$userId"),
            dateTime = LocalDateTime.now().toString(),
            provision = Provision(
                type = ProvisionType.PERMIT,
                action = listOf(Coding("http://terminology.hl7.org/CodeSystem/consentaction", "access", display = null))
            )
        )
        consents[Pair(userId, category)] = consent
        return Result.success(consent)
    }

    override suspend fun revokeConsent(userId: String, category: ConsentCategory): Result<Unit> {
        consents.remove(Pair(userId, category))
        return Result.success(Unit)
    }
}