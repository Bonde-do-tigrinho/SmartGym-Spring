package com.academia.smartgym.infrastructure.api.controllers

import com.academia.smartgym.application.usecases.MaquinaUseCase
import com.academia.smartgym.domain.model.Maquina
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/maquinas")
class MaquinaController(private val useCase: MaquinaUseCase) {

    @GetMapping
    fun getAll(@RequestParam(required = false) nome: String?) =
        if (nome.isNullOrBlank())
            useCase.listarTodas()
        else
            useCase.buscarPorNome(nome)

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) = useCase.buscarPorId(id)

    @PostMapping
    fun create(@RequestBody maquina: Maquina) = useCase.salvar(maquina)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody maquina: Maquina) =
        useCase.salvar(maquina.copy(id = id))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = useCase.excluir(id)
}