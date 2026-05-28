package com.academia.smartgym.infrastructure.api.controllers

import com.academia.smartgym.application.usecases.UsuarioUseCase
import com.academia.smartgym.domain.model.Usuario
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

data class CompletarPerfilRequest(
    val planoId: Int,
    val professorId: Int
)

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

    @PutMapping("/completar-perfil")
    fun completarPerfil(
        @RequestBody request: CompletarPerfilRequest,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<String> {
        val email = userDetails.username
        val usuario = usuarioUseCase.buscarPorEmail(email)
            ?: return ResponseEntity.status(404).body("Usuário não encontrado")

        usuarioUseCase.completarPerfil(usuario.id, request.planoId, request.professorId)

        return ResponseEntity.ok("Perfil completado com sucesso!")
    }

    @PatchMapping("/{id}/plano")
    fun vincularPlano(
        @PathVariable id: Int,
        @RequestParam planoId: Int,
        @RequestParam vencimento: String
    ): ResponseEntity<Usuario> {
        return ResponseEntity.ok(usuarioUseCase.vincularPlano(id, planoId, vencimento))
    }

    @PatchMapping("/{id}/professor")
    fun vincularProfessor(
        @PathVariable id: Int,
        @RequestParam professorId: Int
    ): ResponseEntity<Usuario> {
        return ResponseEntity.ok(usuarioUseCase.vincularProfessor(id, professorId))
    }
}