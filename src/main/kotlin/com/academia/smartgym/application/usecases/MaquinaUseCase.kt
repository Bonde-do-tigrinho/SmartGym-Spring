package com.academia.smartgym.application.usecases

import com.academia.smartgym.domain.model.Maquina
import com.academia.smartgym.domain.repository.MaquinaRepository
import org.springframework.stereotype.Service

@Service
class MaquinaUseCase(private val repository: MaquinaRepository) {
    fun listarTodas() = repository.findAll()

    fun buscarPorId(id: Long) =
        repository.findById(id) ?: throw Exception("Máquina não encontrada")

    fun salvar(maquina: Maquina) = repository.save(maquina)

    fun excluir(id: Long) = repository.deleteById(id)

    fun buscarPorNome(nome: String) =
        repository.findByNomeContainingIgnoreCase(nome)
}