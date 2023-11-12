package co.com.devsu.movements.infrastructure.controllers;

import co.com.devsu.movements.domain.services.MovementService;
import co.com.devsu.movements.infrastructure.acl.dtos.request.RegisterMovementRequest;
import co.com.devsu.movements.infrastructure.acl.dtos.response.RegisterMovementResponse;
import co.com.devsu.movements.infrastructure.acl.transformer.MovementTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class MovementController {

    private final MovementService movementService;
    private final MovementTransformer movementTransformer;

    @Autowired
    public MovementController(MovementService movementService, MovementTransformer movementTransformer) {
        this.movementService = movementService;
        this.movementTransformer = movementTransformer;
    }

    @PostMapping(value = "/movimientos", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<RegisterMovementResponse>> registerAccount(@RequestBody RegisterMovementRequest registerMovementRequest) {
        return Mono.just(movementTransformer.toDomain(registerMovementRequest))
          .flatMap(movement -> movementService.registerMovement(movement, registerMovementRequest.getClientId(), registerMovementRequest.getNumberAccount()))
//          .doOnSuccess(client -> publisher.publish(new ClientRegisted(client)))
          .map(movement -> new RegisterMovementResponse(movement.getAmount()))
          .map(ResponseEntity::ok)
          .defaultIfEmpty(ResponseEntity.notFound().build())
          .log();
    }
}
