package com.academia.smartgym.application.usecases

import com.academia.smartgym.domain.model.DispositivoIot
import com.academia.smartgym.domain.repository.DispositivoIotRepository
import org.springframework.stereotype.Service

@Service
class DispositivoIotUseCase(
    private val dispositivoIotRepository: DispositivoIotRepository
) {
    fun findAll(): List<DispositivoIot> = dispositivoIotRepository.findAll()

    fun findById(id: String): DispositivoIot? = dispositivoIotRepository.findById(id)

    fun create(dispositivoIot: DispositivoIot): DispositivoIot {
        if (dispositivoIot.id.isBlank()) {
            throw IllegalArgumentException("O id do dispositivo e obrigatorio")
        }
        if (dispositivoIotRepository.findById(dispositivoIot.id) != null) {
            throw IllegalArgumentException("Dispositivo IoT ja cadastrado")
        }
        return dispositivoIotRepository.save(dispositivoIot)
    }

    fun update(id: String, dispositivoIot: DispositivoIot): DispositivoIot {
        val existing = dispositivoIotRepository.findById(id) ?: throw Exception("Dispositivo IoT não encontrado")
        val updated = existing.copy(
            nome = dispositivoIot.nome,
            descricao = dispositivoIot.descricao,
            ativo = dispositivoIot.ativo
        )
        return dispositivoIotRepository.save(updated)
    }

    fun delete(id: String) {
        dispositivoIotRepository.findById(id) ?: throw Exception("Dispositivo IoT não encontrado")
        dispositivoIotRepository.deleteById(id)
    }
}

