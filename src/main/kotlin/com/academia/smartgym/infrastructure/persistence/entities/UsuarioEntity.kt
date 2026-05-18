package com.academia.smartgym.infrastructure.persistence.entities

import com.academia.smartgym.domain.model.UserRole
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "usuarios")
data class UsuarioEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    val nome: String,
    @Column(unique = true, nullable = false)
    val email: String,
    @Enumerated(EnumType.STRING)
    val role: UserRole = UserRole.ALUNO,
    val telefone: String,

    @Column(unique = true, nullable = false)
    val cpf: String,
    val plano: String? = null,
    val status: Boolean? = true,

    val treinoAtual: String?,
    val focoTreino: String?,
    val planoVencimento: String?,
    val planoValor: Double?,

    @OneToMany(mappedBy = "usuario")
    val avaliacoes: List<AvaliacaoEntity> = emptyList()
)