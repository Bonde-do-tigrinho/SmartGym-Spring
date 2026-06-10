package com.academia.smartgym.infrastructure.persistence.repositories

import com.academia.smartgym.infrastructure.persistence.entities.AvaliacaoEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface SpringAvaliacaoRepository : JpaRepository<AvaliacaoEntity, Long> {
    fun findByAlunoId(alunoId: Int): List<AvaliacaoEntity>
    fun findByNomeAlunoContainingIgnoreCase(nome: String): List<AvaliacaoEntity>

    @Query(
        value = "SELECT * FROM avaliacoes WHERE professor_id = :professorId",
        nativeQuery = true
    )
    fun findByProfessorId(@Param("professorId") professorId: Int?): List<AvaliacaoEntity>
}

