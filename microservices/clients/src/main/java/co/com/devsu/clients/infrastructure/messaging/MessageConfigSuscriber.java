package co.com.devsu.clients.infrastructure.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:rabbit-config.properties")
public class MessageConfigSuscriber {

    @Value("${topic.name}")
    private String topic;

    @Value("${queue.name}")
    private String queue;

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(topic);
    }

    @Bean
    Queue queue() {
        return new Queue(queue, false);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queue);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Suscriber suscriber) {
        return new MessageListenerAdapter(suscriber, "onMessage");
    }
}
