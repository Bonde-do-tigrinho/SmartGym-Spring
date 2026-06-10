package com.academia.smartgym.domain.repository

import com.academia.smartgym.domain.model.Exercicio

interface ExercicioRepository {
    fun findAll(): List<Exercicio>
    fun findById(id: Int): Exercicio?
    fun save(exercicio: Exercicio): Exercicio
    fun deleteById(id: Int)
    fun findByMaquinaId(maquinaId: Int): List<Exercicio>
    fun findByNomeContainingIgnoreCase(nome: String): List<Exercicio>
}