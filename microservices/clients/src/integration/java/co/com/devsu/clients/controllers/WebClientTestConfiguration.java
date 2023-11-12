package co.com.devsu.clients.controllers;

import co.com.devsu.clients.infrastructure.acl.transformers.ClientTransformer;
import co.com.devsu.clients.infrastructure.messaging.publishers.Publisher;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class WebClientTestConfiguration {

    @MockBean
    Publisher publisher;

    @Bean
    ClientTransformer clientTransformer() {
        return new ClientTransformerImp();
    }

    @Bean
    Publisher publisher() {
        return publisher;
    }

    static class ClientTransformerImp implements ClientTransformer {}
}
