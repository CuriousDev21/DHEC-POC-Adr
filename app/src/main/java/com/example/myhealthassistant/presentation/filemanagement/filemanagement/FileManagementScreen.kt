package com.example.myhealthassistant.presentation.filemanagement.filemanagement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myhealthassistant.domain.model.fihrmodels.Attachment
import com.example.myhealthassistant.domain.model.fihrmodels.Coding
import com.example.myhealthassistant.domain.model.fihrmodels.Content
import com.example.myhealthassistant.domain.model.fihrmodels.Context
import com.example.myhealthassistant.domain.model.fihrmodels.DocumentReference
import com.example.myhealthassistant.domain.model.fihrmodels.Period
import com.example.myhealthassistant.domain.model.fihrmodels.Reference

@Composable
fun FileManagementScreen(
    state: FileManagementState,
    actions: FileManagementActions
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = actions.onUploadFile) {
            Text("Upload Medical Document")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = actions.onSyncFiles) {
            Text("Sync Medical Documents")
        }

        if (state.isLoading) {
            CircularProgressIndicator()
        }

        state.lastTask?.let { task ->
            Text("Last task: ${task.status} (ID: ${task.id})")
        }

        state.files.forEach { file ->
            Text("Document: ${file.id} (${file.type.display ?: file.type.code})")
            Text("Practice: ${file.context.practiceSetting.display ?: file.context.practiceSetting.code}")
            Text("Date: ${file.context.period.start}")
        }

        state.error?.let { error ->
            Text(error, color = Color.Red)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FileManagementScreenPreview() {
    val files = listOf(
        DocumentReference(
            id = "1",
            status = "current",
            type = Coding("http://loinc.org", "34133-9", "Summary of episode note"),
            subject = Reference("Patient/123"),
            content = listOf(
                Content(
                    Attachment(
                        contentType = "application/pdf",
                        url = "Binary/1"
                    )
                )
            ),
            context = Context(
                period = Period(
                    start = "2023-05-20T10:30:00Z",
                    end = null
                ),
                practiceSetting = Coding("http://snomed.info/sct", "408443003", "General medical practice")
            )
        )
    )

    FileManagementScreen(
        state = FileManagementState(files = files),
        actions = FileManagementActions(
            onUploadFile = {},
            onSyncFiles = {}
        )
    )
}