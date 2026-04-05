package com.academia.smartgym.infrastructure.persistence.mappers

import com.example.smartgym.domain.model.Maquina
import com.example.smartgym.domain.model.StatusMaquina
import com.example.smartgym.infrastructure.persistence.entities.MaquinaEntity

class MaquinaMapper {
    fun MaquinaEntity.toDomain() = Maquina(id, nome, localizacao, StatusMaquina.valueOf(status))
    fun Maquina.toEntity() = MaquinaEntity(id, nome, localizacao, status.name)
}