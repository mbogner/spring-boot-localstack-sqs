package dev.mbo.localstackdemo.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.sqs.AmazonSQSAsync
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder
import io.awspring.cloud.core.env.ResourceIdResolver
import io.awspring.cloud.messaging.config.QueueMessageHandlerFactory
import io.awspring.cloud.messaging.core.QueueMessagingTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.converter.MessageConverter
import org.springframework.messaging.converter.StringMessageConverter
import org.springframework.messaging.handler.annotation.support.PayloadMethodArgumentResolver
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver

@Configuration
class SqsConfig {

    companion object {
        const val QUEUE_NAME = "sample-queue"
    }

    @Bean(destroyMethod = "shutdown")
    fun amazonSQS(
        @Value("\${cloud.aws.sqs.endpoint}") sqsEndpoint: String,
        @Value("\${cloud.aws.sqs.region}") region: String,
        @Value("\${cloud.aws.credentials.access-key}") accessKey: String,
        @Value("\${cloud.aws.credentials.secret-key}") secretKey: String,
    ): AmazonSQSAsync {
        return AmazonSQSAsyncClientBuilder.standard()
            .withEndpointConfiguration(EndpointConfiguration(sqsEndpoint, region))
            .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(accessKey, secretKey)))
            .build()
    }

    @Bean
    fun queueMessageHandlerFactory(messageConverter: MessageConverter): QueueMessageHandlerFactory {
        val factory = QueueMessageHandlerFactory()
        factory.setArgumentResolvers(
            listOf<HandlerMethodArgumentResolver>(PayloadMethodArgumentResolver(messageConverter))
        )
        return factory
    }

    @Bean
    fun queueMessagingTemplate(sqs: AmazonSQSAsync, messageConverter: MessageConverter): QueueMessagingTemplate {
        return QueueMessagingTemplate(sqs, null as ResourceIdResolver?, messageConverter)
    }

    @Bean
    fun messageConverter(): MessageConverter {
        val converter = StringMessageConverter()
        converter.isStrictContentTypeMatch = false
        return converter
    }

}