package com.academia.smartgym.domain.repository

import com.academia.smartgym.domain.model.Unidade

interface UnidadeRepository {
    fun findAll(): List<Unidade>
    fun findById(id: Long): Unidade?
    fun save(unidade: Unidade): Unidade
    fun deleteById(id: Long)
}