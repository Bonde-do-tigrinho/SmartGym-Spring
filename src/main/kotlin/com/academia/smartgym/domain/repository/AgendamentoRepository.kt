package com.academia.smartgym.domain.repository

import com.academia.smartgym.domain.model.Agendamento
import java.time.LocalDateTime

interface AgendamentoRepository {
    fun save(agendamento: Agendamento): Agendamento

    fun contarAgendamentosPorAula(aulaColetivaId: Long): Int

    fun existeAgendamentoDoAlunoNoPeriodo(alunoId: Long, inicioDoDia: LocalDateTime, fimDoDia: LocalDateTime): Boolean

    fun findById(id: Long): Agendamento?

    fun deleteById(id: Long)

    fun findByAlunoId(alunoId: Long): List<Agendamento>
    fun findByAulaColetivaId(aulaColetivaId: Long): List<Agendamento>
}