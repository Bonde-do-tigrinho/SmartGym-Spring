package com.academia.smartgym.infrastructure.persistence.mappers

import com.academia.smartgym.domain.model.Plano
import com.academia.smartgym.domain.model.Usuario
import com.academia.smartgym.infrastructure.persistence.entities.PlanoEntity
import com.academia.smartgym.infrastructure.persistence.entities.UsuarioEntity

object UsuarioMapper {
    
    fun toDomain(entity: UsuarioEntity): Usuario = Usuario(
        id = entity.id,
        nome = entity.nome,
        email = entity.email,
        cpf = entity.cpf,
        telefone = entity.telefone,
        senha = entity.senha,
        role = entity.role,
        plano = entity.plano?.let { planoToDomain(it) },
        planoVencimento = entity.planoVencimento,
        professorId = entity.professor?.id,
        professorNome = entity.professor?.nome,
        dataNascimento = entity.dataNascimento,
        altura = entity.altura,
        peso = entity.peso,
        dataCadastro = entity.dataCadastro,
        status = entity.status,
        emailVerificado = entity.emailVerificado
    )

    fun toEntity(domain: Usuario): UsuarioEntity = UsuarioEntity(
        id = domain.id,
        nome = domain.nome,
        email = domain.email,
        cpf = domain.cpf,
        telefone = domain.telefone,
        senha = domain.senha,
        role = domain.role,
        planoVencimento = domain.planoVencimento,
        dataNascimento = domain.dataNascimento,
        altura = domain.altura,
        peso = domain.peso,
        dataCadastro = domain.dataCadastro,
        status = domain.status,
        emailVerificado = domain.emailVerificado
    )

    fun planoToDomain(entity: PlanoEntity): Plano = Plano(
        id = entity.id,
        nome = entity.nome,
        descricao = entity.descricao,
        valor = entity.valor,
        duracaoMeses = entity.duracaoMeses,
        ativo = entity.ativo
    )
}