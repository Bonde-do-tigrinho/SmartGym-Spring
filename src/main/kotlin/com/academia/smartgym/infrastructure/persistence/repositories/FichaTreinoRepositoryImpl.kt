package com.academia.smartgym.infrastructure.persistence.repositories

import com.academia.smartgym.domain.model.FichaTreino
import com.academia.smartgym.domain.repository.FichaTreinoRepository
import com.academia.smartgym.infrastructure.persistence.mappers.FichaTreinoMapper
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository

@Repository
class FichaTreinoRepositoryImpl(
    private val springRepository: SpringFichaTreinoRepository,
    private val springDiaRepository: SpringTreinoDiaRepository, // 👈 Injeta o repositório de dias
    private val springExercicioRepository: SpringExercicioFichaRepository, // 👈 Injeta o de exercícios
    private val mapper: FichaTreinoMapper
) : FichaTreinoRepository {

    override fun findAll(): List<FichaTreino> =
        springRepository.findAllCompletas().map { with(mapper) { it.toDomain() } }

    override fun findById(id: Int): FichaTreino? =
        springRepository.findCompletaById(id).map { with(mapper) { it.toDomain() } }.orElse(null)

    override fun findByAlunoId(alunoId: Int?): List<FichaTreino> =
        springRepository.findByAlunoIdCompleta(alunoId).map { with(mapper) { it.toDomain() } }

    override fun findByProfessorId(professorId: Int?): List<FichaTreino> =
        springRepository.findByProfessorId(professorId).map { with(mapper) { it.toDomain() } }


    @Transactional
    override fun save(fichaTreino: FichaTreino): FichaTreino {
        if (fichaTreino.id != null && springRepository.existsById(fichaTreino.id)) {
            val fichaAntiga = springRepository.findCompletaById(fichaTreino.id).orElse(null)
            fichaAntiga?.let { f ->
                f.rotinaDias.forEach { d ->
                    springExercicioRepository.deleteAll(d.exercicios)
                }
                springDiaRepository.deleteAll(f.rotinaDias)
            }
        }

        val fichaEntity = with(mapper) { fichaTreino.toEntity() }

        val diasParaSalvar = fichaEntity.rotinaDias
        fichaEntity.rotinaDias = mutableSetOf()
        val fichaSalva = springRepository.saveAndFlush(fichaEntity)

        diasParaSalvar.forEach { dia ->
            dia.id = null
            dia.fichaTreinoId = fichaSalva.id

            val exerciciosParaSalvar = dia.exercicios
            dia.exercicios = mutableSetOf()

            val diaSalvo = springDiaRepository.saveAndFlush(dia)

            exerciciosParaSalvar.forEach { exercicio ->
                exercicio.id = null // Força inserção limpa do exercício
                exercicio.treinoDiaId = diaSalvo.id
                springExercicioRepository.saveAndFlush(exercicio)
            }
        }

        return findById(fichaSalva.id!!)!!
    }

    @Transactional
    override fun deleteById(id: Int) {
        if (springRepository.existsById(id)) {
            val ficha = springRepository.findCompletaById(id).orElse(null)
            ficha?.let { f ->
                f.rotinaDias.forEach { d ->
                    springExercicioRepository.deleteAll(d.exercicios)
                }
                springDiaRepository.deleteAll(f.rotinaDias)
            }
            springRepository.deleteById(id)
        }
    }
}