package com.example.myhealthassistant.domain.model

import com.example.myhealthassistant.domain.model.fihrmodels.Coding
import com.example.myhealthassistant.domain.model.fihrmodels.Provision
import com.example.myhealthassistant.domain.model.fihrmodels.Reference

data class Consent(
    val id: String,
    val status: ConsentStatus,
    val scope: Coding,
    val category: List<Coding>,
    val patient: Reference,
    val dateTime: String,
    val provision: Provision
)
enum class ConsentStatus { ACTIVE, INACTIVE }


enum class ConsentCategory {
    DOCUMENT_UPLOAD,
    DOCUMENT_SYNC
}