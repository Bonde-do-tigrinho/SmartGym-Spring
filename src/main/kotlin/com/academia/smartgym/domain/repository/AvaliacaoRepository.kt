package com.academia.smartgym.domain.repository

import com.academia.smartgym.domain.model.Avaliacao

interface AvaliacaoRepository {
    fun findAll(): List<Avaliacao>
    fun findById(id: Long): Avaliacao?
    fun save(avaliacao: Avaliacao): Avaliacao
    fun deleteById(id: Long)
    fun findByAlunoId(alunoId: Int): List<Avaliacao>
    fun findByNomeAlunoContainingIgnoreCase(nome: String): List<Avaliacao>
    fun findByProfessorId(professorId: Int?): List<Avaliacao>
}

