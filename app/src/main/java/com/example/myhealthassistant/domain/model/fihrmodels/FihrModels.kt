package com.example.myhealthassistant.domain.model.fihrmodels
data class DocumentReference(
    val id: String,
    val status: String,
    val type: Coding,
    val subject: Reference,
    val content: List<Content>,
    val context: Context
)

data class Content(
    val attachment: Attachment
)

data class Attachment(
    val contentType: String,
    val url: String
)

data class Context(
    val period: Period,
    val practiceSetting: Coding
)
data class Provision(
    val type: ProvisionType,
    val action: List<Coding>
)

enum class ProvisionType {
    DENY, PERMIT
}
data class Period(
    val start: String,
    val end: String?
)

data class Coding(
    val system: String,
    val code: String,
    val display: String?
)

data class Reference(
    val reference: String
)

data class Task(
    val id: String,
    val status: TaskStatus,
    val intent: String,
    val code: Coding,
    val focus: Reference,
    val for_: Reference,
    val authoredOn: String,
    val lastModified: String
)

enum class TaskStatus {
    DRAFT, REQUESTED, RECEIVED, ACCEPTED, REJECTED, READY, CANCELLED, IN_PROGRESS, ON_HOLD, FAILED, COMPLETED, ENTERED_IN_ERROR
}