package co.com.devsu.clients.controllers;

import co.com.devsu.clients.domain.models.Client;
import co.com.devsu.clients.domain.ports.ClientRepository;
import co.com.devsu.clients.domain.services.ClientService;
import co.com.devsu.clients.factories.models.ClientTestFactory;
import co.com.devsu.clients.factories.resquests.RegisterClientTestFactory;
import co.com.devsu.clients.infrastructure.acl.dtos.requests.DeleteClientRequest;
import co.com.devsu.clients.infrastructure.acl.dtos.requests.UpdateClientRequest;
import co.com.devsu.clients.infrastructure.acl.dtos.responses.DeleteClienteResponse;
import co.com.devsu.clients.infrastructure.acl.dtos.responses.GetClientResonse;
import co.com.devsu.clients.infrastructure.acl.dtos.responses.RegisterClientResponse;
import co.com.devsu.clients.infrastructure.controllers.ClientController;
import io.vavr.control.Option;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@WebFluxTest(controllers = ClientController.class)
@Import({ClientService.class, WebClientTestConfiguration.class})
class ClientControllerIntegrationTest {

    @MockBean
    private ClientRepository clientRepository;

    @Autowired
    private WebTestClient webClient;


    @Test
    void getClient() {
        final Client givenFirstClientRegisted = new ClientTestFactory()
          .get();
        final Client givenSecondClientRegisted = new ClientTestFactory()
          .get();

        when(clientRepository.findAll()).thenReturn(Flux.just(givenFirstClientRegisted, givenSecondClientRegisted));

        webClient.get()
          .uri("/clients")
          .exchange()
          .expectStatus().isOk()
          .expectHeader().valueEquals("Content-Type", "application/json")
          .expectBodyList(GetClientResonse.class)
          .value(getClientResonses -> {
              assertThat(getClientResonses).hasSize(2);
              assertThat(getClientResonses.get(0).getClientId()).isIn(givenFirstClientRegisted.getClientId(), givenSecondClientRegisted.getClientId());
              assertThat(getClientResonses.get(1).getClientId()).isIn(givenFirstClientRegisted.getClientId(), givenSecondClientRegisted.getClientId());
          });

        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void successfullyRegisterNewClientTest() {
        final Client givenFirstClientRegisted = new ClientTestFactory()
          .get();
        when(clientRepository.findByIdentification(any())).thenReturn(Mono.just(Option.none()));
        when(clientRepository.save(any())).thenReturn(Mono.just(givenFirstClientRegisted));

        webClient.post()
          .uri("/clients")
          .contentType(MediaType.APPLICATION_JSON)
          .body(BodyInserters.fromValue(new RegisterClientTestFactory(givenFirstClientRegisted).get()))
          .exchange()
          .expectStatus().isOk()
          .expectHeader().valueEquals("Content-Type", "application/json")
          .expectBody(RegisterClientResponse.class)
          .value(registerClientResponse -> {
                assertThat(registerClientResponse.getClientId()).isEqualTo(givenFirstClientRegisted.getClientId());
                assertThat(registerClientResponse.getName()).isEqualTo(givenFirstClientRegisted.getName());
          });

        verify(clientRepository, times(1)).findByIdentification(any());
        verify(clientRepository, times(1)).save(any());
    }

    @Test
    void updateClient() {
        final Client givenFirstClientRegisted = new ClientTestFactory()
          .get();
        final UpdateClientRequest nameUptete = UpdateClientRequest.builder()
          .clientId(givenFirstClientRegisted.getClientId())
          .phone("NameUpdate")
          .build();

        when(clientRepository.findById(any())).thenReturn(Mono.just(Option.of(givenFirstClientRegisted)));
        when(clientRepository.update(any())).thenReturn(Mono.just(givenFirstClientRegisted));

        webClient.put()
          .uri("/clients")
          .contentType(MediaType.APPLICATION_JSON)
          .body(BodyInserters.fromValue(nameUptete))
          .exchange()
          .expectStatus().isOk()
          .expectHeader().valueEquals("Content-Type", "application/json")
          .expectBody(GetClientResonse.class)
          .value(deleteClienteResponse ->
              assertThat(deleteClienteResponse.getClientId()).isEqualTo(givenFirstClientRegisted.getClientId())
          );

        verify(clientRepository, times(1)).findById(any());
        verify(clientRepository, times(1)).update(any());
    }

    @Test
    void successfullyDeleteClientTest() {
        final Client givenFirstClientRegisted = new ClientTestFactory()
          .get();

        when(clientRepository.findById(any())).thenReturn(Mono.just(Option.of(givenFirstClientRegisted)));
        when(clientRepository.delete(any())).thenReturn(Mono.just(givenFirstClientRegisted));

        webClient.method(HttpMethod.DELETE)
          .uri("/clients")
          .contentType(MediaType.APPLICATION_JSON)
          .body(BodyInserters.fromValue(new DeleteClientRequest(givenFirstClientRegisted.getClientId())))
          .exchange()
          .expectStatus().isOk()
          .expectHeader().valueEquals("Content-Type", "application/json")
          .expectBody(DeleteClienteResponse.class)
          .value(deleteClienteResponse ->
            assertThat(deleteClienteResponse.getClientId()).isEqualTo(givenFirstClientRegisted.getClientId())
          );

        verify(clientRepository, times(1)).findById(any());
        verify(clientRepository, times(1)).delete(any());
    }
}