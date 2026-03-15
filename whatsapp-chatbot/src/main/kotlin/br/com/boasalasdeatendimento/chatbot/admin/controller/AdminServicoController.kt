package br.com.boasalasdeatendimento.chatbot.admin.controller

import br.com.boasalasdeatendimento.chatbot.model.Servico
import br.com.boasalasdeatendimento.chatbot.repository.ServicoRepository
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

data class CriarServicoRequest(
    @field:NotBlank val nome: String,
    @field:Positive val duracao: Int,
    @field:Positive val preco: Double
)

data class AtualizarServicoRequest(
    val nome: String? = null,
    val duracao: Int? = null,
    val preco: Double? = null
)

@RestController
@RequestMapping("/admin/servicos")
class AdminServicoController(
    private val servicoRepository: ServicoRepository
) {

    @GetMapping
    fun listar(): List<Servico> = servicoRepository.listarTodos()

    @GetMapping("/{id}")
    fun buscar(@PathVariable id: String): ResponseEntity<Servico> =
        servicoRepository.buscar(id)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @PostMapping
    fun criar(@Valid @RequestBody req: CriarServicoRequest): Servico {
        val servico = Servico(
            servicoId = UUID.randomUUID().toString(),
            nome = req.nome,
            duracao = req.duracao,
            preco = req.preco
        )
        servicoRepository.salvar(servico)
        return servico
    }

    @PutMapping("/{id}")
    fun atualizar(
        @PathVariable id: String,
        @RequestBody req: AtualizarServicoRequest
    ): ResponseEntity<Servico> {
        val servico = servicoRepository.buscar(id) ?: return ResponseEntity.notFound().build()
        val atualizado = servico.copy(
            nome = req.nome ?: servico.nome,
            duracao = req.duracao ?: servico.duracao,
            preco = req.preco ?: servico.preco
        )
        servicoRepository.salvar(atualizado)
        return ResponseEntity.ok(atualizado)
    }

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: String): ResponseEntity<Void> {
        servicoRepository.buscar(id) ?: return ResponseEntity.notFound().build()
        servicoRepository.deletar(id)
        return ResponseEntity.noContent().build()
    }
}
