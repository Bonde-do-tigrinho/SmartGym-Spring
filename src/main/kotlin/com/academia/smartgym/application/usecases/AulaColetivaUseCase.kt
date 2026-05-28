package com.academia.smartgym.application.usecases

import com.academia.smartgym.domain.model.AulaColetiva
import com.academia.smartgym.domain.repository.AulaColetivaRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

// DTO para facilitar a renderização no front-end KMP
data class AulasDoDia(
    val data: LocalDate,
    val aulas: List<AulaColetiva>
)

@Service
class AulaColetivaUseCase(private val repository: AulaColetivaRepository) {

    fun criarAula(aula: AulaColetiva): AulaColetiva {
        // Validação básica: A aula não pode terminar antes de começar
        if (aula.dataHoraFim.isBefore(aula.dataHoraInicio)) {
            throw IllegalArgumentException("A data de término deve ser após a data de início.")
        }
        return repository.save(aula)
    }

    fun buscarPorId(id: Long): AulaColetiva {
        return repository.findById(id)
            ?: throw IllegalArgumentException("Aula coletiva não encontrada com o ID: $id")
    }

    fun listarAulasDaSemana(dataBase: LocalDate): List<AulasDoDia> {
        val inicioSemana = dataBase.atStartOfDay()
        val fimSemana = dataBase.plusDays(6).atTime(LocalTime.MAX) // Pega os 7 dias

        // Busca todas as aulas de uma vez só no banco (mais performático)
        val todasAsAulas = repository.buscarPorPeriodo(inicioSemana, fimSemana)

        // Agrupa as aulas pela data (ignorando a hora)
        val aulasAgrupadas = todasAsAulas.groupBy { it.dataHoraInicio.toLocalDate() }

        // Monta a lista formatada garantindo que dias sem aula retornem uma lista vazia
        return (0..6).map { diasAdicionais ->
            val dataAtual = dataBase.plusDays(diasAdicionais.toLong())
            AulasDoDia(
                data = dataAtual,
                aulas = aulasAgrupadas[dataAtual] ?: emptyList()
            )
        }
    }

    fun atualizarAula(id: Long, aulaAtualizada: AulaColetiva): AulaColetiva {
        val aulaExistente = buscarPorId(id) // Garante que a aula existe

        if (aulaAtualizada.dataHoraFim.isBefore(aulaAtualizada.dataHoraInicio)) {
            throw IllegalArgumentException("A data de término deve ser após a data de início.")
        }

        // Copia o ID da aula existente para garantir que o Spring faça um UPDATE e não um INSERT
        val aulaParaSalvar = aulaAtualizada.copy(id = aulaExistente.id)
        return repository.save(aulaParaSalvar)
    }

    fun excluirAula(id: Long) {
        val aula = buscarPorId(id)
        // Opcional: Impedir que o professor exclua uma aula que já aconteceu
        if (aula.dataHoraInicio.isBefore(LocalDateTime.now())) {
            throw IllegalArgumentException("Não é possível excluir uma aula que já iniciou ou passou.")
        }
        repository.deleteById(id)
    }
}