package com.academia.smartgym.application.usecases

import com.academia.smartgym.domain.model.Agendamento
import com.academia.smartgym.domain.repository.AgendamentoRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class AgendamentoUseCase(
    private val agendamentoRepository: AgendamentoRepository,
    private val aulaColetivaUseCase: AulaColetivaUseCase // Injetamos o UseCase de aulas para reaproveitar a busca
) {
    fun listarAgendamentosDoAluno(alunoId: Long): List<Agendamento> {
        // Chame seu repository de agendamentos passando o alunoId
        return agendamentoRepository.findByAlunoId(alunoId)
    }

    fun listarAgendamentosDaAula(aulaId: Long): List<Agendamento> {
        // Chame seu repository de agendamentos passando o aulaId
        return agendamentoRepository.findByAulaColetivaId(aulaId)
    }
    fun realizarAgendamento(agendamento: Agendamento): Agendamento {
        // 1. Garante que a aula existe
        val aula = aulaColetivaUseCase.buscarPorId(agendamento.aulaColetivaId)

        // Regra de Negócio 1: Não permitir agendamento de aulas no passado
        if (aula.dataHoraInicio.isBefore(LocalDateTime.now())) {
            throw IllegalArgumentException("Não é possível agendar uma aula que já começou ou já passou.")
        }

        // Regra de Negócio 2: Controle de Capacidade
        val totalAgendamentos = agendamentoRepository.contarAgendamentosPorAula(aula.id!!)
        if (totalAgendamentos >= aula.capacidadeMaxima) {
            throw IllegalArgumentException("A aula selecionada já atingiu a capacidade máxima de vagas.")
        }

        // Regra de Negócio 3: Limite de uma aula por dia para o aluno
        val inicioDoDia = aula.dataHoraInicio.toLocalDate().atStartOfDay()
        val fimDoDia = aula.dataHoraInicio.toLocalDate().atTime(LocalTime.MAX)

        val jaTemAulaNoDia = agendamentoRepository.existeAgendamentoDoAlunoNoPeriodo(
            alunoId = agendamento.alunoId,
            inicioDoDia = inicioDoDia,
            fimDoDia = fimDoDia
        )

        if (jaTemAulaNoDia) {
            throw IllegalArgumentException("O aluno já possui uma aula coletiva agendada para este dia.")
        }

        // Se passou por todas as validações, sobrescrevemos a data do agendamento para a data atual do servidor e salvamos
        val novoAgendamento = agendamento.copy(dataAgendamento = LocalDateTime.now())
        return agendamentoRepository.save(novoAgendamento)
    }
    fun cancelarAgendamento(agendamentoId: Long) {
        val agendamento = agendamentoRepository.findById(agendamentoId)
            ?: throw IllegalArgumentException("Agendamento não encontrado.")

        val aula = aulaColetivaUseCase.buscarPorId(agendamento.aulaColetivaId)

        // Regra: O aluno não pode desmarcar uma aula que já começou
        if (aula.dataHoraInicio.isBefore(LocalDateTime.now())) {
            throw IllegalArgumentException("Não é possível cancelar o agendamento de uma aula que já iniciou ou passou.")
        }

        agendamentoRepository.deleteById(agendamentoId)
    }


}