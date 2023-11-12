package co.com.devsu.movements.domain.ports;

import co.com.devsu.movements.domain.models.Movement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementRepository {
    Mono<Movement> registerMovement(Movement movement, String clientId, String numberAccount);
    Flux<Movement> findAllMovementByNumberAccount(String clientId, String numberAccount);
}
