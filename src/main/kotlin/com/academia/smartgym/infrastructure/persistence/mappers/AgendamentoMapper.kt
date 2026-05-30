package com.academia.smartgym.infrastructure.persistence.mappers

import com.academia.smartgym.domain.model.Agendamento
import com.academia.smartgym.infrastructure.persistence.entities.AgendamentoEntity
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class AgendamentoMapper {
    fun AgendamentoEntity.toDomain() = Agendamento(
        id = id,
        alunoId = alunoId,
        aulaColetivaId = aulaColetivaId,
        dataAgendamento = dataAgendamento
    )

    fun Agendamento.toEntity() = AgendamentoEntity(
        id = id,
        alunoId = alunoId,
        aulaColetivaId = aulaColetivaId,
        dataAgendamento = dataAgendamento ?: LocalDateTime.now()
    )
}