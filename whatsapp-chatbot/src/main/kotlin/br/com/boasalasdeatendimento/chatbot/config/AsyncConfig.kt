package br.com.boasalasdeatendimento.chatbot.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@Configuration
@EnableAsync
@EnableScheduling
class AsyncConfig {

    /**
     * Pool dedicado para processamento de webhooks do WhatsApp.
     * O controller retorna 200 imediatamente (exigência do Meta: < 5 segundos),
     * enquanto este pool processa a lógica de negócio em background.
     */
    @Bean("webhookExecutor")
    fun webhookExecutor(): Executor {
        return ThreadPoolTaskExecutor().apply {
            corePoolSize = 5
            maxPoolSize = 20
            queueCapacity = 200
            setThreadNamePrefix("whatsapp-webhook-")
            setWaitForTasksToCompleteOnShutdown(true)
            setAwaitTerminationSeconds(30)
            initialize()
        }
    }

    /**
     * Pool para geração de slots de disponibilidade (@Scheduled).
     */
    @Bean("slotGeneratorExecutor")
    fun slotGeneratorExecutor(): Executor {
        return ThreadPoolTaskExecutor().apply {
            corePoolSize = 2
            maxPoolSize = 5
            queueCapacity = 10
            setThreadNamePrefix("slot-generator-")
            initialize()
        }
    }
}
