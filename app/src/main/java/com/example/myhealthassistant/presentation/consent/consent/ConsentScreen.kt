package com.example.myhealthassistant.presentation.consent.consent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myhealthassistant.domain.model.Consent
import com.example.myhealthassistant.domain.model.ConsentCategory
import com.example.myhealthassistant.domain.model.ConsentStatus
import com.example.myhealthassistant.domain.model.fihrmodels.Coding
import com.example.myhealthassistant.domain.model.fihrmodels.Provision
import com.example.myhealthassistant.domain.model.fihrmodels.ProvisionType
import com.example.myhealthassistant.domain.model.fihrmodels.Reference
import com.example.myhealthassistant.presentation.consent.consent.components.NoConsentScreen
@Composable
fun ConsentScreen(
    state: ConsentState,
    actions: ConsentActions,
    category: ConsentCategory
) {
    Column(modifier = Modifier.padding(16.dp)) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else {
            state.consent?.let { consent ->
                Text("Consent status: ${consent.status}")
                Text("Consent category: ${consent.category.firstOrNull()?.code}")
                Text("Consent date: ${consent.dateTime}")
                Text("Provision type: ${consent.provision.type}")
                when (consent.status) {
                    ConsentStatus.ACTIVE -> {
                        Button(onClick = actions.onRevokeConsent) {
                            Text("Revoke Consent")
                        }
                    }
                    ConsentStatus.INACTIVE -> {
                        Button(onClick = actions.onGrantConsent) {
                            Text("Grant Consent")
                        }
                    }
                }
            } ?: run {
                Text("No consent information available for ${category.name}")
                Button(onClick = actions.onGrantConsent) {
                    Text("Grant Consent")
                }
            }
        }
        state.error?.let { error ->
            Text(error, color = Color.Red)
        }
        Button(onClick = actions.onBack) {
            Text("Back")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConsentScreenPreview() {
    val consent = Consent(
        id = "1",
        status = ConsentStatus.ACTIVE,
        scope = Coding("http://terminology.hl7.org/CodeSystem/consentscope", "patient-privacy",display = null),
        category = listOf(Coding("http://loinc.org", "DOCUMENT_UPLOAD", display = null)),
        patient = Reference("Patient/123"),
        dateTime = "2023-05-20T10:30:00Z",
        provision = Provision(
            type = ProvisionType.PERMIT,
            action = listOf(Coding("http://terminology.hl7.org/CodeSystem/consentaction", "access",display = null))
        )
    )

    ConsentScreen(
        state = ConsentState(consent = consent),
        actions = ConsentActions(
            onGrantConsent = {},
            onRevokeConsent = {},
            onBack = {}
        ),
        category = ConsentCategory.DOCUMENT_UPLOAD
    )
}