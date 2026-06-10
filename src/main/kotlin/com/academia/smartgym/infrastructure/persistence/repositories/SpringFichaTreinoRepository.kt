package com.academia.smartgym.infrastructure.persistence.repositories

import com.academia.smartgym.domain.model.FichaTreino
import com.academia.smartgym.infrastructure.persistence.entities.FichaTreinoEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface SpringFichaTreinoRepository : JpaRepository<FichaTreinoEntity, Int> {
    @Query("""
        SELECT DISTINCT f FROM FichaTreinoEntity f 
        LEFT JOIN FETCH f.rotinaDias d 
        LEFT JOIN FETCH d.exercicios 
        ORDER BY f.id DESC
    """)
    fun findAllCompletas(): List<FichaTreinoEntity>

    @Query("""
        SELECT DISTINCT f FROM FichaTreinoEntity f 
        LEFT JOIN FETCH f.rotinaDias d 
        LEFT JOIN FETCH d.exercicios 
        WHERE f.id = :id
    """)
    fun findCompletaById(@Param("id") id: Int): Optional<FichaTreinoEntity>

    @Query("""
        SELECT DISTINCT f FROM FichaTreinoEntity f 
        LEFT JOIN FETCH f.rotinaDias d 
        LEFT JOIN FETCH d.exercicios 
        WHERE f.alunoId = :alunoId
        ORDER BY f.id DESC
    """)
    fun findByAlunoIdCompleta(alunoId: Int?): List<FichaTreinoEntity>

    @Query("SELECT f FROM FichaTreinoEntity f LEFT JOIN FETCH f.rotinaDias WHERE f.professorId = :professorId")
    fun findByProfessorId(@Param("professorId") professorId: Int?): List<FichaTreinoEntity>
}
