package br.com.boasalasdeatendimento.chatbot.repository

import br.com.boasalasdeatendimento.chatbot.model.Profissional
import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest

@Repository
class ProfissionalRepository(enhancedClient: DynamoDbEnhancedClient) {

    private val table: DynamoDbTable<Profissional> =
        enhancedClient.table("chatbot-profissionais", TableSchema.fromBean(Profissional::class.java))

    fun buscar(profissionalId: String): Profissional? =
        table.getItem(Key.builder().partitionValue(profissionalId).build())

    fun listarAtivos(): List<Profissional> =
        table.scan(ScanEnhancedRequest.builder().build())
            .items()
            .filter { it.ativo }
            .toList()

    fun listarTodos(): List<Profissional> =
        table.scan().items().toList()

    fun salvar(profissional: Profissional) {
        table.putItem(profissional)
    }

    fun deletar(profissionalId: String) {
        table.deleteItem(Key.builder().partitionValue(profissionalId).build())
    }
}
