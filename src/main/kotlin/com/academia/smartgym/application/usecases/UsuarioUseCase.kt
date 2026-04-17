package com.academia.smartgym.application.usecases

import com.academia.smartgym.domain.model.Usuario
import com.academia.smartgym.domain.repository.UsuarioRepository
import org.springframework.stereotype.Service

@Service
class UsuarioUseCase(
    private val repository: UsuarioRepository
) {

    fun listar() = repository.findAll()

    fun buscar(id: Int) =
        repository.findById(id) ?: throw RuntimeException("Aluno não encontrado")

    fun criar(usuario: Usuario): Usuario{
        if (repository.findByEmail(usuario.email) != null) {
            throw RuntimeException("Este e-mail já está cadastrado.")
        }

        if (repository.findByCpf(usuario.cpf) != null) {
            throw RuntimeException("Este CPF já está cadastrado.")
        }
        return repository.save(usuario)
    }

    fun deletar(id: Int) = repository.deleteById(id)

    fun atualizar(id: Int, usuario: Usuario): Usuario {
        return repository.update(id, usuario)
            ?: throw RuntimeException("Aluno com id $id não encontrado para atualização")
    }
}