package com.academia.smartgym.infrastructure.persistence.entities

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "avaliacoes")
class AvaliacaoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "usuario_id", nullable = false)
    val alunoId: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", insertable = false, updatable = false)
    val usuario: UsuarioEntity? = null,

    @Column(nullable = false)
    val nomeAluno: String,

    @Column(nullable = false)
    val dataAvaliacao: LocalDate,

    @Column(nullable = false)
    val peso: Double,

    @Column(nullable = false)
    val percentualGordura: Double,

    @Column(nullable = false)
    val imc: Double,

    @Column(nullable = false, length = 500)
    val nota: String
)


