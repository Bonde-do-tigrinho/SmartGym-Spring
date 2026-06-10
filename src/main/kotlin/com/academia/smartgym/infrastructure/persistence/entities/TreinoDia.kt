package com.academia.smartgym.infrastructure.persistence.entities

import jakarta.persistence.*

@Entity
@Table(name = "treino_dias")
class TreinoDiaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(nullable = false, length = 2)
    val letra: String, // "A", "B", "C"

    @Column(nullable = false)
    val focoTreino: String,

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "treino_dia_id")
    var exercicios: Set<ExercicioFichaTreinoEntity> = mutableSetOf(),

    @Column(name = "ficha_treino_id")
    var fichaTreinoId: Int? = null
)