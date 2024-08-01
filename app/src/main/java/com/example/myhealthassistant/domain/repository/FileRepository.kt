package com.example.myhealthassistant.domain.repository

import com.example.myhealthassistant.domain.model.File
import com.example.myhealthassistant.domain.model.fihrmodels.DocumentReference
import com.example.myhealthassistant.domain.model.fihrmodels.Task

interface FileRepository {
    suspend fun uploadFile(userId: String, file: File): Result<Task>
    suspend fun getFiles(userId: String): Result<List<DocumentReference>>
    suspend fun syncFiles(userId: String): Result<List<DocumentReference>>
}