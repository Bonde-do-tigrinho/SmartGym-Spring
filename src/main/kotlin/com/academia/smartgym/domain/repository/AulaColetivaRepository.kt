package com.academia.smartgym.domain.repository

import com.academia.smartgym.domain.model.AulaColetiva
import java.time.LocalDateTime

interface AulaColetivaRepository {
    fun save(aula: AulaColetiva): AulaColetiva
    fun findById(id: Long): AulaColetiva?

    fun buscarPorPeriodo(inicio: LocalDateTime, fim: LocalDateTime): List<AulaColetiva>

    fun deleteById(id: Long)
}