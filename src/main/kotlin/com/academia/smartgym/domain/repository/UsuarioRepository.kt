package com.academia.smartgym.domain.repository

import com.academia.smartgym.domain.model.UserRole
import com.academia.smartgym.domain.model.Usuario

interface UsuarioRepository {
    fun findAll(): List<Usuario>

    fun findById(id: Int?): Usuario?

    fun save(usuario: Usuario): Usuario

    fun deleteById(id: Int)

    fun update(id: Int, usuario: Usuario): Usuario?

    fun findByEmail(email: String): Usuario?
    fun findByCpf(cpf: String): Usuario?
    fun findByRole(role: UserRole): List<Usuario>

    fun findByProfessorId(professorId: Int?): List<Usuario>

    fun vincularPlano(alunoId: Int, planoId: Int, vencimento: String): Usuario?
    fun vincularProfessor(alunoId: Int, professorId: Int): Usuario?
}