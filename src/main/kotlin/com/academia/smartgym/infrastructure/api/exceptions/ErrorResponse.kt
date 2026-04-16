package com.academia.smartgym.infrastructure.api.exceptions

import java.time.LocalDateTime

data class ErrorResponse(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val status: Int,
    val message: String?,
    val path: String
)