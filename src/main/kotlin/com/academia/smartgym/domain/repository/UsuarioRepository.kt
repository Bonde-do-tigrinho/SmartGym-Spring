package com.academia.smartgym.domain.repository

import com.academia.smartgym.domain.model.Usuario

interface UsuarioRepository {
    fun findAll(): List<Usuario>

    fun findById(id: Int): Usuario?

    fun save(usuario: Usuario): Usuario

    fun deleteById(id: Int)

    fun update(id: Int, usuario: Usuario): Usuario?

    fun findByEmail(email: String): Usuario?
    fun findByCpf(cpf: String): Usuario?
}