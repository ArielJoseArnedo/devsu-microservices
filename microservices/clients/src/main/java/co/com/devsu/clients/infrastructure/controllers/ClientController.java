package co.com.devsu.clients.infrastructure.controllers;

import co.com.devsu.clients.domain.services.ClientService;
import co.com.devsu.clients.infrastructure.acl.dtos.requests.RegisterClientRequest;
import co.com.devsu.clients.infrastructure.acl.dtos.requests.DeleteClientRequest;
import co.com.devsu.clients.infrastructure.acl.dtos.requests.UpdateClientRequest;
import co.com.devsu.clients.infrastructure.acl.dtos.responses.GetClientResonse;
import co.com.devsu.clients.infrastructure.acl.dtos.responses.RegisterClientResponse;
import co.com.devsu.clients.infrastructure.acl.dtos.responses.DeleteClienteResponse;
import co.com.devsu.clients.infrastructure.acl.transformers.ClientTransformer;
import co.com.devsu.clients.infrastructure.events.ClientDeleted;
import co.com.devsu.clients.infrastructure.events.ClientRegisted;
import co.com.devsu.clients.infrastructure.events.ClientUpdated;
import co.com.devsu.clients.infrastructure.messaging.publishers.Publisher;
import io.vavr.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
public class ClientController {

    private final ClientService clientService;
    private final ClientTransformer clientTransformer;
    private final Publisher publisher;

    @Autowired
    public ClientController(ClientService clientService, ClientTransformer clientTransformer, Publisher publisher) {
        this.clientService = clientService;
        this.clientTransformer = clientTransformer;
        this.publisher = publisher;
    }

    @GetMapping(value = "/clients", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<GetClientResonse> getClient(@RequestParam(name = "id", required = false) Optional<String> clientOpt) {
        return clientService.getOneOrClients(Option.ofOptional(clientOpt))
          .map(clientTransformer::toDto);
    }

    @PostMapping(value = "/clients", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<RegisterClientResponse>> registerClient(@RequestBody RegisterClientRequest registerClientRequest) {
        return Mono.just(clientTransformer.toDomain(registerClientRequest))
          .flatMap(clientService::registerClient)
          .doOnSuccess(client -> publisher.publish(new ClientRegisted(client)))
          .map(client -> new RegisterClientResponse(client.getClientId(), client.getName()))
          .map(ResponseEntity::ok)
          .defaultIfEmpty(ResponseEntity.notFound().build())
          .log();
    }

    @PutMapping(value = "/clients", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<GetClientResonse>> updateClient(@RequestBody UpdateClientRequest updateClientRequest) {
        return Mono.just(clientTransformer.toDomain(updateClientRequest))
          .flatMap(clientService::updateClient)
          .doOnSuccess(client -> publisher.publish(new ClientUpdated(client)))
          .map(clientTransformer::toDto)
          .map(ResponseEntity::ok)
          .defaultIfEmpty(ResponseEntity.notFound().build())
          .log();
    }

    @DeleteMapping(value = "/clients", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DeleteClienteResponse>> deleteClient(@RequestBody DeleteClientRequest deleteClientRequest) {
        return clientService.deleteClient(deleteClientRequest.getClientId())
          .doOnSuccess(client -> publisher.publish(new ClientDeleted(client)))
          .map(client -> new DeleteClienteResponse(client.getClientId()))
          .map(ResponseEntity::ok)
          .defaultIfEmpty(ResponseEntity.notFound().build())
          .log();
    }
}
