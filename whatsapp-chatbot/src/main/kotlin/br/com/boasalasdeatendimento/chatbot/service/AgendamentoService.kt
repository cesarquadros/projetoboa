package br.com.boasalasdeatendimento.chatbot.service

import br.com.boasalasdeatendimento.chatbot.model.Agendamento
import br.com.boasalasdeatendimento.chatbot.model.StatusAgendamento
import br.com.boasalasdeatendimento.chatbot.repository.AgendamentoRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Service
class AgendamentoService(
    private val agendamentoRepository: AgendamentoRepository
) {
    private val dtFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    fun agendar(
        clienteTelefone: String,
        profissionalId: String,
        profissionalNome: String,
        servicoId: String,
        servicoNome: String,
        data: String,
        horario: String
    ): Agendamento {
        // data: dd/MM/yyyy, horario: HH:mm -> dataHora ISO: yyyy-MM-ddTHH:mm
        val dt = LocalDateTime.parse("$data $horario", dtFormatter)
        val isoDataHora = dt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))

        val agendamento = Agendamento(
            clienteTelefone = clienteTelefone,
            agendamentoId = UUID.randomUUID().toString(),
            profissionalId = profissionalId,
            profissionalNome = profissionalNome,
            servicoId = servicoId,
            servicoNome = servicoNome,
            dataHora = isoDataHora,
            status = StatusAgendamento.AGENDADO.name
        )
        agendamentoRepository.salvar(agendamento)
        return agendamento
    }

    fun cancelar(clienteTelefone: String, agendamentoId: String): Boolean {
        val agendamento = agendamentoRepository.buscar(clienteTelefone, agendamentoId) ?: return false
        if (agendamento.status != StatusAgendamento.AGENDADO.name) return false
        agendamentoRepository.salvar(agendamento.copy(status = StatusAgendamento.CANCELADO.name))
        return true
    }

    fun listarHistorico(clienteTelefone: String): List<Agendamento> =
        agendamentoRepository.listarPorCliente(clienteTelefone)
            .sortedByDescending { it.dataHora }

    fun listarAgendadosFuturos(clienteTelefone: String): List<Agendamento> {
        val agora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))
        return agendamentoRepository.listarAgendadosPorCliente(clienteTelefone)
            .filter { it.dataHora >= agora }
    }

    fun listarTodos(): List<Agendamento> = agendamentoRepository.listarTodos()
}
