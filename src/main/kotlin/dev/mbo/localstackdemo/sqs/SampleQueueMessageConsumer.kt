package dev.mbo.localstackdemo.sqs

import io.awspring.cloud.messaging.config.annotation.NotificationMessage
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy
import io.awspring.cloud.messaging.listener.annotation.SqsListener
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller

@Controller
class SampleQueueMessageConsumer {

    private val log = LoggerFactory.getLogger(javaClass)

    @SqsListener(value = ["sample-queue"], deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    fun consume(@NotificationMessage message: String) {
        log.info("received message: {}", message)
    }

}