package com.academia.smartgym.infrastructure.persistence.repositories

import com.academia.smartgym.infrastructure.persistence.entities.UsuarioEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SpringUsuarioRepository : JpaRepository<UsuarioEntity, Int>{
    fun findByEmail(email: String): UsuarioEntity?
    fun findByCpf(cpf: String): UsuarioEntity?
}