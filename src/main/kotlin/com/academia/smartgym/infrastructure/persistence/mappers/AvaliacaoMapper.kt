package com.academia.smartgym.infrastructure.persistence.mappers

import com.academia.smartgym.domain.model.Avaliacao
import com.academia.smartgym.infrastructure.persistence.entities.AvaliacaoEntity

class AvaliacaoMapper {
    fun AvaliacaoEntity.toDomain() = Avaliacao(
        id = id,
        alunoId = alunoId,
        nomeAluno = nomeAluno,
        dataAvaliacao = dataAvaliacao,
        peso = peso,
        percentualGordura = percentualGordura,
        imc = imc,
        nota = nota
    )

    fun Avaliacao.toEntity() = AvaliacaoEntity(
        id = id,
        alunoId = alunoId,
        nomeAluno = nomeAluno,
        dataAvaliacao = dataAvaliacao,
        peso = peso,
        percentualGordura = percentualGordura,
        imc = imc,
        nota = nota
    )
}

