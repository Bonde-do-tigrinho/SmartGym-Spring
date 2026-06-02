package com.academia.smartgym.infrastructure.persistence.mappers

import com.academia.smartgym.domain.model.MaquinaIot
import com.academia.smartgym.infrastructure.persistence.entities.DispositivoIotEntity
import com.academia.smartgym.infrastructure.persistence.entities.MaquinaIotEntity
import java.util.UUID

fun MaquinaIot.toEntity(dispositivoIot: DispositivoIotEntity) = MaquinaIotEntity(
    id = this.id ?: UUID.randomUUID().toString(),
    nome = this.nome,
    localizacao = this.localizacao,
    status = this.status,
    dispositivoIot = dispositivoIot
)

fun MaquinaIotEntity.toDomain() = MaquinaIot(
    id = this.id,
    nome = this.nome,
    localizacao = this.localizacao,
    status = this.status,
    deviceId = this.dispositivoIot.id
)