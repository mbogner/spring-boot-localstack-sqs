package dev.mbo.localstackdemo.controller

import dev.mbo.localstackdemo.config.SqsConfig
import io.awspring.cloud.messaging.core.QueueMessagingTemplate
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class SampleQueueMessageProducer constructor(
    private val sqsSender: QueueMessagingTemplate
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping(path = ["/send"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun send(): ResponseEntity<SendResponse> {
        val message = UUID.randomUUID().toString()
        log.info("sending message: {}", message)
        sqsSender.convertAndSend(SqsConfig.QUEUE_NAME, message, mapOf(), null)
        log.info("sent message: {}", message)
        return ResponseEntity.ok(SendResponse(message))
    }

    data class SendResponse(
        val message: String
    )

}