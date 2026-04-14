package com.academia.smartgym.infrastructure.persistence.mappers

import com.academia.smartgym.domain.model.Maquina
import com.academia.smartgym.domain.model.StatusMaquina
import com.academia.smartgym.infrastructure.persistence.entities.MaquinaEntity

class MaquinaMapper {
    fun MaquinaEntity.toDomain() = Maquina(id, nome, localizacao, StatusMaquina.valueOf(status))
    fun Maquina.toEntity() = MaquinaEntity(id, nome, localizacao, status.name)
}