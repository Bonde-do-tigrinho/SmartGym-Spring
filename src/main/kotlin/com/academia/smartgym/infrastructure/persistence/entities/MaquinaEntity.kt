package com.academia.smartgym.infrastructure.persistence.entities

import jakarta.persistence.*

@Entity
@Table(name = "maquinas") // Nome da tabela no Postgres
class MaquinaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val nome: String,

    @Column(nullable = false)
    val localizacao: String,

    @Column(nullable = false)
    val status: String = "LIVRE" // No banco, salvamos como String
)