package com.academia.smartgym.infrastructure.api.controllers

import com.academia.smartgym.application.usecases.UsuarioUseCase
import com.academia.smartgym.domain.model.Usuario
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/usuarios")
class UsuarioController(
    private val usuarioUseCase: UsuarioUseCase
) {

    @GetMapping("/me")
    fun me(@AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<Usuario> {
        val usuario = usuarioUseCase.buscarPorEmail(userDetails.username)
        return ResponseEntity.ok(usuario)
    }
}