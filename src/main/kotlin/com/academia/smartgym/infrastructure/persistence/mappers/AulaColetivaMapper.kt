package com.academia.smartgym.infrastructure.persistence.mappers

import com.academia.smartgym.domain.model.AulaColetiva
import com.academia.smartgym.infrastructure.persistence.entities.AulaColetivaEntity
import org.springframework.stereotype.Component

@Component
class AulaColetivaMapper {
    fun AulaColetivaEntity.toDomain() = AulaColetiva(
        id = id,
        nome = nome,
        professorId = professorId,
        capacidadeMaxima = capacidadeMaxima,
        dataHoraInicio = dataHoraInicio,
        dataHoraFim = dataHoraFim
    )

    fun AulaColetiva.toEntity() = AulaColetivaEntity(
        id = id,
        nome = nome,
        professorId = professorId,
        capacidadeMaxima = capacidadeMaxima,
        dataHoraInicio = dataHoraInicio,
        dataHoraFim = dataHoraFim
    )
}