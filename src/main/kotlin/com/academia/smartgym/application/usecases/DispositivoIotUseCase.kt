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
        return dispositivoIotRepository.save(dispositivoIot)
    }

    fun update(id: String, dispositivoIot: DispositivoIot): DispositivoIot {
        dispositivoIotRepository.findById(id) ?: throw Exception("Dispositivo IoT não encontrado")
        return dispositivoIotRepository.save(dispositivoIot)
    }

    fun delete(id: String) {
        dispositivoIotRepository.findById(id) ?: throw Exception("Dispositivo IoT não encontrado")
        dispositivoIotRepository.deleteById(id)
    }
}