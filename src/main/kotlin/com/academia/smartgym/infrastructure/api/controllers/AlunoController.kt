package com.academia.smartgym.infrastructure.api.controllers

import com.academia.smartgym.application.usecases.AlunoUseCase
import com.academia.smartgym.domain.model.Aluno
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/alunos")
class AlunoController(
    private val useCase: AlunoUseCase
) {

    @GetMapping
    fun listar() = useCase.listar()

    @PostMapping
    fun criar(@RequestBody aluno: Aluno) = useCase.criar(aluno)

    @GetMapping("/{id}")
    fun buscar(@PathVariable id: Long) = useCase.buscar(id)

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: Long) = useCase.deletar(id)

    @PutMapping("/{id}")
    fun atualizar(@PathVariable id: Long, @RequestBody aluno: Aluno): Aluno {
        return useCase.atualizar(id, aluno)
    }
}