package com.academia.smartgym.domain.repository

import com.academia.smartgym.domain.model.Plano

interface PlanoRepository {
    fun findAll(): List<Plano>
    fun findById(id: Int): Plano?
    fun save(plano: Plano): Plano
    fun deleteById(id: Int)
}