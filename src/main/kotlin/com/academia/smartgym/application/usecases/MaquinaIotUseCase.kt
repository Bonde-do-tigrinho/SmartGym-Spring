package com.academia.smartgym.application.usecases

import com.academia.smartgym.domain.model.MaquinaIot
import com.academia.smartgym.domain.repository.MaquinaIotRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class MaquinaIotUseCase(
    private val maquinaIotRepository: MaquinaIotRepository
) {
    fun findAll(nome: String?): List<MaquinaIot> {
        return if (nome.isNullOrBlank()) {
            maquinaIotRepository.findAll()
        } else {
            maquinaIotRepository.findByName(nome)
        }
    }

    fun findById(id: String): MaquinaIot? = maquinaIotRepository.findById(id)

    fun create(maquinaIot: MaquinaIot): MaquinaIot {
        val newMaquinaIot = maquinaIot.copy(id = UUID.randomUUID().toString())
        return maquinaIotRepository.save(newMaquinaIot)
    }

    fun update(id: String, maquinaIot: MaquinaIot): MaquinaIot {
        val existingMaquina = maquinaIotRepository.findById(id) ?: throw Exception("Máquina IOT não encontrada")
        val updatedMaquina = existingMaquina.copy(
            nome = maquinaIot.nome,
            localizacao = maquinaIot.localizacao,
            status = maquinaIot.status
        )
        return maquinaIotRepository.save(updatedMaquina)
    }

    fun delete(id: String) {
        maquinaIotRepository.findById(id) ?: throw Exception("Máquina IOT não encontrada")
        maquinaIotRepository.deleteById(id)
    }
}