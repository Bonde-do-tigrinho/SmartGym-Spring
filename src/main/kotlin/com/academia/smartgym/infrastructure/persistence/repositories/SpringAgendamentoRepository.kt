package com.academia.smartgym.infrastructure.persistence.repositories

import com.academia.smartgym.infrastructure.persistence.entities.AgendamentoEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface SpringAgendamentoRepository : JpaRepository<AgendamentoEntity, Long> {

    // Conta quantos agendamentos ativos existem para uma determinada aula
    fun countByAulaColetivaId(aulaColetivaId: Long): Int

    // JPQL para verificar se o aluno já tem algum agendamento em uma aula que acontece naquele dia
    @Query("""
        SELECT COUNT(a) > 0 
        FROM AgendamentoEntity a 
        JOIN AulaColetivaEntity ac ON a.aulaColetivaId = ac.id 
        WHERE a.alunoId = :alunoId 
          AND ac.dataHoraInicio BETWEEN :inicioDia AND :fimDia
    """)
    fun existeAgendamentoNoDia(
        @Param("alunoId") alunoId: Long,
        @Param("inicioDia") inicioDia: LocalDateTime,
        @Param("fimDia") fimDia: LocalDateTime
    ): Boolean

    fun findByAlunoId(alunoId: Long): List<AgendamentoEntity>
    fun findByAulaColetivaId(aulaColetivaId: Long): List<AgendamentoEntity>
}