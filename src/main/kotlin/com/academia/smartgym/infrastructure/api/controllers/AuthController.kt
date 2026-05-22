package com.academia.smartgym.infrastructure.api.controllers

import com.academia.smartgym.application.usecases.AuthUseCase
import com.academia.smartgym.domain.model.AuthRequest
import com.academia.smartgym.domain.model.AuthResponse
import com.academia.smartgym.domain.model.RegisterRequest
import com.academia.smartgym.domain.model.Usuario
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authUseCase: AuthUseCase
) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: AuthRequest): AuthResponse {
        return authUseCase.login(request)
    }

    @PostMapping("/login/professor")
    fun loginProfessor(@Valid @RequestBody request: AuthRequest): AuthResponse {
        return authUseCase.loginProfessor(request)
    }

    @PostMapping("/register")
    fun registrar(@Valid @RequestBody request: RegisterRequest): Usuario {
        return authUseCase.registrar(request)
    }

    @GetMapping("/verificar")
    fun verificarEmail(@RequestParam token: String): ResponseEntity<String> {
        val resultado = authUseCase.verificarEmail(token)
        return ResponseEntity.ok(resultado)
    }

    @PostMapping("/reenviar-verificacao")
    fun reenviarVerificacao(@RequestBody body: Map<String, String>): ResponseEntity<String> {
        val email = body["email"] ?: return ResponseEntity.badRequest().body("Email não informado")
        val resultado = authUseCase.reenviarVerificacao(email)
        return ResponseEntity.ok(resultado)
    }

    @PostMapping("/recuperar-senha")
    fun recuperarSenha(@RequestBody body: Map<String, String>): ResponseEntity<String> {
        val email = body["email"] ?: return ResponseEntity.badRequest().body("Email não informado")
        val resultado = authUseCase.solicitarRecuperacaoSenha(email)
        return ResponseEntity.ok(resultado)
    }

    @PostMapping("/resetar-senha")
    fun resetarSenha(@RequestParam token: String, @RequestBody body: Map<String, String>): ResponseEntity<String> {
        val novaSenha = body["novaSenha"] ?: return ResponseEntity.badRequest().body("Nova senha não informada")
        val resultado = authUseCase.resetarSenha(token, novaSenha)
        return ResponseEntity.ok(resultado)
    }
}