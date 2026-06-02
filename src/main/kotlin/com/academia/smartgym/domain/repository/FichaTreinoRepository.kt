package com.academia.smartgym.domain.repository

import com.academia.smartgym.domain.model.FichaTreino

interface FichaTreinoRepository {
    fun findAll(): List<FichaTreino>
    fun findById(id: Int): FichaTreino?
    fun save(fichaTreino: FichaTreino): FichaTreino
    fun deleteById(id: Int)
    fun findByAlunoId(alunoId: Int?): List<FichaTreino>
    fun findByProfessorId(professorId: Int?): List<FichaTreino>
}