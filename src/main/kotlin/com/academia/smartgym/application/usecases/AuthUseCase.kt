package com.academia.smartgym.application.usecases

import com.academia.smartgym.domain.model.AuthRequest
import com.academia.smartgym.domain.model.AuthResponse
import com.academia.smartgym.domain.model.RegisterRequest
import com.academia.smartgym.domain.model.UserRole
import com.academia.smartgym.domain.model.Usuario
import com.academia.smartgym.domain.repository.UsuarioRepository
import com.academia.smartgym.infrastructure.api.security.JwtService
import org.springframework.security.authentication.AuthenticationManager
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
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.email, request.senha)
        )

        val usuario = usuarioRepository.findByEmail(request.email)
            ?: throw UsernameNotFoundException("Usuário não encontrado")

        val token = jwtService.generateToken(
            username = usuario.email,
            role = usuario.role?.name ?: "ALUNO"
        )

        return AuthResponse(
            token = token,
            role = usuario.role?.name ?: "ALUNO",
            nome = usuario.nome
        )
    }

    fun registrar(request: RegisterRequest): Usuario{
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