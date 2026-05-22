package com.academia.smartgym.domain.repository

import com.academia.smartgym.domain.model.Plano

interface PlanoRepository {
    fun findAll(): List<Plano>
    fun findById(id: Long): Plano?
    fun save(plano: Plano): Plano
    fun deleteById(id: Long)
}