package com.academia.smartgym.infrastructure.persistence.mappers

import com.academia.smartgym.domain.model.DispositivoIot
import com.academia.smartgym.infrastructure.persistence.entities.DispositivoIotEntity

fun DispositivoIot.toEntity() = DispositivoIotEntity(
    id = this.id,
    nome = this.nome,
    descricao = this.descricao,
    ativo = this.ativo,
)

fun DispositivoIotEntity.toDomain() = DispositivoIot(
    id = this.id,
    nome = this.nome,
    descricao = this.descricao,
    ativo = this.ativo,
)

