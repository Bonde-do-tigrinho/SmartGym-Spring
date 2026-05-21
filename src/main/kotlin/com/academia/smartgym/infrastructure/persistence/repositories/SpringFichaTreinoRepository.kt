package com.academia.smartgym.infrastructure.persistence.repositories

import com.academia.smartgym.infrastructure.persistence.entities.FichaTreinoEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SpringFichaTreinoRepository : JpaRepository<FichaTreinoEntity, Long> {
    fun findByAlunoId(alunoId: Int): List<FichaTreinoEntity>
}
