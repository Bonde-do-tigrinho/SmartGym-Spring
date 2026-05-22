package com.academia.smartgym.application.usecases

import com.academia.smartgym.domain.model.AuthRequest
import com.academia.smartgym.domain.model.AuthResponse
import com.academia.smartgym.domain.model.PasswordResetToken
import com.academia.smartgym.domain.model.RegisterRequest
import com.academia.smartgym.domain.model.UserRole
import com.academia.smartgym.domain.model.Usuario
import com.academia.smartgym.domain.model.VerificationToken
import com.academia.smartgym.domain.repository.PasswordResetTokenRepository
import com.academia.smartgym.domain.repository.UsuarioRepository
import com.academia.smartgym.domain.repository.VerificationTokenRepository
import com.academia.smartgym.infrastructure.api.security.JwtService
import com.academia.smartgym.infrastructure.api.security.services.EmailService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class AuthUseCase(
    private val usuarioRepository: UsuarioRepository,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager,
    private val usuarioUseCase: UsuarioUseCase,
    private val emailService: EmailService,
    private val verificationTokenRepository: VerificationTokenRepository,
    private val passwordResetTokenRepository: PasswordResetTokenRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun login(request: AuthRequest): AuthResponse {
        val usuario = usuarioRepository.findByEmail(request.email)
            ?: throw UsernameNotFoundException("Usuário não encontrado")

        if (!usuario.emailVerificado) {
            throw RuntimeException("Verifique seu email antes de fazer login")
        }

        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.email, request.senha)
        )

        val token = jwtService.generateToken(
            username = usuario.email,
            role = role
        )

        return AuthResponse(
            token = token,
            role = role,
            nome = usuario.nome
        )
    }

    fun registrar(request: RegisterRequest): Usuario {
        val novoUsuario = Usuario(
            id = null,
            nome = request.nome,
            email = request.email,
            cpf = request.cpf,
            telefone = request.telefone,
            senha = request.senha,
            role = UserRole.ALUNO,
            status = true,
            planoVencimento = null,
            focoTreino = null,
            treinoAtual = null,
            plano = "Basic",
            planoValor = 0.0,
            emailVerificado = false
        )

        val usuarioCriado = usuarioUseCase.criar(novoUsuario)

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

    fun verificarEmail(token: String): String {
        val verification = verificationTokenRepository.buscarPorToken(token)
            ?: return "Token inválido"

        if (verification.expiracao.isBefore(LocalDateTime.now())) {
            return "Token expirado. Faça o cadastro novamente."
        }

        val usuario = usuarioRepository.findById(verification.usuarioId)
            ?: return "Usuário não encontrado"

        usuarioRepository.save(usuario.copy(emailVerificado = true))

        verificationTokenRepository.deletarPorUsuarioId(verification.usuarioId)

        return "Email verificado com sucesso! Agora você pode fazer login."
    }

    fun reenviarVerificacao(email: String): String {
        val usuario = usuarioRepository.findByEmail(email)
            ?: return "Usuário não encontrado"

        if (usuario.emailVerificado) {
            return "Email já verificado"
        }

        usuario.id?.let { verificationTokenRepository.deletarPorUsuarioId(it) }

        val token = UUID.randomUUID().toString()
        verificationTokenRepository.salvar(
            VerificationToken(
                token = token,
                usuarioId = usuario.id!!,
                expiracao = LocalDateTime.now().plusHours(24)
            )
        )

        emailService.enviarVerificacaoEmail(usuario.nome, usuario.email, token)

        return "Email de verificação reenviado com sucesso!"
    }

    fun solicitarRecuperacaoSenha(email: String): String {
        val usuario = usuarioRepository.findByEmail(email)
            ?: return "Se este email estiver cadastrado, você receberá as instruções."

        usuario.id?.let { passwordResetTokenRepository.deletarPorUsuarioId(it) }

        // ✅ gera novo token
        val token = UUID.randomUUID().toString()
        passwordResetTokenRepository.salvar(
            PasswordResetToken(
                token = token,
                usuarioId = usuario.id!!,
                expiracao = LocalDateTime.now().plusHours(1) // expira em 1 hora
            )
        )

        emailService.enviarRecuperacaoSenha(usuario.nome, usuario.email, token)

        return "Se este email estiver cadastrado, você receberá as instruções."
    }

    fun resetarSenha(token: String, novaSenha: String): String {
        val resetToken = passwordResetTokenRepository.buscarPorToken(token)
            ?: return "Token inválido"

        if (resetToken.expiracao.isBefore(LocalDateTime.now())) {
            return "Token expirado. Solicite a recuperação novamente."
        }

        val usuario = usuarioRepository.findById(resetToken.usuarioId)
            ?: return "Usuário não encontrado"

        usuarioRepository.save(
            usuario.copy(senha = passwordEncoder.encode(novaSenha))
        )

        passwordResetTokenRepository.deletarPorUsuarioId(resetToken.usuarioId)

        return "Senha redefinida com sucesso! Agora você pode fazer login."
    }
}
