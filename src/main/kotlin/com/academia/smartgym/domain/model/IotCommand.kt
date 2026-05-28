package com.academia.smartgym.domain.model

import jakarta.validation.constraints.NotBlank

data class IotCommand(
    @field:NotBlank(message = "O comando e obrigatorio")
    val command: String,
    val value: String? = null
)

