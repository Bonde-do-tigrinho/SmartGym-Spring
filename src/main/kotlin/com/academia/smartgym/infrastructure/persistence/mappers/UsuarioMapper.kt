package com.academia.smartgym.infrastructure.persistence.mappers

import com.academia.smartgym.domain.model.Usuario
import com.academia.smartgym.infrastructure.persistence.entities.UsuarioEntity

object UsuarioMapper {
    fun toDomain(entity: UsuarioEntity) = Usuario(
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
        planoValor = entity.planoValor,
        role = entity.role,
    )

    fun toEntity(domain: Usuario) = UsuarioEntity(
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
        planoValor = domain.planoValor,
        role = domain.role,
    )
}