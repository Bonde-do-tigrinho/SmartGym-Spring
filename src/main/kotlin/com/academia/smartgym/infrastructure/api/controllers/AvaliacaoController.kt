package com.academia.smartgym.infrastructure.api.controllers

import com.academia.smartgym.application.usecases.AvaliacaoUseCase
import com.academia.smartgym.application.usecases.UsuarioUseCase
import com.academia.smartgym.domain.model.Avaliacao
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/avaliacoes")
class AvaliacaoController(
    private val useCase: AvaliacaoUseCase,
    private val usuarioUseCase: UsuarioUseCase,
    private val avaliacaoRepository: AvaliacaoUseCase
) {

    @GetMapping
    fun getAll(@RequestParam(required = false) nome: String?) =
        if (nome.isNullOrBlank())
            useCase.listarTodas()
        else
            useCase.buscarPorNomeAluno(nome)

    @GetMapping("/professor")
    fun listarAvaliacoesDoProfessor(@AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<List<Avaliacao>> {
        val professor = usuarioUseCase.buscarPorEmail(userDetails.username)
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        val avaliacoesProfessor = avaliacaoRepository.buscarPorProfessor(professor.id)

        return ResponseEntity.ok(avaliacoesProfessor)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) = useCase.buscarPorId(id)

    @PostMapping
    fun create(@Valid @RequestBody avaliacao: Avaliacao) = useCase.salvar(avaliacao)

    @PutMapping("/{id}")
    fun atualizarAvaliacao(
        @PathVariable id: Long,
        @RequestBody avaliacaoAlterada: Avaliacao,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<Avaliacao> {
        val professor = usuarioUseCase.buscarPorEmail(userDetails.username)
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        val avaliacaoAntiga = avaliacaoRepository.buscarPorId(id)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).build()

        val avaliacaoParaSalvar = avaliacaoAlterada.copy(
            id = id,
            professorId = professor.id
        )

        val avaliacaoSalva = avaliacaoRepository.salvar(avaliacaoParaSalvar)
        return ResponseEntity.ok(avaliacaoSalva)
    }

    @DeleteMapping("/{id}")
    fun delete(@Valid @PathVariable id: Long) = useCase.excluir(id)

    @GetMapping("/aluno/{alunoId}")
    fun getByAluno(@Valid @PathVariable alunoId: Int) = useCase.listarPorAluno(alunoId)
}

