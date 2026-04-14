package com.academia.smartgym.infrastructure.api.controllers

import com.academia.smartgym.application.usecases.ExercicioUseCase
import com.academia.smartgym.domain.model.Exercicio
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/exercicios")
class ExercicioController(private val useCase: ExercicioUseCase) {

    @GetMapping
    fun getAll(@RequestParam(required = false) nome: String?) =
        if (nome.isNullOrBlank())
            useCase.listarTodos()
        else
            useCase.buscarPorNome(nome)

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) = useCase.buscarPorId(id)

    @PostMapping
    fun create(@RequestBody exercicio: Exercicio) = useCase.salvar(exercicio)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody exercicio: Exercicio) =
        useCase.salvar(exercicio.copy(id = id))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = useCase.excluir(id)

    @GetMapping("/maquina/{maquinaId}")
    fun getByMaquina(@PathVariable maquinaId: Long) = useCase.listarPorMaquina(maquinaId)
}

