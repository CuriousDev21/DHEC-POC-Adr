package com.example.myhealthassistant.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.myhealthassistant.domain.model.File
import com.example.myhealthassistant.domain.model.fihrmodels.Attachment
import com.example.myhealthassistant.domain.model.fihrmodels.Coding
import com.example.myhealthassistant.domain.model.fihrmodels.Content
import com.example.myhealthassistant.domain.model.fihrmodels.Context
import com.example.myhealthassistant.domain.model.fihrmodels.DocumentReference
import com.example.myhealthassistant.domain.model.fihrmodels.Period
import com.example.myhealthassistant.domain.model.fihrmodels.Reference
import com.example.myhealthassistant.domain.model.fihrmodels.Task
import com.example.myhealthassistant.domain.model.fihrmodels.TaskStatus
import com.example.myhealthassistant.domain.repository.FileRepository
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.util.UUID

class FileRepositoryImpl : FileRepository {
    private val files = mutableMapOf<String, MutableList<DocumentReference>>()
    private val tasks = mutableListOf<Task>()

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun uploadFile(userId: String, file: File): Result<Task> {
        val binaryId = UUID.randomUUID().toString()
        val documentReferenceId = UUID.randomUUID().toString()
        val taskId = UUID.randomUUID().toString()

        val documentReference = DocumentReference(
            id = documentReferenceId,
            status = "current",
            type = Coding("http://loinc.org", "34133-9", "Summary of doctor notes"),
            subject = Reference("Patient/$userId"),
            content = listOf(
                Content(
                    Attachment(
                        contentType = "application/pdf",
                        url = "Binary/$binaryId"
                    )
                )
            ),
            context = Context(
                period = Period(
                    start = LocalDateTime.now().toString(),
                    end = null
                ),
                practiceSetting = Coding("http://snomed.info/sct", "408443003", "General medical practice")
            )
        )

        files.getOrPut(userId) { mutableListOf() }.add(documentReference)

        val task = Task(
            id = taskId,
            status = TaskStatus.IN_PROGRESS,
            intent = "order",
            code = Coding("http://hl7.org/fhir/CodeSystem/task-code", "fulfill", display = null),
            focus = Reference("DocumentReference/$documentReferenceId"),
            for_ = Reference("Patient/$userId"),
            authoredOn = LocalDateTime.now().toString(),
            lastModified = LocalDateTime.now().toString()
        )

        tasks.add(task)

        return Result.success(task)
    }

    override suspend fun getFiles(userId: String): Result<List<DocumentReference>> {
        return Result.success(files[userId] ?: emptyList())
    }

    override suspend fun syncFiles(userId: String): Result<List<DocumentReference>> {
        // Simulated sync operation
        delay(2000) // Simulate network delay
        return getFiles(userId)
    }
}