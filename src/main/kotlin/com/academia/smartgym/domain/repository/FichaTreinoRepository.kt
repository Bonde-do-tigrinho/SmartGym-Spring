package com.academia.smartgym.domain.repository

import com.academia.smartgym.domain.model.FichaTreino

interface FichaTreinoRepository {
    fun findAll(): List<FichaTreino>
    fun findById(id: Long): FichaTreino?
    fun save(fichaTreino: FichaTreino): FichaTreino
    fun deleteById(id: Long)
    fun findByAlunoId(alunoId: Int): List<FichaTreino>
}