package com.academia.smartgym.infrastructure.persistence.entities

import com.academia.smartgym.domain.model.UserRole
import jakarta.persistence.*

@Entity
@Table(name = "usuarios")
data class UsuarioEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(nullable = false)
    val nome: String,

    @Column(unique = true, nullable = false)
    val email: String,

    @Column(unique = true, nullable = false)
    val cpf: String,

    val telefone: String,
    val senha: String? = null,

    @Enumerated(EnumType.STRING)
    val role: UserRole = UserRole.ALUNO,

    // ── Plano ──────────────────────────────────────
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plano_id", nullable = true)
    val plano: PlanoEntity? = null,

    @Column(name = "plano_vencimento")
    val planoVencimento: String? = null,

    // ── Professor ──────────────────────────────────
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "professor_id", nullable = true)
    val professor: UsuarioEntity? = null,

    // ── Dados Físicos ──────────────────────────────
    val dataNascimento: String? = null,
    val altura: Double? = null,
    val peso: Double? = null,

    @Column(name = "data_cadastro")
    val dataCadastro: String? = null,

    // ── Status ─────────────────────────────────────
    val status: Boolean = true,

    @Column(name = "email_verificado", nullable = false)
    val emailVerificado: Boolean = false,

    // ── Relacionamentos ────────────────────────────
    @OneToMany(mappedBy = "usuario", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val avaliacoes: List<AvaliacaoEntity> = emptyList()
)