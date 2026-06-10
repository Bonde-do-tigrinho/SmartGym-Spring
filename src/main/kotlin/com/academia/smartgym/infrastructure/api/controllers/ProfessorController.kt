package com.academia.smartgym.infrastructure.api.controllers

import com.academia.smartgym.application.dto.AlunoResumido
import com.academia.smartgym.application.usecases.UsuarioUseCase
import com.academia.smartgym.domain.model.Usuario
import com.academia.smartgym.domain.model.UserRole
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/professores")
class ProfessorController(
    private val useCase: UsuarioUseCase
) {

    @GetMapping
    fun listar() = useCase.listarPorRole(UserRole.PROFESSOR)

    @PostMapping
    fun criar(@Valid @RequestBody usuario: Usuario): Usuario {
        return useCase.criar(usuario.copy(role = UserRole.PROFESSOR))
    }

    @GetMapping("/{id}")
    fun buscar( @PathVariable id: Int) = useCase.buscar(id)

    @DeleteMapping("/{id}")
    fun deletar(@Valid @PathVariable id: Int) = useCase.deletar(id)

    @PutMapping("/{id}")
    fun atualizar(@Valid @PathVariable id: Int, @RequestBody usuario: Usuario): Usuario {
        return useCase.atualizar(id, usuario.copy(role = UserRole.PROFESSOR))
    }

    @GetMapping("/meus-alunos")
    fun listarMeusAlunos(@AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<List<AlunoResumido>> {
        val professor = useCase.buscarPorEmail(userDetails.username)
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        val meusAlunos = useCase.listarAlunosPorProfessor(professor.id)

        return ResponseEntity.ok(meusAlunos.map { AlunoResumido(it.id, it.nome) })
    }
}