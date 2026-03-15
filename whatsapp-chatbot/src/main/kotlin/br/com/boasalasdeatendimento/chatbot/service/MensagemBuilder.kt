package br.com.boasalasdeatendimento.chatbot.service

import br.com.boasalasdeatendimento.chatbot.model.Agendamento
import br.com.boasalasdeatendimento.chatbot.model.Profissional
import br.com.boasalasdeatendimento.chatbot.model.Servico
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class MensagemBuilder {

    fun boasVindas(nome: String): String = buildString {
        append("Olá${if (nome.isNotBlank()) ", $nome" else ""}! 👋\n")
        append("Bem-vindo ao salão de beleza!\n\n")
        append(menuPrincipal())
    }

    fun menuPrincipal(): String = buildString {
        append("O que você deseja fazer?\n\n")
        append("1️⃣ Fazer um agendamento\n")
        append("2️⃣ Consultar disponibilidade\n")
        append("3️⃣ Ver meu histórico\n")
        append("4️⃣ Cancelar agendamento\n\n")
        append("Digite o número da opção desejada.")
    }

    fun listaProfissionais(profissionais: List<Profissional>): String = buildString {
        if (profissionais.isEmpty()) {
            append("Nenhum profissional disponível no momento.")
            return@buildString
        }
        append("Escolha o profissional:\n\n")
        profissionais.forEachIndexed { index, p ->
            append("${index + 1}️⃣ ${p.nome}")
            if (p.especialidades.isNotEmpty()) {
                append(" (${p.especialidades.joinToString(", ")})")
            }
            append("\n")
        }
        append("\nDigite o número do profissional.")
    }

    fun listaServicos(servicos: List<Servico>): String = buildString {
        if (servicos.isEmpty()) {
            append("Nenhum serviço disponível no momento.")
            return@buildString
        }
        append("Escolha o serviço:\n\n")
        servicos.forEachIndexed { index, s ->
            append("${index + 1}️⃣ ${s.nome} — ${s.duracao} min — R$ ${"%.2f".format(s.preco)}\n")
        }
        append("\nDigite o número do serviço.")
    }

    fun listaDias(dias: List<String>): String = buildString {
        if (dias.isEmpty()) {
            append("Sem datas disponíveis nos próximos dias.")
            return@buildString
        }
        append("Escolha a data:\n\n")
        dias.forEachIndexed { index, d ->
            append("${index + 1}️⃣ $d\n")
        }
        append("\nDigite o número da data.")
    }

    fun listaHorarios(horarios: List<String>, data: String): String = buildString {
        if (horarios.isEmpty()) {
            append("Não há horários disponíveis em $data.\n")
            append("Por favor, escolha outra data.")
            return@buildString
        }
        append("Horários disponíveis em $data:\n\n")
        horarios.forEachIndexed { index, h ->
            append("${index + 1}️⃣ $h\n")
        }
        append("\nDigite o número do horário.")
    }

    fun confirmacaoAgendamento(
        profissionalNome: String,
        servicoNome: String,
        data: String,
        horario: String
    ): String = buildString {
        append("Confirme seu agendamento:\n\n")
        append("💇 Serviço: $servicoNome\n")
        append("👤 Profissional: $profissionalNome\n")
        append("📅 Data: $data\n")
        append("🕐 Horário: $horario\n\n")
        append("1️⃣ Confirmar\n")
        append("2️⃣ Cancelar\n\n")
        append("Digite 1 para confirmar ou 2 para cancelar.")
    }

    fun agendamentoConfirmado(
        profissionalNome: String,
        servicoNome: String,
        data: String,
        horario: String
    ): String = buildString {
        append("✅ Agendamento confirmado!\n\n")
        append("💇 $servicoNome\n")
        append("👤 $profissionalNome\n")
        append("📅 $data às $horario\n\n")
        append("Até logo! 😊")
    }

    fun historicoAgendamentos(agendamentos: List<Agendamento>): String = buildString {
        if (agendamentos.isEmpty()) {
            append("Você ainda não tem agendamentos.")
            return@buildString
        }
        append("📋 Seu histórico (últimos ${minOf(agendamentos.size, 5)}):\n\n")
        agendamentos.take(5).forEach { a ->
            val dataHoraFormatada = formatarDataHora(a.dataHora)
            val statusEmoji = when (a.status) {
                "AGENDADO" -> "🟡"
                "CONCLUIDO" -> "✅"
                "CANCELADO" -> "❌"
                else -> "❓"
            }
            append("$statusEmoji ${a.servicoNome} com ${a.profissionalNome}\n")
            append("   📅 $dataHoraFormatada\n\n")
        }
    }

    fun listaAgendamentosParaCancelar(agendamentos: List<Agendamento>): String = buildString {
        if (agendamentos.isEmpty()) {
            append("Você não tem agendamentos futuros para cancelar.")
            return@buildString
        }
        append("Selecione o agendamento que deseja cancelar:\n\n")
        agendamentos.forEachIndexed { index, a ->
            val dataHoraFormatada = formatarDataHora(a.dataHora)
            append("${index + 1}️⃣ ${a.servicoNome} com ${a.profissionalNome}\n")
            append("   📅 $dataHoraFormatada\n\n")
        }
        append("Digite o número ou *0* para voltar ao menu.")
    }

    fun confirmacaoCancelamento(agendamento: Agendamento): String = buildString {
        val dataHoraFormatada = formatarDataHora(agendamento.dataHora)
        append("Tem certeza que deseja cancelar?\n\n")
        append("💇 ${agendamento.servicoNome}\n")
        append("👤 ${agendamento.profissionalNome}\n")
        append("📅 $dataHoraFormatada\n\n")
        append("1️⃣ Sim, cancelar\n")
        append("2️⃣ Não, manter agendamento")
    }

    fun cancelamentoConfirmado(): String = "✅ Agendamento cancelado com sucesso!"

    fun opcaoInvalida(): String = "❓ Opção inválida. Por favor, escolha uma das opções disponíveis."

    fun erroGenerico(): String = "⚠️ Ocorreu um erro. Por favor, tente novamente."

    private fun formatarDataHora(isoDataHora: String): String {
        return try {
            val dt = LocalDateTime.parse(isoDataHora)
            dt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm"))
        } catch (e: Exception) {
            isoDataHora
        }
    }
}
