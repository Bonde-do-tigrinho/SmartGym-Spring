package com.academia.smartgym.infrastructure.persistence.repositories

import com.academia.smartgym.domain.model.UserRole
import com.academia.smartgym.domain.model.Usuario
import com.academia.smartgym.domain.repository.UsuarioRepository
import com.academia.smartgym.infrastructure.persistence.entities.UsuarioEntity
import com.academia.smartgym.infrastructure.persistence.mappers.UsuarioMapper
import org.springframework.stereotype.Repository

@Repository
class UsuarioRepositoryImpl(
    private val springRepo: SpringUsuarioRepository,
    private val springPlanoRepo: SpringPlanoRepository
) : UsuarioRepository {

    override fun findAll(): List<Usuario> =
        springRepo.findAll().map { UsuarioMapper.toDomain(it) }

    override fun findById(id: Int?): Usuario? {
        return id?.let { nonNullId ->
            springRepo.findById(nonNullId)
                .map { UsuarioMapper.toDomain(it) }
                .orElse(null)
        }
    }

    override fun findByEmail(email: String): Usuario? =
        springRepo.findByEmail(email)?.let { UsuarioMapper.toDomain(it) }

    override fun findByCpf(cpf: String): Usuario? =
        springRepo.findByCpf(cpf)?.let { UsuarioMapper.toDomain(it) }

    override fun findByRole(role: UserRole): List<Usuario> =
        springRepo.findByRole(role).map { UsuarioMapper.toDomain(it) }

    override fun save(usuario: Usuario): Usuario {
        val entity = resolverRelacionamentos(usuario)
        return UsuarioMapper.toDomain(springRepo.save(entity))
    }

    override fun update(id: Int, usuario: Usuario): Usuario? {
        if (!springRepo.existsById(id)) return null
        val entity = resolverRelacionamentos(usuario).copy(id = id)
        return UsuarioMapper.toDomain(springRepo.save(entity))
    }

    override fun deleteById(id: Int) =
        springRepo.deleteById(id)

    private fun resolverRelacionamentos(usuario: Usuario): UsuarioEntity {
        val planoEntity = usuario.plano?.id?.let {
            springPlanoRepo.findById(it).orElse(null)
        }
        val professorEntity = usuario.professorId?.let {
            springRepo.findById(it).orElse(null)
        }
        return UsuarioMapper.toEntity(usuario).copy(
            plano = planoEntity,
            professor = professorEntity
        )
    }

    override fun vincularPlano(alunoId: Int, planoId: Int, vencimento: String): Usuario? {
        val aluno = springRepo.findById(alunoId).orElse(null) ?: return null
        val plano = springPlanoRepo.findById(planoId).orElse(null) ?: return null
        val atualizado = aluno.copy(plano = plano, planoVencimento = vencimento)
        return UsuarioMapper.toDomain(springRepo.save(atualizado))
    }

    override fun vincularProfessor(alunoId: Int, professorId: Int): Usuario? {
        val aluno = springRepo.findById(alunoId).orElse(null) ?: return null
        val professor = springRepo.findById(professorId).orElse(null) ?: return null
        val atualizado = aluno.copy(professor = professor)
        return UsuarioMapper.toDomain(springRepo.save(atualizado))
    }

    override fun findByProfessorId(professorId: Int?): List<Usuario> {
        val entities = springRepo.findByProfessorId(professorId)

        return entities.map{ UsuarioMapper.toDomain(it)} }
    }
