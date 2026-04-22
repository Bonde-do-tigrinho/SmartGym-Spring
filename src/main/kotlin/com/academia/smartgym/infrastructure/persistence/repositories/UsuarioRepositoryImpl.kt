package com.academia.smartgym.infrastructure.persistence.repositories

import com.academia.smartgym.domain.model.UserRole
import com.academia.smartgym.domain.model.Usuario
import com.academia.smartgym.domain.repository.UsuarioRepository
import com.academia.smartgym.infrastructure.persistence.mappers.UsuarioMapper
import org.springframework.stereotype.Repository

@Repository
class UsuarioRepositoryImpl (
        private val springRepo: SpringUsuarioRepository
): UsuarioRepository{
    override fun findAll(): List<Usuario> =
        springRepo.findAll().map{ UsuarioMapper.toDomain(it) }



    override fun findById(id: Int): Usuario? =
        springRepo.findById(id).map { UsuarioMapper.toDomain(it) }.orElse(null)

    override fun save(usuario: Usuario): Usuario =
        UsuarioMapper.toDomain(
            springRepo.save(UsuarioMapper.toEntity(usuario))
        )

    override fun deleteById(id: Int) =
        springRepo.deleteById(id)

    override fun update(id: Int, usuario: Usuario): Usuario? {
        val exists = springRepo.existsById(id)
        return if (exists) {
            val entityToUpdate = UsuarioMapper.toEntity(usuario).copy(id = id)
            UsuarioMapper.toDomain(springRepo.save(entityToUpdate))
        } else null
    }

    override fun findByEmail(email: String): Usuario? =
        springRepo.findByEmail(email)?.let { UsuarioMapper.toDomain(it) }

    override fun findByCpf(cpf: String): Usuario? =
        springRepo.findByCpf(cpf)?.let { UsuarioMapper.toDomain(it) }

    override fun findByRole(role: UserRole): List<Usuario> =
        springRepo.findByRole(role).map { UsuarioMapper.toDomain(it) }
}

