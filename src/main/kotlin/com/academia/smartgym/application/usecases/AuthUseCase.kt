package com.academia.smartgym.application.usecases

import com.academia.smartgym.domain.model.AuthRequest
import com.academia.smartgym.domain.model.AuthResponse
import com.academia.smartgym.domain.model.RegisterRequest
import com.academia.smartgym.domain.model.UserRole
import com.academia.smartgym.domain.model.Usuario
import com.academia.smartgym.domain.repository.UsuarioRepository
import com.academia.smartgym.infrastructure.api.security.JwtService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthUseCase(
    private val usuarioRepository: UsuarioRepository,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager,
    private val usuarioUseCase: UsuarioUseCase
) {
    fun login(request: AuthRequest): AuthResponse {
        return autenticar(request, null)
    }

    fun loginProfessor(request: AuthRequest): AuthResponse {
        return autenticar(request, UserRole.PROFESSOR)
    }

    private fun autenticar(request: AuthRequest, roleObrigatoria: UserRole?): AuthResponse {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(request.email, request.senha)
            )
        } catch (_: Exception) {
            throw BadCredentialsException("E-mail ou senha inválidos")
        }

        val usuario = usuarioRepository.findByEmail(request.email)
            ?: throw UsernameNotFoundException("Usuário não encontrado")

        if (roleObrigatoria != null && usuario.role != roleObrigatoria) {
            throw BadCredentialsException("Usuário não é ${roleObrigatoria.name}")
        }

        val role = usuario.role.name
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
            plano = null,
            planoValor = null,
        )

        return usuarioUseCase.criar(novoUsuario)
    }
}