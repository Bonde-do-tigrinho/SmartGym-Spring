package com.academia.smartgym.domain.repository

import com.academia.smartgym.domain.model.Exercicio

interface ExercicioRepository {
    fun findAll(): List<Exercicio>
    fun findById(id: Long): Exercicio?
    fun save(exercicio: Exercicio): Exercicio
    fun deleteById(id: Long)
    fun findByMaquinaId(maquinaId: Long): List<Exercicio>
    fun findByNomeContainingIgnoreCase(nome: String): List<Exercicio>
}