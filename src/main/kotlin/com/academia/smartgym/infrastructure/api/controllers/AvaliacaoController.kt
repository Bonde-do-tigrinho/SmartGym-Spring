package com.academia.smartgym.infrastructure.api.controllers

import com.academia.smartgym.application.usecases.AvaliacaoUseCase
import com.academia.smartgym.domain.model.Avaliacao
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/avaliacoes")
class AvaliacaoController(private val useCase: AvaliacaoUseCase) {

    @GetMapping
    fun getAll(@RequestParam(required = false) nome: String?) =
        if (nome.isNullOrBlank())
            useCase.listarTodas()
        else
            useCase.buscarPorNomeAluno(nome)

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) = useCase.buscarPorId(id)

    @PostMapping
    fun create(@RequestBody avaliacao: Avaliacao) = useCase.salvar(avaliacao)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody avaliacao: Avaliacao) =
        useCase.salvar(avaliacao.copy(id = id))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = useCase.excluir(id)

    @GetMapping("/aluno/{alunoId}")
    fun getByAluno(@PathVariable alunoId: Int) = useCase.listarPorAluno(alunoId)
}

