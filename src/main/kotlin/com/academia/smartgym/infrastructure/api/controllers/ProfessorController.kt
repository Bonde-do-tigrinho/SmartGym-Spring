package com.academia.smartgym.infrastructure.api.controllers

import com.academia.smartgym.application.usecases.UsuarioUseCase
import com.academia.smartgym.domain.model.Usuario
import com.academia.smartgym.domain.model.UserRole
import jakarta.validation.Valid
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
}