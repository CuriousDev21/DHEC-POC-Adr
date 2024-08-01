import com.example.myhealthassistant.dispatchers.MainDispatcherRule
import com.example.myhealthassistant.domain.model.Consent
import com.example.myhealthassistant.domain.model.ConsentCategory
import com.example.myhealthassistant.domain.model.ConsentStatus
import com.example.myhealthassistant.domain.model.fihrmodels.Coding
import com.example.myhealthassistant.domain.model.fihrmodels.Provision
import com.example.myhealthassistant.domain.model.fihrmodels.ProvisionType
import com.example.myhealthassistant.domain.model.fihrmodels.Reference
import com.example.myhealthassistant.domain.usecase.consent.GetConsentUseCase
import com.example.myhealthassistant.domain.usecase.consent.GrantConsentUseCase
import com.example.myhealthassistant.domain.usecase.consent.RevokeConsentUseCase
import com.example.myhealthassistant.presentation.consent.consent.ConsentViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class ConsentViewModelTest {


    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var getConsentUseCase: GetConsentUseCase
    private lateinit var grantConsentUseCase: GrantConsentUseCase
    private lateinit var revokeConsentUseCase: RevokeConsentUseCase
    private lateinit var viewModel: ConsentViewModel

    @Before
    fun setUp() {
        getConsentUseCase = mock()
        grantConsentUseCase = mock()
        revokeConsentUseCase = mock()
        viewModel = ConsentViewModel(
            getConsentUseCase,
            grantConsentUseCase,
            revokeConsentUseCase
        )
    }

    @Test
    fun `getConsent should update state with consent when successful`():Unit = runTest {
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
        whenever(getConsentUseCase(userId, category)).thenReturn(Result.success(expectedConsent))

        // When
        viewModel.getConsent(userId, category)
        val state = viewModel.stateFlow.first()

        // Then
        state.isLoading shouldBe false
        state.consent shouldBeEqualTo expectedConsent
        state.error.shouldBeNull()
    }

    @Test
    fun `getConsent should update state with error when failed`():Unit = runTest {
        // Given
        val userId = "user123"
        val category = ConsentCategory.DOCUMENT_UPLOAD
        val errorMessage = "Consent not found"
        whenever(getConsentUseCase(userId, category)).thenReturn(Result.failure(Exception(errorMessage)))

        // When
        viewModel.getConsent(userId, category)
        val state = viewModel.stateFlow.first()

        // Then
        state.isLoading shouldBe false
        state.consent.shouldBeNull()
        state.error shouldBeEqualTo errorMessage
    }

    @Test
    fun `grantConsent should call getConsent on success`():Unit = runTest {
        // Given
        val userId = "user123"
        val category = ConsentCategory.DOCUMENT_UPLOAD
        val expectedConsent = Result.success(Consent(
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
        ))

        whenever(grantConsentUseCase(userId, category)).thenReturn(expectedConsent)

        // When
        viewModel.grantConsent(userId, category)

        // Then
        verify(getConsentUseCase, times(1)).invoke(userId, category)
    }

    @Test
    fun `revokeConsent should call getConsent on success`():Unit = runTest {
        // Given
        val userId = "user123"
        val category = ConsentCategory.DOCUMENT_UPLOAD

        whenever(revokeConsentUseCase(userId, category)).thenReturn(Result.success(Unit))

        // When
        viewModel.revokeConsent(userId, category)

        // Then
        verify(getConsentUseCase, times(1)).invoke(userId, category)
    }
}