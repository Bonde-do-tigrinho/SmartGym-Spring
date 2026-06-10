package com.academia.smartgym.infrastructure.api.controllers

import com.academia.smartgym.application.usecases.ExercicioUseCase
import com.academia.smartgym.domain.model.Exercicio
import jakarta.validation.Valid
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
    fun getById(@PathVariable id: Int) = useCase.buscarPorId(id)

    @PostMapping
    fun create(@Valid @RequestBody exercicio: Exercicio) = useCase.salvar(exercicio)

    @PutMapping("/{id}")
    fun update(@Valid @PathVariable id: Int, @RequestBody exercicio: Exercicio) =
        useCase.salvar(exercicio.copy(id = id))

    @DeleteMapping("/{id}")
    fun delete(@Valid @PathVariable id: Int) = useCase.excluir(id)

    @GetMapping("/maquina/{maquinaId}")
    fun getByMaquina(@Valid @PathVariable maquinaId: Int) = useCase.listarPorMaquina(maquinaId)
}

