package com.academia.smartgym.infrastructure.persistence.repositories

import com.academia.smartgym.domain.model.Aluno
import com.academia.smartgym.domain.repository.AlunoRepository
import com.academia.smartgym.infrastructure.persistence.mappers.AlunoMapper
import org.springframework.stereotype.Repository

@Repository
class AlunoRepositoryImpl (
        private val springRepo: SpringAlunoRepository
): AlunoRepository{
    override fun findAll(): List<Aluno> =
        springRepo.findAll().map{ AlunoMapper.toDomain(it) }

    override fun findById(id: Long): Aluno? =
        springRepo.findById(id).map { AlunoMapper.toDomain(it) }.orElse(null)

    override fun save(aluno: Aluno): Aluno =
        AlunoMapper.toDomain(
            springRepo.save(AlunoMapper.toEntity(aluno))
        )

    override fun deleteById(id: Long) =
        springRepo.deleteById(id)

    override fun update(id: Long, aluno: Aluno): Aluno? {
        val exists = springRepo.existsById(id)
        return if (exists) {
            // Garantimos que o ID do objeto que vai ser salvo é o ID da rota
            val entityToUpdate = AlunoMapper.toEntity(aluno).copy(id = id)
            AlunoMapper.toDomain(springRepo.save(entityToUpdate))
        } else null
    }

    override fun findByEmail(email: String): Aluno? =
        springRepo.findByEmail(email)?.let { AlunoMapper.toDomain(it) }

    override fun findByCpf(cpf: String): Aluno? =
        springRepo.findByCpf(cpf)?.let { AlunoMapper.toDomain(it) }
}

