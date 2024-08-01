package com.example.myhealthassistant.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.myhealthassistant.TestApp
import com.example.myhealthassistant.domain.model.*
import com.example.myhealthassistant.domain.model.fihrmodels.Coding
import com.example.myhealthassistant.domain.model.fihrmodels.Provision
import com.example.myhealthassistant.domain.model.fihrmodels.ProvisionType
import com.example.myhealthassistant.domain.model.fihrmodels.Reference
import com.example.myhealthassistant.presentation.consent.consent.ConsentActions
import com.example.myhealthassistant.presentation.consent.consent.ConsentScreen
import com.example.myhealthassistant.presentation.consent.consent.ConsentState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.amshove.kluent.shouldBe
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30], application = TestApp::class)
class ConsentScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mockConsent: Consent

    @Before
    fun setUp() {
        mockConsent = Consent(
            id = "1",
            status = ConsentStatus.ACTIVE,
            scope = Coding("http://terminology.hl7.org/CodeSystem/consentscope", "patient-privacy",display = null),
            category = listOf(Coding("http://loinc.org", "DOCUMENT_UPLOAD", display = null)),
            patient = Reference("Patient/123"),
            dateTime = "2023-05-20T10:30:00Z",
            provision = Provision(
                type = ProvisionType.PERMIT,
                action = listOf(Coding("http://terminology.hl7.org/CodeSystem/consentaction", "access", display = null))
            )
        )
    }

    @Test
    fun `test loading state is displayed`() {
        composeTestRule.setContent {
            ConsentScreen(
                state = ConsentState(isLoading = true),
                actions = ConsentActions(),
                category = ConsentCategory.DOCUMENT_UPLOAD
            )
        }

        composeTestRule.onNodeWithText("Loading...").assertExists()
    }

    @Test
    fun `test consent details are displayed`() {
        composeTestRule.setContent {
            ConsentScreen(
                state = ConsentState(consent = mockConsent),
                actions = ConsentActions(),
                category = ConsentCategory.DOCUMENT_UPLOAD
            )
        }

        composeTestRule.onNodeWithText("Consent status: ACTIVE").assertExists()
        composeTestRule.onNodeWithText("Consent category: DOCUMENT_UPLOAD").assertExists()
        composeTestRule.onNodeWithText("Consent date: 2023-05-20T10:30:00Z").assertExists()
    }

    @Test
    fun `test no consent message and grant button are displayed`() {
        composeTestRule.setContent {
            ConsentScreen(
                state = ConsentState(consent = null),
                actions = ConsentActions(),
                category = ConsentCategory.DOCUMENT_UPLOAD
            )
        }

        composeTestRule.onNodeWithText("No consent information available for DOCUMENT_UPLOAD").assertExists()
        composeTestRule.onNodeWithText("Grant Consent").assertExists()
    }

    @Test
    fun `test error message is displayed`() {
        composeTestRule.setContent {
            ConsentScreen(
                state = ConsentState(error = "An error occurred"),
                actions = ConsentActions(),
                category = ConsentCategory.DOCUMENT_UPLOAD
            )
        }

        composeTestRule.onNodeWithText("An error occurred").assertExists()
    }

    @Test
    fun `test revoke consent button is displayed`() {
        composeTestRule.setContent {
            ConsentScreen(
                state = ConsentState(consent = mockConsent.copy(status = ConsentStatus.ACTIVE)),
                actions = ConsentActions(),
                category = ConsentCategory.DOCUMENT_UPLOAD
            )
        }

        composeTestRule.onNodeWithText("Revoke Consent").assertExists()
    }

    @Test
    fun `test grant consent button is displayed`() {
        composeTestRule.setContent {
            ConsentScreen(
                state = ConsentState(consent = mockConsent.copy(status = ConsentStatus.INACTIVE)),
                actions = ConsentActions(),
                category = ConsentCategory.DOCUMENT_UPLOAD
            )
        }

        composeTestRule.onNodeWithText("Grant Consent").assertExists()
    }
}