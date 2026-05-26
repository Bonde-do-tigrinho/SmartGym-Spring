package com.academia.smartgym.infrastructure.persistence.repositories

import com.academia.smartgym.domain.model.DispositivoIot
import com.academia.smartgym.domain.repository.DispositivoIotRepository
import com.academia.smartgym.infrastructure.persistence.mappers.toDomain
import com.academia.smartgym.infrastructure.persistence.mappers.toEntity
import org.springframework.stereotype.Component

@Component
class DispositivoIotRepositoryImpl(
    private val springDispositivoIotRepository: SpringDispositivoIotRepository
) : DispositivoIotRepository {
    override fun findAll(): List<DispositivoIot> = springDispositivoIotRepository.findAll().map { it.toDomain() }

    override fun findById(id: String): DispositivoIot? = springDispositivoIotRepository.findById(id).map { it.toDomain() }.orElse(null)

    override fun save(dispositivoIot: DispositivoIot): DispositivoIot = springDispositivoIotRepository.save(dispositivoIot.toEntity()).toDomain()

    override fun deleteById(id: String) = springDispositivoIotRepository.deleteById(id)
}

