package com.academia.smartgym.infrastructure.persistence.mappers

import com.academia.smartgym.domain.model.Aluno
import com.academia.smartgym.infrastructure.persistence.entities.AlunoEntity

object AlunoMapper {
    fun toDomain(entity: AlunoEntity) = Aluno(
        id = entity.id,
        nome = entity.nome,
        email = entity.email,
        telefone = entity.telefone,
        cpf = entity.cpf,
        plano = entity.plano,
        status = entity.status,
        treinoAtual = entity.treinoAtual,
        focoTreino = entity.focoTreino,
        planoVencimento = entity.planoVencimento,
        planoValor = entity.planoValor
    )

    fun toEntity(domain: Aluno) = AlunoEntity(
        id = domain.id,
        nome = domain.nome,
        email = domain.email,
        telefone = domain.telefone,
        cpf = domain.cpf,
        plano = domain.plano,
        status = domain.status,
        treinoAtual = domain.treinoAtual,
        focoTreino = domain.focoTreino,
        planoVencimento = domain.planoVencimento,
        planoValor = domain.planoValor
    )
}