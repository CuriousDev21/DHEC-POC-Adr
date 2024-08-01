package com.example.myhealthassistant.presentation.filemanagement.filemanagement

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhealthassistant.domain.model.File
import com.example.myhealthassistant.domain.usecase.filemanagement.GetFilesUseCase
import com.example.myhealthassistant.domain.usecase.filemanagement.SyncFilesUseCase
import com.example.myhealthassistant.domain.usecase.filemanagement.UploadFileUseCase

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FileManagementViewModel(
private val uploadFileUseCase: UploadFileUseCase,
private val getFilesUseCase: GetFilesUseCase,
private val syncFilesUseCase: SyncFilesUseCase
) : ViewModel() {
    private val _stateFlow = MutableStateFlow(FileManagementState())
    val stateFlow: StateFlow<FileManagementState> = _stateFlow.asStateFlow()

    fun getFiles(userId: String) {
        viewModelScope.launch {
            _stateFlow.update { it.copy(isLoading = true) }
            getFilesUseCase(userId)
                .onSuccess { files ->
                    _stateFlow.update { it.copy(isLoading = false, files = files) }
                }
                .onFailure { error ->
                    _stateFlow.update { it.copy(isLoading = false, error = error.message) }
                }
        }
    }

    fun uploadFile(userId: String, file: File) {
        viewModelScope.launch {
            _stateFlow.update { it.copy(isLoading = true) }
            uploadFileUseCase(userId, file)
                .onSuccess { task ->
                    _stateFlow.update { it.copy(isLoading = false, lastTask = task) }
                    getFiles(userId)
                }
                .onFailure { error ->
                    _stateFlow.update { it.copy(isLoading = false, error = error.message) }
                }
        }
    }

    fun syncFiles(userId: String) {
        viewModelScope.launch {
            _stateFlow.update { it.copy(isLoading = true) }
            syncFilesUseCase(userId)
                .onSuccess { files ->
                    _stateFlow.update { it.copy(isLoading = false, files = files) }
                }
                .onFailure { error ->
                    _stateFlow.update { it.copy(isLoading = false, error = error.message) }
                }
        }
    }
}