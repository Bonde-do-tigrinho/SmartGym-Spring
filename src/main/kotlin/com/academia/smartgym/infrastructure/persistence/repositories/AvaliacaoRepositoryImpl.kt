package com.academia.smartgym.infrastructure.persistence.repositories

import com.academia.smartgym.domain.model.Avaliacao
import com.academia.smartgym.domain.repository.AvaliacaoRepository
import com.academia.smartgym.infrastructure.persistence.entities.AvaliacaoEntity
import com.academia.smartgym.infrastructure.persistence.mappers.AvaliacaoMapper
import org.springframework.stereotype.Repository

@Repository
class AvaliacaoRepositoryImpl(
    private val springAvaliacaoRepository: SpringAvaliacaoRepository,
    private val mapper: AvaliacaoMapper
) : AvaliacaoRepository {

    override fun findAll(): List<Avaliacao> =
        springAvaliacaoRepository.findAll().map { it.toDomain() }

    override fun findById(id: Long): Avaliacao? =
        springAvaliacaoRepository.findById(id).map { it.toDomain() }.orElse(null)

    override fun save(avaliacao: Avaliacao): Avaliacao {
        val entity = avaliacao.toEntity()
        val saved = springAvaliacaoRepository.save(entity)
        return saved.toDomain()
    }

    override fun deleteById(id: Long) =
        springAvaliacaoRepository.deleteById(id)

    override fun findByAlunoId(alunoId: Int): List<Avaliacao> =
        springAvaliacaoRepository.findByAlunoId(alunoId).map { it.toDomain() }

    override fun findByNomeAlunoContainingIgnoreCase(nome: String): List<Avaliacao> =
        springAvaliacaoRepository.findByNomeAlunoContainingIgnoreCase(nome).map { it.toDomain() }

    override fun findByProfessorId(professorId: Int?): List<Avaliacao> =
        springAvaliacaoRepository.findByProfessorId(professorId).map { it.toDomain() }

    private fun AvaliacaoEntity.toDomain() = mapper.run { this@toDomain.toDomain() }
    private fun Avaliacao.toEntity() = mapper.run { this@toEntity.toEntity() }
}

