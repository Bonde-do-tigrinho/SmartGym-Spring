package com.academia.smartgym.infrastructure.api.controllers

import com.academia.smartgym.application.usecases.FichaTreinoUseCase
import com.academia.smartgym.domain.model.FichaTreino
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/fichas-treino")
class FichaTreinoController(
    private val useCase: FichaTreinoUseCase
) {

    @GetMapping
    fun getAll(@RequestParam(required = false) alunoId: Int?) =
        if (alunoId == null) useCase.listarTodas() else useCase.listarPorAluno(alunoId)

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) = useCase.buscarPorId(id)

    @PostMapping
    fun create(@RequestBody fichaTreino: FichaTreino) = useCase.salvar(fichaTreino)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody fichaTreino: FichaTreino) =
        useCase.salvar(fichaTreino.copy(id = id))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = useCase.excluir(id)
}
