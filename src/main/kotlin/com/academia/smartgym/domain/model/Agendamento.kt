package com.academia.smartgym.domain.model

import java.time.LocalDateTime

data class Agendamento(
    val id: Long? = null,
    val alunoId: Long,
    val aulaColetivaId: Long,
    val dataAgendamento: LocalDateTime? = null
)