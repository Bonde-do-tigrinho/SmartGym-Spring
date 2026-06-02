package com.academia.smartgym.infrastructure.persistence.mappers

import com.academia.smartgym.domain.model.FichaTreino
import com.academia.smartgym.domain.model.TreinoDia
import com.academia.smartgym.domain.model.ExercicioFichaTreino
import com.academia.smartgym.infrastructure.persistence.entities.FichaTreinoEntity
import com.academia.smartgym.infrastructure.persistence.entities.TreinoDiaEntity
import com.academia.smartgym.infrastructure.persistence.entities.ExercicioFichaTreinoEntity

class FichaTreinoMapper {
    
    fun FichaTreinoEntity.toDomain() = FichaTreino(
        id = id,
        alunoId = alunoId,
        professorId = professorId,
        vigencia = vigencia,
        rotinaDias = rotinaDias.map { diaEntity ->
            TreinoDia(
                id = diaEntity.id,
                letra = diaEntity.letra,
                focoTreino = diaEntity.focoTreino,
                exercicios = diaEntity.exercicios.map { exEntity ->
                    ExercicioFichaTreino(
                        id = exEntity.id,
                        exercicioId = exEntity.exercicioId,
                        series = exEntity.series,
                        repeticoes = exEntity.repeticoes,
                        descansoSegundos = exEntity.descansoSegundos
                    )
                }
            )
        }
    )

    fun FichaTreino.toEntity() = FichaTreinoEntity(
        id = id,
        alunoId = alunoId,
        professorId = professorId,
        vigencia = vigencia,
        rotinaDias = rotinaDias.map { diaDomain ->
            TreinoDiaEntity(
                id = diaDomain.id,
                letra = diaDomain.letra,
                focoTreino = diaDomain.focoTreino,
                exercicios = diaDomain.exercicios.map { exDomain ->
                    ExercicioFichaTreinoEntity(
                        id = exDomain.id,
                        exercicioId = exDomain.exercicioId,
                        series = exDomain.series,
                        repeticoes = exDomain.repeticoes,
                        descansoSegundos = exDomain.descansoSegundos
                    )
                }.toSet()
            )
        }.toSet()
    )
}