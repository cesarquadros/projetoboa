package br.com.boasalasdeatendimento.chatbot.admin.controller

import br.com.boasalasdeatendimento.chatbot.model.HorarioAtendimento
import br.com.boasalasdeatendimento.chatbot.model.Profissional
import br.com.boasalasdeatendimento.chatbot.repository.ProfissionalRepository
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
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

data class CriarProfissionalRequest(
    @field:NotBlank val nome: String,
    val especialidades: Set<String> = emptySet(),
    @field:NotEmpty val horarioAtendimento: List<HorarioAtendimento>
)

data class AtualizarProfissionalRequest(
    val nome: String? = null,
    val especialidades: Set<String>? = null,
    val ativo: Boolean? = null,
    val horarioAtendimento: List<HorarioAtendimento>? = null
)

@RestController
@RequestMapping("/admin/profissionais")
class AdminProfissionalController(
    private val profissionalRepository: ProfissionalRepository,
    private val objectMapper: ObjectMapper
) {

    @GetMapping
    fun listar(): List<Profissional> = profissionalRepository.listarTodos()

    @GetMapping("/{id}")
    fun buscar(@PathVariable id: String): ResponseEntity<Profissional> =
        profissionalRepository.buscar(id)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @PostMapping
    fun criar(@Valid @RequestBody req: CriarProfissionalRequest): Profissional {
        val profissional = Profissional(
            profissionalId = UUID.randomUUID().toString(),
            nome = req.nome,
            especialidades = req.especialidades.toMutableSet(),
            ativo = true,
            horarioAtendimento = objectMapper.writeValueAsString(req.horarioAtendimento)
        )
        profissionalRepository.salvar(profissional)
        return profissional
    }

    @PutMapping("/{id}")
    fun atualizar(
        @PathVariable id: String,
        @RequestBody req: AtualizarProfissionalRequest
    ): ResponseEntity<Profissional> {
        val profissional = profissionalRepository.buscar(id)
            ?: return ResponseEntity.notFound().build()

        val atualizado = profissional.copy(
            nome = req.nome ?: profissional.nome,
            especialidades = req.especialidades?.toMutableSet() ?: profissional.especialidades,
            ativo = req.ativo ?: profissional.ativo,
            horarioAtendimento = req.horarioAtendimento
                ?.let { objectMapper.writeValueAsString(it) }
                ?: profissional.horarioAtendimento
        )
        profissionalRepository.salvar(atualizado)
        return ResponseEntity.ok(atualizado)
    }

    @DeleteMapping("/{id}")
    fun desativar(@PathVariable id: String): ResponseEntity<Void> {
        val profissional = profissionalRepository.buscar(id)
            ?: return ResponseEntity.notFound().build()
        profissionalRepository.salvar(profissional.copy(ativo = false))
        return ResponseEntity.noContent().build()
    }
}
