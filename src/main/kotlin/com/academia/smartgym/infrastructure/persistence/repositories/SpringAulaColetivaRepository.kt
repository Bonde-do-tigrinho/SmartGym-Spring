package com.academia.smartgym.infrastructure.persistence.repositories

import com.academia.smartgym.infrastructure.persistence.entities.AulaColetivaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface SpringAulaColetivaRepository : JpaRepository<AulaColetivaEntity, Long> {
    // Busca as aulas que iniciam entre o período especificado (usado para a visão semanal)
    fun findByDataHoraInicioBetween(inicio: LocalDateTime, fim: LocalDateTime): List<AulaColetivaEntity>
}