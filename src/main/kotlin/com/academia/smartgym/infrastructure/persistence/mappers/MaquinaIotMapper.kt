package com.academia.smartgym.infrastructure.persistence.mappers

import com.academia.smartgym.domain.model.MaquinaIot
import com.academia.smartgym.infrastructure.persistence.entities.MaquinaIotEntity

fun MaquinaIot.toEntity() = MaquinaIotEntity(
    id = this.id!!,
    nome = this.nome,
    localizacao = this.localizacao,
    status = this.status
)

fun MaquinaIotEntity.toDomain() = MaquinaIot(
    id = this.id,
    nome = this.nome,
    localizacao = this.localizacao,
    status = this.status
)