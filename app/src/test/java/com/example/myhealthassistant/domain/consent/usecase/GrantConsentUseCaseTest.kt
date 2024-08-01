package com.example.myhealthassistant.domain.consent.usecase

import com.example.myhealthassistant.domain.model.Consent
import com.example.myhealthassistant.domain.model.ConsentCategory
import com.example.myhealthassistant.domain.model.ConsentStatus
import com.example.myhealthassistant.domain.model.fihrmodels.Coding
import com.example.myhealthassistant.domain.model.fihrmodels.Provision
import com.example.myhealthassistant.domain.model.fihrmodels.ProvisionType
import com.example.myhealthassistant.domain.model.fihrmodels.Reference
import com.example.myhealthassistant.domain.repository.ConsentRepository
import com.example.myhealthassistant.domain.usecase.consent.GetConsentUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before

import org.mockito.kotlin.doReturn

class GetConsentUseCaseTest {
    private lateinit var repository: ConsentRepository
    private lateinit var useCase: GetConsentUseCase

    @Before
    fun setUp() {
        repository = mock()
        useCase = GetConsentUseCase(repository)
    }

    @Test
    fun `should return consent when found`():Unit = runBlocking {
        // Given
        val userId = "user123"
        val category = ConsentCategory.DOCUMENT_UPLOAD
        val expectedConsent = Consent(
            id = "1",
            status = ConsentStatus.ACTIVE,
            scope = Coding("http://terminology.hl7.org/CodeSystem/consentscope", "patient-privacy", display = null),
            category = listOf(Coding("http://loinc.org", "DOCUMENT_UPLOAD", display = null)),
            patient = Reference("Patient/$userId"),
            dateTime = "2023-05-20T10:30:00Z",
            provision = Provision(
                type = ProvisionType.PERMIT,
                action = listOf(Coding("http://terminology.hl7.org/CodeSystem/consentaction", "access", display = null))
            )
        )
        whenever(repository.getConsent(userId, category)).doReturn(Result.success(expectedConsent))

        // When
        val result = useCase(userId, category)

        // Then
        result.isSuccess shouldBe true
        result.getOrNull() shouldBeEqualTo expectedConsent
    }

    @Test
    fun `should return failure when consent not found`(): Unit = runBlocking {
        // Arrange
        val userId = "user123"
        val category = ConsentCategory.DOCUMENT_UPLOAD
        whenever(repository.getConsent(userId, category)).doReturn(Result.failure(Exception("Consent not found")))

        // Act
        val result = useCase(userId, category)

        // Assert
        result.isFailure shouldBe true
    }
}
