package br.com.boasalasdeatendimento.chatbot.admin.controller

import br.com.boasalasdeatendimento.chatbot.service.SlotGeneratorService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/slots")
class AdminSlotController(
    private val slotGeneratorService: SlotGeneratorService
) {

    /**
     * Dispara geração manual de slots.
     * Útil após cadastrar um novo profissional ou ajustar horários.
     *
     * POST /admin/slots/gerar?diasAFrente=14&apenasAtivos=true
     */
    @PostMapping("/gerar")
    fun gerarSlots(
        @RequestParam(defaultValue = "14") diasAFrente: Int,
        @RequestParam(defaultValue = "true") apenasAtivos: Boolean
    ): ResponseEntity<Map<String, Any>> {
        slotGeneratorService.gerarSlots(diasAFrente = diasAFrente, apenasAtivos = apenasAtivos)
        return ResponseEntity.ok(mapOf(
            "status" to "ok",
            "diasAFrente" to diasAFrente,
            "apenasAtivos" to apenasAtivos
        ))
    }
}
