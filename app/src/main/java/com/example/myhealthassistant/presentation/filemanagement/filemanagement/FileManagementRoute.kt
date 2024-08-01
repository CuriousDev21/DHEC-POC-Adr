package com.example.myhealthassistant.presentation.filemanagement.filemanagement

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.example.myhealthassistant.domain.model.File
import org.koin.androidx.compose.koinViewModel
import java.util.UUID

@Composable
fun FileManagementRoute(
    userId: String,
    viewModel: FileManagementViewModel = koinViewModel()
) {
    val state by viewModel.stateFlow.collectAsState()

    val actions = remember(viewModel) {
        FileManagementActions(
            onUploadFile = {
                // In a real app, we would have launch a native media or external storage picker  here
                val testContent = "dummy content"
                val dummyFile = File(UUID.randomUUID().toString(), "test.pdf", testContent.toByteArray())
                viewModel.uploadFile(userId, dummyFile)
            },
            onSyncFiles = {
                viewModel.syncFiles(userId)
            }
        )
    }

    LaunchedEffect(userId) {
        viewModel.getFiles(userId)
    }

    FileManagementScreen(state, actions)
}

@Composable
fun rememberFileManagementActions(
    coordinator: FileManagementCoordinator,
    userId: String
): FileManagementActions {
    return remember(coordinator, userId) {
        FileManagementActions(
            onUploadFile = {
                val dummyFile = File(UUID.randomUUID().toString(), "test.txt", "Hello, World!".toByteArray())
                coordinator.uploadFile(userId, dummyFile)
            },
            onSyncFiles = {
                coordinator.syncFiles(userId)
            }
        )
    }
}