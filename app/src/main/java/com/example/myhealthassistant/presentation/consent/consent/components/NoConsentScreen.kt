package com.example.myhealthassistant.presentation.consent.consent.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myhealthassistant.presentation.consent.consent.ConsentActions

@Composable
fun NoConsentScreen(actions: ConsentActions) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("No consent found. Please grant consent to proceed.")
        Button(onClick = actions.onGrantConsent) {
            Text("Grant Consent")
        }
        Button(onClick = actions.onBack) {
            Text("Back")
        }
    }
}