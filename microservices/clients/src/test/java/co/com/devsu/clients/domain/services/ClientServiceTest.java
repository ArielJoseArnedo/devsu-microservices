package co.com.devsu.clients.domain.services;

import co.com.devsu.clients.domain.errors.ClientAlreadyRegisted;
import co.com.devsu.clients.domain.errors.ClientNotFound;
import co.com.devsu.clients.domain.models.Client;
import co.com.devsu.clients.domain.ports.ClientRepository;
import co.com.devsu.clients.factories.models.ClientTestFactory;
import io.vavr.control.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        this.clientService = new ClientService(clientRepository);
    }

    @Test
    @DisplayName("Tests if the registration of new client  has been successfully completed")
    void successfullyRegisterNewClientTest() {
        final Client client = new ClientTestFactory()
          .get();

        when(clientRepository.findByIdentification(any())).thenReturn(Mono.just(Option.none()));
        when(clientRepository.save(any())).thenReturn(Mono.just(client));

        final Mono<Client> clientMonoResult = clientService.registerClient(client);

        StepVerifier.create(clientMonoResult)
          .assertNext(clientRegisted -> assertThat(clientRegisted).isEqualTo(client))
          .verifyComplete();
    }

    @Test
    @DisplayName("Tests if the registration of already registered client fails")
    void failureToRegisterNewClientTest () {
        final Client client = new ClientTestFactory()
          .get();

        when(clientRepository.findByIdentification(any())).thenReturn(Mono.just(Option.of(client)));

        final Mono<Client> clientMonoResult = clientService.registerClient(client);

        StepVerifier.create(clientMonoResult)
          .expectError(ClientAlreadyRegisted.class)
          .verify();
    }


    @Test
    @DisplayName("Tests the update of an already registered client has been performed correctly")
    void successfullyUpdateClientTest() {
        final Client client = new ClientTestFactory()
          .get();

        when(clientRepository.findById(any())).thenReturn(Mono.just(Option.of(client)));
        when(clientRepository.update(any())).thenReturn(Mono.just(client));

        final Mono<Client> clientMonoResult = clientService.updateClient(client);

        StepVerifier.create(clientMonoResult)
          .assertNext(clientRegisted -> assertThat(clientRegisted).isEqualTo(client))
          .verifyComplete();
    }

    @Test
    @DisplayName("Tests if the upgrade of an unregistered client fails")
    void failureToUpdateUnregisteredClientTest() {
        final Client client = new ClientTestFactory()
          .get();

        when(clientRepository.findById(any())).thenReturn(Mono.just(Option.none()));

        final Mono<Client> clientMonoResult = clientService.updateClient(client);

        StepVerifier.create(clientMonoResult)
          .expectError(ClientNotFound.class)
          .verify();
    }

    @Test
    @DisplayName("Tests the delete of an already registered client has been performed correctly")
    void successfullyToDeleteClientTest() {
        final Client client = new ClientTestFactory()
          .get();

        when(clientRepository.findById(any())).thenReturn(Mono.just(Option.of(client)));
        when(clientRepository.delete(any())).thenReturn(Mono.just(client));


        final Mono<Client> clientMonoResult = clientService.deleteClient(client.getClientId());

        StepVerifier.create(clientMonoResult)
          .assertNext(clientRegisted -> assertThat(clientRegisted).isEqualTo(client))
          .verifyComplete();
    }

    @Test
    @DisplayName("Tests if the delete of an unregistered client fails")
    void failureToDeleteUnregisteredClientTest() {
        final Client client = new ClientTestFactory()
          .get();

        when(clientRepository.findById(any())).thenReturn(Mono.just(Option.none()));

        final Mono<Client> clientMonoResult = clientService.deleteClient(client.getClientId());

        StepVerifier.create(clientMonoResult)
          .expectError(ClientNotFound.class)
          .verify();
    }

    @Test
    void getAllClientsTest() {
        final Client client = new ClientTestFactory()
          .setClientId(UUID.randomUUID().toString())
          .get();
        final Client secondclient = new ClientTestFactory()
          .setClientId(UUID.randomUUID().toString())
          .get();

        when(clientRepository.findAll()).thenReturn(Flux.just(client, secondclient));

        final Flux<Client> clientMonoResult = clientService.getOneOrClients(Option.none());

        StepVerifier.create(clientMonoResult)
          .assertNext(clientResult -> assertThat(clientResult).isEqualTo(client))
          .assertNext(clientResult -> assertThat(clientResult).isEqualTo(secondclient))
          .expectComplete()
          .verify();
    }

    @Test
    void getOneClientTest() {
        final Client client = new ClientTestFactory()
          .get();

        when(clientRepository.findById(any())).thenReturn(Mono.just(Option.of(client)));

        final Flux<Client> clientMonoResult = clientService.getOneOrClients(Option.of(client.getClientId()));

        StepVerifier.create(clientMonoResult)
          .assertNext(clientResult -> assertThat(clientResult).isEqualTo(client))
          .expectComplete()
          .verify();
    }
}