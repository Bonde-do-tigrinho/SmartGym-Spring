package com.academia.smartgym.infrastructure.api.controllers

import com.academia.smartgym.application.usecases.UsuarioUseCase
import com.academia.smartgym.domain.model.UserRole
import com.academia.smartgym.domain.model.Usuario
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/alunos")
class AlunoController(
    private val useCase: UsuarioUseCase
) {

    @GetMapping
    fun listar() = useCase.listarPorRole(UserRole.ALUNO)

    @PostMapping
    fun criar(@RequestBody usuario: Usuario) = useCase.criar(usuario.copy(role = UserRole.ALUNO))

    @GetMapping("/{id}")
    fun buscar(@PathVariable id: Int) = useCase.buscar(id)

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: Int) = useCase.deletar(id)

    @PutMapping("/{id}")
    fun atualizar(@PathVariable id: Int, @RequestBody usuario: Usuario): Usuario {
        return useCase.atualizar(id, usuario.copy(role = UserRole.ALUNO))
    }
}