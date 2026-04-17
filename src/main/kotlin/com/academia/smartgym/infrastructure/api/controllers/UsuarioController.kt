package com.academia.smartgym.infrastructure.api.controllers

import com.academia.smartgym.application.usecases.UsuarioUseCase
import com.academia.smartgym.domain.model.Usuario
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/alunos")
class UsuarioController(
    private val useCase: UsuarioUseCase
) {

    @GetMapping
    fun listar() = useCase.listar()

    @PostMapping
    fun criar(@RequestBody usuario: Usuario) = useCase.criar(usuario)

    @GetMapping("/{id}")
    fun buscar(@PathVariable id: Int) = useCase.buscar(id)

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: Int) = useCase.deletar(id)

    @PutMapping("/{id}")
    fun atualizar(@PathVariable id: Int, @RequestBody usuario: Usuario): Usuario {
        return useCase.atualizar(id, usuario)
    }
}