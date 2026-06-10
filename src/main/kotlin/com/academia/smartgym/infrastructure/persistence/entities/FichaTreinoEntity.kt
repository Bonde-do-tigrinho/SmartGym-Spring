package com.academia.smartgym.infrastructure.persistence.entities

import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "fichas_treino")
class FichaTreinoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(name = "aluno_id", nullable = false)
    val alunoId: Int?,

    @Column(name = "professor_id")
    val professorId: Int? = null,

    @Column(nullable = false)
    val vigencia: Date,

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "ficha_treino_id")
    var rotinaDias: Set<TreinoDiaEntity> = mutableSetOf()
)
