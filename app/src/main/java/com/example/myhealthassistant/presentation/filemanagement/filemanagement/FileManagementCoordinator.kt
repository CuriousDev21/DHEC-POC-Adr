package com.example.myhealthassistant.presentation.filemanagement.filemanagement

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.myhealthassistant.domain.model.File
import org.koin.androidx.compose.koinViewModel

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class FileManagementCoordinator(
    val viewModel: FileManagementViewModel
) {
    val screenStateFlow = viewModel.stateFlow

    fun uploadFile(userId: String, file: File) {
        viewModel.uploadFile(userId, file)
    }

    fun syncFiles(userId: String) {
        viewModel.syncFiles(userId)
    }

    fun getFiles(userId: String) {
        viewModel.getFiles(userId)
    }
}

@Composable
fun rememberFileManagementCoordinator(
    viewModel: FileManagementViewModel = koinViewModel()
): FileManagementCoordinator {
    return remember(viewModel) {
        FileManagementCoordinator(viewModel = viewModel)
    }
}