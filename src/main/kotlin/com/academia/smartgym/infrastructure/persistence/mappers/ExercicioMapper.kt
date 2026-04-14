package com.academia.smartgym.infrastructure.persistence.mappers

import com.academia.smartgym.domain.model.Exercicio
import com.academia.smartgym.domain.model.TipoExercicio
import com.academia.smartgym.infrastructure.persistence.entities.ExercicioEntity

class ExercicioMapper {
    fun ExercicioEntity.toDomain() = Exercicio(
        id = id,
        nome = nome,
        descricao = descricao,
        tipo = TipoExercicio.valueOf(tipo),
        maquinaId = maquinaId
    )

    fun Exercicio.toEntity() = ExercicioEntity(
        id = id,
        nome = nome,
        descricao = descricao,
        tipo = tipo.name,
        maquinaId = maquinaId
    )
}

