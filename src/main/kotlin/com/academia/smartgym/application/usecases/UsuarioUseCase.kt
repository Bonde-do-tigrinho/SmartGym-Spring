package com.academia.smartgym.application.usecases

import com.academia.smartgym.domain.model.UserRole
import com.academia.smartgym.domain.model.Usuario
import com.academia.smartgym.domain.repository.UsuarioRepository
import com.academia.smartgym.infrastructure.api.security.services.EmailService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UsuarioUseCase(
    private val repository: UsuarioRepository,
    private val passwordEncoder: PasswordEncoder,
    private val emailService: EmailService
) {

    fun listar() = repository.findAll()

    fun listarPorRole(role: UserRole) = repository.findByRole(role)

    fun buscar(id: Int) =
        repository.findById(id) ?: throw RuntimeException("Usuario não encontrado")

    fun criar(usuario: Usuario): Usuario{
        if (repository.findByEmail(usuario.email) != null) {
            throw RuntimeException("Este e-mail já está cadastrado.")
        }

        if (repository.findByCpf(usuario.cpf) != null) {
            throw RuntimeException("Este CPF já está cadastrado.")
        }

        val senhaGeradaPeloSistema: String?

        val senhaFinal = if (usuario.senha.isNullOrBlank()) {
            val senhaGerada = gerarSenhaAleatoria()
            println("SENHA GERADA PARA ${usuario.email}: $senhaGerada")
            senhaGeradaPeloSistema = senhaGerada
            senhaGerada
        } else {
            senhaGeradaPeloSistema = null
            usuario.senha
        }

        val usuarioProcessado = usuario.copy(
            senha = passwordEncoder.encode(senhaFinal)
        )

        val usuarioCriado = repository.save(usuarioProcessado)

        if (senhaGeradaPeloSistema != null) {
            emailService.enviarSenhaParaNovoUsuario(
                nome = usuarioCriado.nome,
                email = usuarioCriado.email,
                senha = senhaGeradaPeloSistema
            )
        }

        return usuarioCriado
    }

    fun criarSemEmail(usuario: Usuario): Usuario {
        if (repository.findByEmail(usuario.email) != null) {
            throw RuntimeException("Este e-mail já está cadastrado.")
        }

        if (repository.findByCpf(usuario.cpf) != null) {
            throw RuntimeException("Este CPF já está cadastrado.")
        }

        val senhaFinal = if (usuario.senha.isNullOrBlank()) {
            gerarSenhaAleatoria()
        } else {
            usuario.senha
        }

        return repository.save(usuario.copy(
            senha = passwordEncoder.encode(senhaFinal)
        ))
    }

    fun deletar(id: Int) = repository.deleteById(id)

    fun atualizar(id: Int, usuario: Usuario): Usuario {
        return repository.update(id, usuario)
            ?: throw RuntimeException("Usuario com id $id não encontrado para atualização")
    }

    private fun gerarSenhaAleatoria(): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%"
        return (1..8)
            .map { chars.random() }
            .joinToString("")
    }
}