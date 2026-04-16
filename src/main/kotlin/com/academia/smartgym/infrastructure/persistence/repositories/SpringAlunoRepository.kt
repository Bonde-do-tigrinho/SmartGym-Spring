package com.academia.smartgym.infrastructure.persistence.repositories

import com.academia.smartgym.domain.model.Aluno
import com.academia.smartgym.infrastructure.persistence.entities.AlunoEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SpringAlunoRepository : JpaRepository<AlunoEntity, Int>{
    fun findByEmail(email: String): AlunoEntity?
    fun findByCpf(cpf: String): AlunoEntity?
}