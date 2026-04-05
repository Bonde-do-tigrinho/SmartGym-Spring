package com.academia.smartgym.domain.repository

import com.example.smartgym.domain.model.Maquina

interface MaquinaRepository {
    fun findAll(): List<Maquina>
    fun findById(id: Long): Maquina?
    fun save(maquina: Maquina): Maquina
    fun deleteById(id: Long)
}