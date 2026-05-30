package com.academia.smartgym.infrastructure.persistence.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "dispositivos_iot")
data class DispositivoIotEntity(
    @Id
    @Column(name = "id", nullable = false, length = 120)
    val id: String,

    @Column(nullable = false)
    val nome: String,

    val descricao: String? = null,

    val ativo: Boolean = true,
)

