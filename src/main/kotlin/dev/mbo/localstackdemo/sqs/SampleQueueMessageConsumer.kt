package dev.mbo.localstackdemo.sqs

import dev.mbo.localstackdemo.config.SqsConfig
import io.awspring.cloud.messaging.config.annotation.NotificationMessage
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy
import io.awspring.cloud.messaging.listener.annotation.SqsListener
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller

@Controller
class SampleQueueMessageConsumer {

    private val log = LoggerFactory.getLogger(javaClass)

    @SqsListener(value = [SqsConfig.QUEUE_NAME], deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    fun consume(@NotificationMessage message: String) {
        log.info("received message: {}", message)
    }

}