package co.com.devsu.clients.infrastructure.messaging.publishers;

import co.com.devsu.clients.infrastructure.events.Event;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@PropertySource("classpath:rabbit-config.properties")
public class Publisher {

    @Value("${topic.name}")
    private String exchange;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public Publisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void publish(Event message) {
        log.info("The message ({}): {} is published in the topic: {}",message.getName(), message.getMessage().toString(), exchange);
        Mono.just(objectMapper.convertValue(message, JsonNode.class))
          .subscribe(jsonNode-> rabbitTemplate.convertAndSend(exchange, "", jsonNode.toString()));
    }
}
