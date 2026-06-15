package com.academia.smartgym.infrastructure.persistence.entities

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "agendamentos")
class AgendamentoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "aluno_id", nullable = false)
    var alunoId: Long,

    @Column(name = "aula_coletiva_id", nullable = false)
    var aulaColetivaId: Long,

    @Column(name = "data_agendamento", nullable = false)
    var dataAgendamento: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    val confirmado: Boolean = true
)