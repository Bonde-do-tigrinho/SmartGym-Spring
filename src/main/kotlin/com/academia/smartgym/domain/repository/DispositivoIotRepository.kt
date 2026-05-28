package com.academia.smartgym.domain.repository

import com.academia.smartgym.domain.model.DispositivoIot

interface DispositivoIotRepository {
    fun findAll(): List<DispositivoIot>
    fun findById(id: String): DispositivoIot?
    fun save(dispositivoIot: DispositivoIot): DispositivoIot
    fun deleteById(id: String)
}

