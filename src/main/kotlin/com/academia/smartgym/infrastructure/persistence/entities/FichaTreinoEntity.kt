package com.academia.smartgym.infrastructure.persistence.entities

import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "fichas_treino")
class FichaTreinoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "aluno_id", nullable = false)
    val alunoId: Int,

    @ElementCollection
    @CollectionTable(
        name = "ficha_treino_exercicios",
        joinColumns = [JoinColumn(name = "ficha_treino_id")]
    )
    val exercicios: List<FichaTreinoExercicioEmbeddable> = emptyList(),

    @Column(nullable = false)
    val focoTreino: String,

    @Column(nullable = false)
    val vigencia: Date
)
