package com.example.myhealthassistant.presentation.filemanagement.filemanagement

import androidx.compose.runtime.Composable
import com.example.myhealthassistant.domain.model.File
import com.example.myhealthassistant.domain.model.fihrmodels.DocumentReference
import com.example.myhealthassistant.domain.model.fihrmodels.Task

/**
 * UI State that represents FileManagementScreen
 **/
data class FileManagementState(
    val isLoading: Boolean = false,
    val files: List<DocumentReference> = emptyList(),
    val lastTask: Task? = null,
    val error: String? = null
)
/**
 * FileManagement Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class FileManagementActions(
    val onUploadFile: () -> Unit,
    val onSyncFiles: () -> Unit
)

