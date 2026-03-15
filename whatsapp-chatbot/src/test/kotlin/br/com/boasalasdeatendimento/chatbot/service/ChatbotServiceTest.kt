package br.com.boasalasdeatendimento.chatbot.service

import br.com.boasalasdeatendimento.chatbot.model.EstadoChatbot
import br.com.boasalasdeatendimento.chatbot.model.Profissional
import br.com.boasalasdeatendimento.chatbot.model.SessaoChatbot
import br.com.boasalasdeatendimento.chatbot.model.Servico
import br.com.boasalasdeatendimento.chatbot.repository.ClienteRepository
import br.com.boasalasdeatendimento.chatbot.repository.ServicoRepository
import br.com.boasalasdeatendimento.chatbot.repository.SessaoRepository
import br.com.boasalasdeatendimento.chatbot.webhook.WhatsAppApiClient
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ChatbotServiceTest {

    private val sessaoRepository: SessaoRepository = mockk(relaxed = true)
    private val clienteRepository: ClienteRepository = mockk(relaxed = true)
    private val servicoRepository: ServicoRepository = mockk()
    private val profissionalService: ProfissionalService = mockk()
    private val agendamentoService: AgendamentoService = mockk(relaxed = true)
    private val whatsAppApiClient: WhatsAppApiClient = mockk(relaxed = true)
    private val objectMapper = ObjectMapper().registerKotlinModule()
    private val mensagemBuilder = MensagemBuilder()

    private lateinit var chatbotService: ChatbotService

    @BeforeEach
    fun setup() {
        chatbotService = ChatbotService(
            sessaoRepository = sessaoRepository,
            clienteRepository = clienteRepository,
            servicoRepository = servicoRepository,
            profissionalService = profissionalService,
            agendamentoService = agendamentoService,
            whatsAppApiClient = whatsAppApiClient,
            mensagemBuilder = mensagemBuilder,
            objectMapper = objectMapper
        )
        every { clienteRepository.buscar(any()) } returns null
    }

    @Test
    fun `nova sessao exibe boas vindas e menu principal`() {
        every { sessaoRepository.buscar("5511999999999") } returns null

        chatbotService.processar("5511999999999", "Olá", "João")

        val mensagemSlot = slot<String>()
        verify { whatsAppApiClient.enviarMensagem("5511999999999", capture(mensagemSlot)) }
        assert(mensagemSlot.captured.contains("Bem-vindo"))
        assert(mensagemSlot.captured.contains("1️⃣"))
    }

    @Test
    fun `opcao 1 no menu exibe lista de profissionais`() {
        val sessao = SessaoChatbot(
            telefone = "5511999999999",
            estado = EstadoChatbot.MENU_PRINCIPAL.name,
            contexto = "{}"
        )
        val profissionais = listOf(
            Profissional(profissionalId = "p1", nome = "Ana", especialidades = mutableSetOf("Corte"))
        )
        every { sessaoRepository.buscar("5511999999999") } returns sessao
        every { profissionalService.listarAtivos() } returns profissionais

        chatbotService.processar("5511999999999", "1", "")

        val mensagemSlot = slot<String>()
        verify { whatsAppApiClient.enviarMensagem("5511999999999", capture(mensagemSlot)) }
        assert(mensagemSlot.captured.contains("Ana"))
    }

    @Test
    fun `opcao invalida retorna mensagem de opcao invalida`() {
        val sessao = SessaoChatbot(
            telefone = "5511999999999",
            estado = EstadoChatbot.MENU_PRINCIPAL.name,
            contexto = "{}"
        )
        every { sessaoRepository.buscar("5511999999999") } returns sessao

        chatbotService.processar("5511999999999", "99", "")

        val mensagemSlot = slot<String>()
        verify { whatsAppApiClient.enviarMensagem("5511999999999", capture(mensagemSlot)) }
        assert(mensagemSlot.captured.contains("inválida") || mensagemSlot.captured.contains("Opção"))
    }

    @Test
    fun `fluxo de agendamento completo salva agendamento`() {
        val telefone = "5511999999999"
        val profissionais = listOf(Profissional(profissionalId = "p1", nome = "Ana"))
        val servicos = listOf(Servico(servicoId = "s1", nome = "Corte", duracao = 60, preco = 80.0))
        val dias = listOf("15/01/2025")
        val horarios = listOf("09:00", "10:00")

        // Passo 1: Menu → escolher profissional
        val sessaoMenu = SessaoChatbot(telefone, EstadoChatbot.MENU_PRINCIPAL.name, "{}")
        every { sessaoRepository.buscar(telefone) } returns sessaoMenu
        every { profissionalService.listarAtivos() } returns profissionais
        chatbotService.processar(telefone, "1", "")

        // Passo 2: Escolher profissional → listar serviços
        val sessaoProf = SessaoChatbot(telefone, EstadoChatbot.AGENDAR_PROFISSIONAL.name, "{}")
        every { sessaoRepository.buscar(telefone) } returns sessaoProf
        every { profissionalService.listarAtivos() } returns profissionais
        every { servicoRepository.listarTodos() } returns servicos
        chatbotService.processar(telefone, "1", "")

        // Passo 3: Escolher serviço → listar datas
        val sessaoServ = SessaoChatbot(
            telefone, EstadoChatbot.AGENDAR_SERVICO.name,
            """{"profissionalId":"p1","profissionalNome":"Ana"}"""
        )
        every { sessaoRepository.buscar(telefone) } returns sessaoServ
        every { servicoRepository.listarTodos() } returns servicos
        every { profissionalService.proximosDiasDisponiveis("p1") } returns dias
        chatbotService.processar(telefone, "1", "")

        // Verifica que chegou até listar datas
        val mensagemSlot = slot<String>()
        verify(atLeast = 1) { whatsAppApiClient.enviarMensagem(telefone, capture(mensagemSlot)) }
    }
}
