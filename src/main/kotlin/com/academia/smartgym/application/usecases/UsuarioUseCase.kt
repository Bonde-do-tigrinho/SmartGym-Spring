package com.academia.smartgym.application.usecases

import com.academia.smartgym.domain.model.UserRole
import com.academia.smartgym.domain.model.Usuario
import com.academia.smartgym.domain.model.VerificationToken
import com.academia.smartgym.domain.repository.PlanoRepository
import com.academia.smartgym.domain.repository.UsuarioRepository
import com.academia.smartgym.domain.repository.VerificationTokenRepository
import com.academia.smartgym.infrastructure.api.security.services.EmailService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class UsuarioUseCase(
    private val repository: UsuarioRepository,
    private val passwordEncoder: PasswordEncoder,
    private val emailService: EmailService,
    private val verificationTokenRepository: VerificationTokenRepository,
    private val planoRepository: PlanoRepository,           // novo
    private val usuarioRepository: UsuarioRepository,        // para buscar professor
    planoRepository1: PlanoRepository
) {

    fun listar() = repository.findAll()

    fun listarPorRole(role: UserRole) = repository.findByRole(role)

    fun buscar(id: Int) =
        repository.findById(id) ?: throw RuntimeException("Usuario não encontrado")

    fun buscarPorEmail(email: String): Usuario =
        repository.findByEmail(email) ?: throw RuntimeException("Usuário não encontrado")

    fun criar(usuario: Usuario): Usuario {
        if (repository.findByEmail(usuario.email) != null)
            throw RuntimeException("Este e-mail já está cadastrado.")
        if (repository.findByCpf(usuario.cpf) != null)
            throw RuntimeException("Este CPF já está cadastrado.")

        val senhaGeradaPeloSistema: String?
        val senhaFinal = if (usuario.senha.isNullOrBlank()) {
            val gerada = gerarSenhaAleatoria()
            senhaGeradaPeloSistema = gerada
            gerada
        } else {
            senhaGeradaPeloSistema = null
            usuario.senha
        }

        val usuarioProcessado = usuario.copy(
            senha = passwordEncoder.encode(senhaFinal),
            emailVerificado = false,
            dataCadastro = LocalDateTime.now().toString()
        )

        val usuarioCriado = repository.save(usuarioProcessado)

        if (senhaGeradaPeloSistema != null) {
            emailService.enviarSenhaParaNovoUsuario(
                nome = usuarioCriado.nome,
                email = usuarioCriado.email,
                senha = senhaGeradaPeloSistema
            )
        }

        val token = UUID.randomUUID().toString()
        verificationTokenRepository.salvar(
            VerificationToken(
                token = token,
                usuarioId = usuarioCriado.id!!,
                expiracao = LocalDateTime.now().plusHours(24)
            )
        )
        emailService.enviarVerificacaoEmail(usuarioCriado.nome, usuarioCriado.email, token)

        return usuarioCriado
    }

    fun criarSemEmail(usuario: Usuario): Usuario {
        if (repository.findByEmail(usuario.email) != null)
            throw RuntimeException("Este e-mail já está cadastrado.")
        if (repository.findByCpf(usuario.cpf) != null)
            throw RuntimeException("Este CPF já está cadastrado.")

        val senhaFinal = if (usuario.senha.isNullOrBlank()) gerarSenhaAleatoria() else usuario.senha

        return repository.save(
            usuario.copy(
                senha = passwordEncoder.encode(senhaFinal),
                dataCadastro = LocalDateTime.now().toString()
            )
        )
    }

    fun atualizar(id: Int, usuario: Usuario): Usuario =
        repository.update(id, usuario)
            ?: throw RuntimeException("Usuario com id $id não encontrado")

    // ── Novos métodos ──────────────────────────────
    fun vincularPlano(alunoId: Int, planoId: Int, vencimento: String): Usuario {
        val aluno = buscar(alunoId)
        val plano = planoRepository.findById(planoId)
            ?: throw RuntimeException("Plano não encontrado")
        return repository.update(alunoId, aluno.copy(
            plano = plano,
            planoVencimento = vencimento
        )) ?: throw RuntimeException("Erro ao vincular plano")
    }

    fun vincularProfessor(alunoId: Int, professorId: Int): Usuario {
        val aluno = buscar(alunoId)
        val professor = buscar(professorId)
        if (professor.role != UserRole.PROFESSOR)
            throw RuntimeException("Usuário não é um professor")
        return repository.update(alunoId, aluno.copy(professorId = professor.id))
            ?: throw RuntimeException("Erro ao vincular professor")
    }

    fun deletar(id: Int) = repository.deleteById(id)

    private fun gerarSenhaAleatoria(): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%"
        return (1..8).map { chars.random() }.joinToString("")
    }

}