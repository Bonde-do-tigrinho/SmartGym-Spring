package com.academia.smartgym.infrastructure.persistence.entities

import com.academia.smartgym.domain.model.StatusMaquinaIot
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.JoinColumn
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "maquinas_iot")
data class MaquinaIotEntity(
    @Id
    val id: String,
    val nome: String,
    val localizacao: String,
    @Enumerated(EnumType.STRING)
    val status: StatusMaquinaIot,
    @ManyToOne(optional = false)
    @JoinColumn(name = "device_id", referencedColumnName = "id", nullable = false)
    val dispositivoIot: DispositivoIotEntity,
)