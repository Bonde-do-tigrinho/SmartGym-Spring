package com.academia.smartgym.infrastructure.persistence.repositories

import com.academia.smartgym.domain.model.MaquinaIot
import com.academia.smartgym.domain.repository.MaquinaIotRepository
import com.academia.smartgym.infrastructure.persistence.mappers.toDomain
import com.academia.smartgym.infrastructure.persistence.mappers.toEntity
import org.springframework.stereotype.Component

@Component
class MaquinaIotRepositoryImpl(
    private val springMaquinaIotRepository: SpringMaquinaIotRepository,
    private val springDispositivoIotRepository: SpringDispositivoIotRepository
) : MaquinaIotRepository {
    override fun findAll(): List<MaquinaIot> = springMaquinaIotRepository.findAll().map { it.toDomain() }

    override fun findById(id: String): MaquinaIot? = springMaquinaIotRepository.findById(id).map { it.toDomain() }.orElse(null)

    override fun save(maquinaIot: MaquinaIot): MaquinaIot {
        val deviceId = maquinaIot.deviceId ?: throw IllegalArgumentException("A máquina IoT precisa de um deviceId")
        val dispositivo = springDispositivoIotRepository.findById(deviceId)
            .orElseThrow { IllegalArgumentException("Dispositivo IoT não encontrado: $deviceId") }
        return springMaquinaIotRepository.save(maquinaIot.toEntity(dispositivo)).toDomain()
    }

    override fun deleteById(id: String) = springMaquinaIotRepository.deleteById(id)

    override fun findByName(nome: String): List<MaquinaIot> = springMaquinaIotRepository.findByNomeContainingIgnoreCase(nome).map { it.toDomain() }

    override fun findByDeviceId(deviceId: String): MaquinaIot? = springMaquinaIotRepository.findByDispositivoIot_Id(deviceId)?.toDomain()
}