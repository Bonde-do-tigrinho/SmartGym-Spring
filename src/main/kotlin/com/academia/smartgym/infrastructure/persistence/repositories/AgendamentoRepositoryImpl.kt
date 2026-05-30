package com.academia.smartgym.infrastructure.persistence.repositories

import com.academia.smartgym.domain.model.Agendamento
import com.academia.smartgym.domain.repository.AgendamentoRepository
import com.academia.smartgym.infrastructure.persistence.entities.AgendamentoEntity
import com.academia.smartgym.infrastructure.persistence.mappers.AgendamentoMapper
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class AgendamentoRepositoryImpl(
    private val springRepository: SpringAgendamentoRepository,
    private val mapper: AgendamentoMapper
) : AgendamentoRepository {

    override fun save(agendamento: Agendamento): Agendamento {
        val entity = agendamento.toEntity()
        return springRepository.save(entity).toDomain()
    }

    override fun contarAgendamentosPorAula(aulaColetivaId: Long): Int {
        return springRepository.countByAulaColetivaId(aulaColetivaId)
    }

    override fun existeAgendamentoDoAlunoNoPeriodo(
        alunoId: Long,
        inicioDoDia: LocalDateTime,
        fimDoDia: LocalDateTime
    ): Boolean {
        return springRepository.existeAgendamentoNoDia(alunoId, inicioDoDia, fimDoDia)
    }
    override fun findById(id: Long): Agendamento? = springRepository.findById(id).map { it.toDomain() }.orElse(null)
    
    override fun deleteById(id: Long) = springRepository.deleteById(id)

    override fun findByAlunoId(alunoId: Long): List<Agendamento> {
        return springRepository.findByAlunoId(alunoId).map { it.toDomain() }
    }

    override fun findByAulaColetivaId(aulaColetivaId: Long): List<Agendamento> {
        return springRepository.findByAulaColetivaId(aulaColetivaId).map { it.toDomain() }
    }
    private fun AgendamentoEntity.toDomain() = mapper.run { this@toDomain.toDomain() }
    private fun Agendamento.toEntity() = mapper.run { this@toEntity.toEntity() }

}