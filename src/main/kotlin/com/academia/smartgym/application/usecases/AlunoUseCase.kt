package com.academia.smartgym.application.usecases

import com.academia.smartgym.domain.model.Aluno
import com.academia.smartgym.domain.repository.AlunoRepository
import org.springframework.stereotype.Service

@Service
class AlunoUseCase(
    private val repository: AlunoRepository
) {

    fun listar() = repository.findAll()

    fun buscar(id: Long) =
        repository.findById(id) ?: throw RuntimeException("Aluno não encontrado")

    fun criar(aluno: Aluno): Aluno{
        if (repository.findByEmail(aluno.email) != null) {
            throw RuntimeException("Este e-mail já está cadastrado.")
        }

        if (repository.findByCpf(aluno.cpf) != null) {
            throw RuntimeException("Este CPF já está cadastrado.")
        }
        return repository.save(aluno)
    }

    fun deletar(id: Long) = repository.deleteById(id)

    fun atualizar(id: Long, aluno: Aluno): Aluno {
        return repository.update(id, aluno)
            ?: throw RuntimeException("Aluno com id $id não encontrado para atualização")
    }
}