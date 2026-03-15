package br.com.boasalasdeatendimento.chatbot.service

import br.com.boasalasdeatendimento.chatbot.model.HorarioAtendimento
import br.com.boasalasdeatendimento.chatbot.model.Profissional
import br.com.boasalasdeatendimento.chatbot.model.StatusAgendamento
import br.com.boasalasdeatendimento.chatbot.repository.AgendamentoRepository
import br.com.boasalasdeatendimento.chatbot.repository.ProfissionalRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Service
class ProfissionalService(
    private val profissionalRepository: ProfissionalRepository,
    private val agendamentoRepository: AgendamentoRepository,
    private val objectMapper: ObjectMapper
) {
    private val dtFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    private val isoDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun listarAtivos(): List<Profissional> = profissionalRepository.listarAtivos()

    fun buscar(id: String): Profissional? = profissionalRepository.buscar(id)

    /**
     * Retorna lista de horários disponíveis para um profissional numa data específica.
     * Exclui horários que já têm agendamentos com status AGENDADO.
     * dataTexto: formato dd/MM/yyyy
     */
    fun horariosDisponiveis(profissionalId: String, dataTexto: String): List<String> {
        val profissional = profissionalRepository.buscar(profissionalId) ?: return emptyList()
        val data = LocalDate.parse(dataTexto, dtFormatter)
        val diaSemana = data.dayOfWeek.value // 1=Segunda ... 7=Domingo

        val horarios: List<HorarioAtendimento> = objectMapper.readValue(profissional.horarioAtendimento)
        val gradeHoje = horarios.filter { it.diaSemana == diaSemana }
        if (gradeHoje.isEmpty()) return emptyList()

        val slotsGerados = mutableListOf<LocalTime>()
        for (grade in gradeHoje) {
            var hora = LocalTime.parse(grade.horaInicio)
            val horaFim = LocalTime.parse(grade.horaFim)
            while (!hora.isAfter(horaFim.minusMinutes(grade.intervaloMinutos.toLong()))) {
                slotsGerados.add(hora)
                hora = hora.plusMinutes(grade.intervaloMinutos.toLong())
            }
        }

        val dataIsoInicio = "${data.format(isoDate)}T00:00"
        val ocupados = agendamentoRepository
            .listarPorProfissionalAPartirDe(profissionalId, dataIsoInicio)
            .filter {
                it.status == StatusAgendamento.AGENDADO.name &&
                it.dataHora.startsWith(data.format(isoDate))
            }
            .map { it.dataHora.substring(11, 16) } // extrai "HH:mm"
            .toSet()

        return slotsGerados
            .map { it.format(DateTimeFormatter.ofPattern("HH:mm")) }
            .filter { it !in ocupados }
    }

    /**
     * Retorna os próximos N dias em que o profissional atende.
     */
    fun proximosDiasDisponiveis(profissionalId: String, quantidade: Int = 7): List<String> {
        val profissional = profissionalRepository.buscar(profissionalId) ?: return emptyList()
        val horarios: List<HorarioAtendimento> = objectMapper.readValue(profissional.horarioAtendimento)
        val diasComAtendimento = horarios.map { it.diaSemana }.toSet()

        val dias = mutableListOf<String>()
        var data = LocalDate.now().plusDays(1)
        while (dias.size < quantidade) {
            if (data.dayOfWeek.value in diasComAtendimento) {
                dias.add(data.format(dtFormatter))
            }
            data = data.plusDays(1)
        }
        return dias
    }
}
