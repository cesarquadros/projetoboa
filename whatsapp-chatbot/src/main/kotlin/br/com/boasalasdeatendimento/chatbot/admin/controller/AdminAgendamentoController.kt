package br.com.boasalasdeatendimento.chatbot.admin.controller

import br.com.boasalasdeatendimento.chatbot.model.Agendamento
import br.com.boasalasdeatendimento.chatbot.service.AgendamentoService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/agendamentos")
class AdminAgendamentoController(
    private val agendamentoService: AgendamentoService
) {

    /**
     * Lista todos os agendamentos. Filtros opcionais por profissionalId, status e data (yyyy-MM-dd).
     */
    @GetMapping
    fun listar(
        @RequestParam(required = false) profissionalId: String?,
        @RequestParam(required = false) status: String?,
        @RequestParam(required = false) data: String?
    ): List<Agendamento> {
        var agendamentos = agendamentoService.listarTodos()

        if (!profissionalId.isNullOrBlank()) {
            agendamentos = agendamentos.filter { it.profissionalId == profissionalId }
        }
        if (!status.isNullOrBlank()) {
            agendamentos = agendamentos.filter { it.status.equals(status, ignoreCase = true) }
        }
        if (!data.isNullOrBlank()) {
            agendamentos = agendamentos.filter { it.dataHora.startsWith(data) }
        }

        return agendamentos.sortedBy { it.dataHora }
    }
}
