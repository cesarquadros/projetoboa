package br.com.boasalasdeatendimento.chatbot.service

import br.com.boasalasdeatendimento.chatbot.model.Cliente
import br.com.boasalasdeatendimento.chatbot.model.ContextoChatbot
import br.com.boasalasdeatendimento.chatbot.model.EstadoChatbot
import br.com.boasalasdeatendimento.chatbot.model.SessaoChatbot
import br.com.boasalasdeatendimento.chatbot.repository.ClienteRepository
import br.com.boasalasdeatendimento.chatbot.repository.ServicoRepository
import br.com.boasalasdeatendimento.chatbot.repository.SessaoRepository
import br.com.boasalasdeatendimento.chatbot.webhook.WhatsAppApiClient
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ChatbotService(
    private val sessaoRepository: SessaoRepository,
    private val clienteRepository: ClienteRepository,
    private val servicoRepository: ServicoRepository,
    private val profissionalService: ProfissionalService,
    private val agendamentoService: AgendamentoService,
    private val whatsAppApiClient: WhatsAppApiClient,
    private val mensagemBuilder: MensagemBuilder,
    private val objectMapper: ObjectMapper
) {
    private val log = LoggerFactory.getLogger(javaClass)
    private val TTL_SEGUNDOS = 1800L // 30 minutos

    fun processar(telefone: String, texto: String, nomeContato: String) {
        val sessao = sessaoRepository.buscar(telefone)
            ?: SessaoChatbot(telefone = telefone, estado = EstadoChatbot.INICIO.name)

        garantirCliente(telefone, nomeContato)

        val resposta = try {
            processarEstado(sessao, texto.trim(), nomeContato)
        } catch (ex: Exception) {
            log.error("Erro ao processar mensagem de $telefone: ${ex.message}", ex)
            mensagemBuilder.erroGenerico()
        }

        whatsAppApiClient.enviarMensagem(telefone, resposta)
    }

    private fun processarEstado(sessao: SessaoChatbot, texto: String, nome: String): String {
        val estado = EstadoChatbot.valueOf(sessao.estado)
        val contexto = parseContexto(sessao.contexto)

        return when (estado) {
            EstadoChatbot.INICIO,
            EstadoChatbot.MENU_PRINCIPAL -> processarMenu(sessao, texto, nome)

            EstadoChatbot.AGENDAR_PROFISSIONAL -> processarEscolhaProfissional(sessao, texto)
            EstadoChatbot.AGENDAR_SERVICO -> processarEscolhaServico(sessao, texto, contexto)
            EstadoChatbot.AGENDAR_DATA -> processarEscolhaData(sessao, texto, contexto)
            EstadoChatbot.AGENDAR_HORARIO -> processarEscolhaHorario(sessao, texto, contexto)
            EstadoChatbot.AGENDAR_CONFIRMAR -> processarConfirmacaoAgendamento(sessao, texto, contexto)

            EstadoChatbot.DISPONIBILIDADE_PROFISSIONAL -> processarDisponibilidadeProfissional(sessao, texto)
            EstadoChatbot.DISPONIBILIDADE_DATA -> processarDisponibilidadeData(sessao, texto, contexto)

            EstadoChatbot.HISTORICO -> processarMenu(sessao, texto, nome)

            EstadoChatbot.CANCELAR_LISTAR -> processarEscolhaCancelamento(sessao, texto)
            EstadoChatbot.CANCELAR_CONFIRMAR -> processarConfirmacaoCancelamento(sessao, texto, contexto)
        }
    }

    private fun processarMenu(sessao: SessaoChatbot, texto: String, nome: String): String {
        if (sessao.estado == EstadoChatbot.INICIO.name || texto == "0" || texto.lowercase() == "menu") {
            atualizarSessao(sessao, EstadoChatbot.MENU_PRINCIPAL, ContextoChatbot())
            return mensagemBuilder.boasVindas(nome)
        }

        return when (texto) {
            "1" -> {
                val profissionais = profissionalService.listarAtivos()
                atualizarSessao(sessao, EstadoChatbot.AGENDAR_PROFISSIONAL, ContextoChatbot())
                mensagemBuilder.listaProfissionais(profissionais)
            }
            "2" -> {
                val profissionais = profissionalService.listarAtivos()
                atualizarSessao(sessao, EstadoChatbot.DISPONIBILIDADE_PROFISSIONAL, ContextoChatbot())
                mensagemBuilder.listaProfissionais(profissionais)
            }
            "3" -> {
                val historico = agendamentoService.listarHistorico(sessao.telefone)
                atualizarSessao(sessao, EstadoChatbot.MENU_PRINCIPAL, ContextoChatbot())
                mensagemBuilder.historicoAgendamentos(historico) + "\n\n" + mensagemBuilder.menuPrincipal()
            }
            "4" -> {
                val futuros = agendamentoService.listarAgendadosFuturos(sessao.telefone)
                atualizarSessao(sessao, EstadoChatbot.CANCELAR_LISTAR, ContextoChatbot())
                mensagemBuilder.listaAgendamentosParaCancelar(futuros)
            }
            else -> mensagemBuilder.opcaoInvalida() + "\n\n" + mensagemBuilder.menuPrincipal()
        }
    }

    private fun processarEscolhaProfissional(sessao: SessaoChatbot, texto: String): String {
        if (texto == "0") return voltarMenu(sessao)
        val profissionais = profissionalService.listarAtivos()
        val index = texto.toIntOrNull()?.minus(1) ?: return mensagemBuilder.opcaoInvalida() + "\n\n" + mensagemBuilder.listaProfissionais(profissionais)
        val profissional = profissionais.getOrNull(index) ?: return mensagemBuilder.opcaoInvalida() + "\n\n" + mensagemBuilder.listaProfissionais(profissionais)

        val servicos = servicoRepository.listarTodos()
        atualizarSessao(sessao, EstadoChatbot.AGENDAR_SERVICO, ContextoChatbot(
            profissionalId = profissional.profissionalId,
            profissionalNome = profissional.nome
        ))
        return mensagemBuilder.listaServicos(servicos)
    }

    private fun processarEscolhaServico(sessao: SessaoChatbot, texto: String, contexto: ContextoChatbot): String {
        if (texto == "0") return voltarMenu(sessao)
        val servicos = servicoRepository.listarTodos()
        val index = texto.toIntOrNull()?.minus(1) ?: return mensagemBuilder.opcaoInvalida() + "\n\n" + mensagemBuilder.listaServicos(servicos)
        val servico = servicos.getOrNull(index) ?: return mensagemBuilder.opcaoInvalida() + "\n\n" + mensagemBuilder.listaServicos(servicos)

        val dias = profissionalService.proximosDiasDisponiveis(contexto.profissionalId!!)
        atualizarSessao(sessao, EstadoChatbot.AGENDAR_DATA, contexto.copy(
            servicoId = servico.servicoId,
            servicoNome = servico.nome
        ))
        return mensagemBuilder.listaDias(dias)
    }

    private fun processarEscolhaData(sessao: SessaoChatbot, texto: String, contexto: ContextoChatbot): String {
        if (texto == "0") return voltarMenu(sessao)
        val dias = profissionalService.proximosDiasDisponiveis(contexto.profissionalId!!)
        val index = texto.toIntOrNull()?.minus(1) ?: return mensagemBuilder.opcaoInvalida() + "\n\n" + mensagemBuilder.listaDias(dias)
        val dataSelecionada = dias.getOrNull(index) ?: return mensagemBuilder.opcaoInvalida() + "\n\n" + mensagemBuilder.listaDias(dias)

        val horarios = profissionalService.horariosDisponiveis(contexto.profissionalId, dataSelecionada)
        atualizarSessao(sessao, EstadoChatbot.AGENDAR_HORARIO, contexto.copy(data = dataSelecionada))
        return mensagemBuilder.listaHorarios(horarios, dataSelecionada)
    }

    private fun processarEscolhaHorario(sessao: SessaoChatbot, texto: String, contexto: ContextoChatbot): String {
        if (texto == "0") return voltarMenu(sessao)
        val horarios = profissionalService.horariosDisponiveis(contexto.profissionalId!!, contexto.data!!)
        val index = texto.toIntOrNull()?.minus(1) ?: return mensagemBuilder.opcaoInvalida() + "\n\n" + mensagemBuilder.listaHorarios(horarios, contexto.data)
        val horarioSelecionado = horarios.getOrNull(index) ?: return mensagemBuilder.opcaoInvalida() + "\n\n" + mensagemBuilder.listaHorarios(horarios, contexto.data)

        atualizarSessao(sessao, EstadoChatbot.AGENDAR_CONFIRMAR, contexto.copy(horario = horarioSelecionado))
        return mensagemBuilder.confirmacaoAgendamento(
            profissionalNome = contexto.profissionalNome!!,
            servicoNome = contexto.servicoNome!!,
            data = contexto.data,
            horario = horarioSelecionado
        )
    }

    private fun processarConfirmacaoAgendamento(sessao: SessaoChatbot, texto: String, contexto: ContextoChatbot): String {
        return when (texto) {
            "1" -> {
                val agendamento = agendamentoService.agendar(
                    clienteTelefone = sessao.telefone,
                    profissionalId = contexto.profissionalId!!,
                    profissionalNome = contexto.profissionalNome!!,
                    servicoId = contexto.servicoId!!,
                    servicoNome = contexto.servicoNome!!,
                    data = contexto.data!!,
                    horario = contexto.horario!!
                )
                atualizarSessao(sessao, EstadoChatbot.MENU_PRINCIPAL, ContextoChatbot())
                mensagemBuilder.agendamentoConfirmado(
                    profissionalNome = contexto.profissionalNome,
                    servicoNome = contexto.servicoNome,
                    data = contexto.data,
                    horario = contexto.horario
                ) + "\n\n" + mensagemBuilder.menuPrincipal()
            }
            "2" -> voltarMenu(sessao)
            else -> mensagemBuilder.opcaoInvalida() + "\n\n" + mensagemBuilder.confirmacaoAgendamento(
                profissionalNome = contexto.profissionalNome!!,
                servicoNome = contexto.servicoNome!!,
                data = contexto.data!!,
                horario = contexto.horario!!
            )
        }
    }

    private fun processarDisponibilidadeProfissional(sessao: SessaoChatbot, texto: String): String {
        if (texto == "0") return voltarMenu(sessao)
        val profissionais = profissionalService.listarAtivos()
        val index = texto.toIntOrNull()?.minus(1) ?: return mensagemBuilder.opcaoInvalida() + "\n\n" + mensagemBuilder.listaProfissionais(profissionais)
        val profissional = profissionais.getOrNull(index) ?: return mensagemBuilder.opcaoInvalida() + "\n\n" + mensagemBuilder.listaProfissionais(profissionais)

        val dias = profissionalService.proximosDiasDisponiveis(profissional.profissionalId)
        atualizarSessao(sessao, EstadoChatbot.DISPONIBILIDADE_DATA, ContextoChatbot(
            profissionalId = profissional.profissionalId,
            profissionalNome = profissional.nome
        ))
        return mensagemBuilder.listaDias(dias)
    }

    private fun processarDisponibilidadeData(sessao: SessaoChatbot, texto: String, contexto: ContextoChatbot): String {
        if (texto == "0") return voltarMenu(sessao)
        val dias = profissionalService.proximosDiasDisponiveis(contexto.profissionalId!!)
        val index = texto.toIntOrNull()?.minus(1) ?: return mensagemBuilder.opcaoInvalida() + "\n\n" + mensagemBuilder.listaDias(dias)
        val dataSelecionada = dias.getOrNull(index) ?: return mensagemBuilder.opcaoInvalida() + "\n\n" + mensagemBuilder.listaDias(dias)

        val horarios = profissionalService.horariosDisponiveis(contexto.profissionalId, dataSelecionada)
        atualizarSessao(sessao, EstadoChatbot.MENU_PRINCIPAL, ContextoChatbot())
        return mensagemBuilder.listaHorarios(horarios, dataSelecionada) + "\n\n" + mensagemBuilder.menuPrincipal()
    }

    private fun processarEscolhaCancelamento(sessao: SessaoChatbot, texto: String): String {
        if (texto == "0") return voltarMenu(sessao)
        val futuros = agendamentoService.listarAgendadosFuturos(sessao.telefone)
        val index = texto.toIntOrNull()?.minus(1) ?: return mensagemBuilder.opcaoInvalida() + "\n\n" + mensagemBuilder.listaAgendamentosParaCancelar(futuros)
        val agendamento = futuros.getOrNull(index) ?: return mensagemBuilder.opcaoInvalida() + "\n\n" + mensagemBuilder.listaAgendamentosParaCancelar(futuros)

        atualizarSessao(sessao, EstadoChatbot.CANCELAR_CONFIRMAR, ContextoChatbot(agendamentoId = agendamento.agendamentoId))
        return mensagemBuilder.confirmacaoCancelamento(agendamento)
    }

    private fun processarConfirmacaoCancelamento(sessao: SessaoChatbot, texto: String, contexto: ContextoChatbot): String {
        return when (texto) {
            "1" -> {
                agendamentoService.cancelar(sessao.telefone, contexto.agendamentoId!!)
                atualizarSessao(sessao, EstadoChatbot.MENU_PRINCIPAL, ContextoChatbot())
                mensagemBuilder.cancelamentoConfirmado() + "\n\n" + mensagemBuilder.menuPrincipal()
            }
            "2" -> voltarMenu(sessao)
            else -> mensagemBuilder.opcaoInvalida()
        }
    }

    private fun voltarMenu(sessao: SessaoChatbot): String {
        atualizarSessao(sessao, EstadoChatbot.MENU_PRINCIPAL, ContextoChatbot())
        return mensagemBuilder.menuPrincipal()
    }

    private fun atualizarSessao(sessao: SessaoChatbot, novoEstado: EstadoChatbot, contexto: ContextoChatbot) {
        sessao.estado = novoEstado.name
        sessao.contexto = objectMapper.writeValueAsString(contexto)
        sessao.ttl = Instant.now().epochSecond + TTL_SEGUNDOS
        sessaoRepository.salvar(sessao)
    }

    private fun parseContexto(contextoJson: String): ContextoChatbot =
        try { objectMapper.readValue(contextoJson) } catch (e: Exception) { ContextoChatbot() }

    private fun garantirCliente(telefone: String, nome: String) {
        if (clienteRepository.buscar(telefone) == null) {
            clienteRepository.salvar(Cliente(
                telefone = telefone,
                nome = nome,
                createdAt = java.time.Instant.now().toString()
            ))
        }
    }
}
