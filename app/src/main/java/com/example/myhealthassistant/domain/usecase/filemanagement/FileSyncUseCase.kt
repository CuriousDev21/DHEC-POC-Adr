package com.example.myhealthassistant.domain.usecase.filemanagement

import com.example.myhealthassistant.domain.model.File
import com.example.myhealthassistant.domain.model.fihrmodels.DocumentReference
import com.example.myhealthassistant.domain.model.fihrmodels.Task
import com.example.myhealthassistant.domain.repository.FileRepository

class UploadFileUseCase(private val fileRepository: FileRepository) {
    suspend operator fun invoke(userId: String, file: File): Result<Task> =
        fileRepository.uploadFile(userId, file)
}

class GetFilesUseCase(private val fileRepository: FileRepository) {
    suspend operator fun invoke(userId: String): Result<List<DocumentReference>> =
        fileRepository.getFiles(userId)
}


class SyncFilesUseCase(private val fileRepository: FileRepository) {
    suspend operator fun invoke(userId: String): Result<List<DocumentReference>> =
        fileRepository.syncFiles(userId)
}