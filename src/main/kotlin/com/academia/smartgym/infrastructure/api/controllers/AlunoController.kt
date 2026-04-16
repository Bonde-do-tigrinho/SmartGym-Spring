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
    fun buscar(@PathVariable id: Int) = useCase.buscar(id)

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: Int) = useCase.deletar(id)

    @PutMapping("/{id}")
    fun atualizar(@PathVariable id: Int, @RequestBody aluno: Aluno): Aluno {
        return useCase.atualizar(id, aluno)
    }
}