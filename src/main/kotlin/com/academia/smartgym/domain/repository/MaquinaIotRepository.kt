package com.academia.smartgym.domain.repository

import com.academia.smartgym.domain.model.MaquinaIot

interface MaquinaIotRepository {
    fun findAll(): List<MaquinaIot>
    fun findById(id: String): MaquinaIot?
    fun save(maquinaIot: MaquinaIot): MaquinaIot
    fun deleteById(id: String)
    fun findByName(nome: String): List<MaquinaIot>
}