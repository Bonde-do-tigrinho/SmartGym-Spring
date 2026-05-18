package com.academia.smartgym.infrastructure.api.controllers

import com.academia.smartgym.application.usecases.AuthUseCase
import com.academia.smartgym.domain.model.AuthRequest
import com.academia.smartgym.domain.model.AuthResponse
import com.academia.smartgym.domain.model.RegisterRequest
import com.academia.smartgym.domain.model.Usuario
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
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

    @PostMapping("/register")
    fun registrar(@Valid @RequestBody request: RegisterRequest): Usuario {
        return authUseCase.registrar(request)
    }
}