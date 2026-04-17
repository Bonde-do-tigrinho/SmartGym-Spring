package com.academia.smartgym.domain.repository

import com.academia.smartgym.domain.model.Aluno

interface AlunoRepository {
    fun findAll(): List<Aluno>

    fun findById(id: Int): Aluno?

    fun save(aluno: Aluno): Aluno

    fun deleteById(id: Int)

    fun update(id: Int, aluno: Aluno): Aluno?

    fun findByEmail(email: String): Aluno?
    fun findByCpf(cpf: String): Aluno?
}