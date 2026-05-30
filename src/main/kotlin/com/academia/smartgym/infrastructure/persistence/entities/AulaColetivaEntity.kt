package com.academia.smartgym.infrastructure.persistence.entities

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "aulas_coletivas")
class AulaColetivaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var nome: String,

    @Column(name = "professor_id", nullable = false)
    var professorId: Long,

    @Column(name = "capacidade_maxima", nullable = false)
    var capacidadeMaxima: Int,

    @Column(name = "data_hora_inicio", nullable = false)
    var dataHoraInicio: LocalDateTime,

    @Column(name = "data_hora_fim", nullable = false)
    var dataHoraFim: LocalDateTime
)