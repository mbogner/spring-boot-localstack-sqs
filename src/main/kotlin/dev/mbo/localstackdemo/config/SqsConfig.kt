package dev.mbo.localstackdemo.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.sqs.AmazonSQSAsync
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder
import io.awspring.cloud.messaging.config.QueueMessageHandlerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.converter.StringMessageConverter
import org.springframework.messaging.handler.annotation.support.PayloadArgumentResolver
import org.springframework.messaging.handler.annotation.support.PayloadMethodArgumentResolver
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver


@Configuration
class SqsConfig {

    @Bean
    fun endpointConfiguration(): EndpointConfiguration {
        return EndpointConfiguration("http://localhost:4566", "eu-west-1")
    }

    @Bean(destroyMethod = "shutdown")
    fun amazonSQS(endpointConfiguration: EndpointConfiguration): AmazonSQSAsync {
        return AmazonSQSAsyncClientBuilder.standard()
            .withEndpointConfiguration(endpointConfiguration)
            .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials("foo", "bar")))
            .build();
    }

    @Bean
    fun queueMessageHandlerFactory(): QueueMessageHandlerFactory {
        val factory = QueueMessageHandlerFactory()
        val messageConverter = StringMessageConverter()

        //set strict content type match to false
        messageConverter.isStrictContentTypeMatch = false
        factory.setArgumentResolvers(
            listOf<HandlerMethodArgumentResolver>(
                PayloadMethodArgumentResolver(
                    messageConverter
                )
            )
        )
        return factory
    }

}