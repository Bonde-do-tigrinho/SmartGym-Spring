package com.academia.smartgym.domain.model

import java.time.LocalDateTime

data class AulaColetiva(
    val id: Long? = null,
    val nome: String,
    val professorId: Long,
    val capacidadeMaxima: Int,
    val dataHoraInicio: LocalDateTime,
    val dataHoraFim: LocalDateTime
)