package br.com.boasalasdeatendimento.chatbot.service

import br.com.boasalasdeatendimento.chatbot.model.Agendamento
import br.com.boasalasdeatendimento.chatbot.model.HorarioAtendimento
import br.com.boasalasdeatendimento.chatbot.model.Profissional
import br.com.boasalasdeatendimento.chatbot.model.StatusAgendamento
import br.com.boasalasdeatendimento.chatbot.repository.AgendamentoRepository
import br.com.boasalasdeatendimento.chatbot.repository.ProfissionalRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ProfissionalServiceTest {

    private val profissionalRepository: ProfissionalRepository = mockk()
    private val agendamentoRepository: AgendamentoRepository = mockk()
    private val objectMapper = ObjectMapper().registerKotlinModule()

    private lateinit var service: ProfissionalService

    @BeforeEach
    fun setup() {
        service = ProfissionalService(profissionalRepository, agendamentoRepository, objectMapper)
    }

    @Test
    fun `horariosDisponiveis retorna slots quando nao ha agendamentos`() {
        val profissionalId = "prof-001"
        val horarios = listOf(
            HorarioAtendimento(diaSemana = 1, horaInicio = "09:00", horaFim = "12:00", intervaloMinutos = 60)
        )
        val profissional = Profissional(
            profissionalId = profissionalId,
            nome = "Ana",
            horarioAtendimento = objectMapper.writeValueAsString(horarios)
        )

        // Próxima segunda-feira
        val proximaSegunda = proximoDiaSemana(1)
        val dtFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val isoDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dataTexto = proximaSegunda.format(dtFormatter)
        val dataIso = proximaSegunda.format(isoDate)

        every { profissionalRepository.buscar(profissionalId) } returns profissional
        every { agendamentoRepository.listarPorProfissionalAPartirDe(profissionalId, "${dataIso}T00:00") } returns emptyList()

        val slots = service.horariosDisponiveis(profissionalId, dataTexto)

        assertEquals(listOf("09:00", "10:00", "11:00"), slots)
    }

    @Test
    fun `horariosDisponiveis exclui horarios ja agendados`() {
        val profissionalId = "prof-001"
        val horarios = listOf(
            HorarioAtendimento(diaSemana = 1, horaInicio = "09:00", horaFim = "11:00", intervaloMinutos = 60)
        )
        val profissional = Profissional(
            profissionalId = profissionalId,
            nome = "Ana",
            horarioAtendimento = objectMapper.writeValueAsString(horarios)
        )

        val proximaSegunda = proximoDiaSemana(1)
        val dtFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val isoDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dataTexto = proximaSegunda.format(dtFormatter)
        val dataIso = proximaSegunda.format(isoDate)

        val agendamentoOcupado = Agendamento(
            clienteTelefone = "5511999999999",
            agendamentoId = "id-1",
            profissionalId = profissionalId,
            dataHora = "${dataIso}T09:00",
            status = StatusAgendamento.AGENDADO.name
        )

        every { profissionalRepository.buscar(profissionalId) } returns profissional
        every { agendamentoRepository.listarPorProfissionalAPartirDe(profissionalId, "${dataIso}T00:00") } returns listOf(agendamentoOcupado)

        val slots = service.horariosDisponiveis(profissionalId, dataTexto)

        assertEquals(listOf("10:00"), slots)
    }

    @Test
    fun `horariosDisponiveis retorna vazio quando profissional nao atende no dia`() {
        val profissionalId = "prof-001"
        // Só atende segunda (diaSemana=1)
        val horarios = listOf(
            HorarioAtendimento(diaSemana = 1, horaInicio = "09:00", horaFim = "12:00", intervaloMinutos = 60)
        )
        val profissional = Profissional(
            profissionalId = profissionalId,
            nome = "Ana",
            horarioAtendimento = objectMapper.writeValueAsString(horarios)
        )

        val proximaSabado = proximoDiaSemana(6)
        val dtFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val dataTexto = proximaSabado.format(dtFormatter)

        every { profissionalRepository.buscar(profissionalId) } returns profissional

        val slots = service.horariosDisponiveis(profissionalId, dataTexto)

        assertTrue(slots.isEmpty())
    }

    private fun proximoDiaSemana(diaSemana: Int): LocalDate {
        var data = LocalDate.now().plusDays(1)
        while (data.dayOfWeek.value != diaSemana) {
            data = data.plusDays(1)
        }
        return data
    }
}
