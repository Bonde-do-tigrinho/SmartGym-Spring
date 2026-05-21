package com.academia.smartgym.infrastructure.persistence.mappers

import com.academia.smartgym.domain.model.ExercicioFichaTreino
import com.academia.smartgym.domain.model.FichaTreino
import com.academia.smartgym.infrastructure.persistence.entities.FichaTreinoEntity
import com.academia.smartgym.infrastructure.persistence.entities.FichaTreinoExercicioEmbeddable

class FichaTreinoMapper {
    fun FichaTreinoEntity.toDomain() = FichaTreino(
        id = id,
        alunoId = alunoId,
        exercicios = exercicios.map {
            ExercicioFichaTreino(
                exercicioId = it.exercicioId,
                series = it.series,
                repeticoes = it.repeticoes,
                descansoSegundos = it.descansoSegundos
            )
        },
        vigencia = vigencia,
        focoTreino = focoTreino
    )

    fun FichaTreino.toEntity() = FichaTreinoEntity(
        id = id,
        alunoId = alunoId,
        exercicios = exercicios.map {
            FichaTreinoExercicioEmbeddable(
                exercicioId = it.exercicioId,
                series = it.series,
                repeticoes = it.repeticoes,
                descansoSegundos = it.descansoSegundos
            )
        },
        vigencia = vigencia,
        focoTreino = focoTreino
    )
}
