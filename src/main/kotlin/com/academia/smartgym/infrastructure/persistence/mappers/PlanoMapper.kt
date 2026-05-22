package com.academia.smartgym.infrastructure.persistence.mappers

import com.academia.smartgym.domain.model.Plano
import com.academia.smartgym.infrastructure.persistence.entities.PlanoEntity

fun Plano.toEntity(): PlanoEntity {
    return PlanoEntity(
        id = this.id,
        nome = this.nome,
        descricao = this.descricao,
        valor = this.valor,
        duracaoMeses = this.duracaoMeses,
        ativo = this.ativo
    )
}

fun PlanoEntity.toDomain(): Plano {
    return Plano(
        id = this.id,
        nome = this.nome,
        descricao = this.descricao,
        valor = this.valor,
        duracaoMeses = this.duracaoMeses,
        ativo = this.ativo
    )
}