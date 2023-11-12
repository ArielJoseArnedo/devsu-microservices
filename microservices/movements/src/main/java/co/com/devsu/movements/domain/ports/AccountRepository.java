package co.com.devsu.movements.domain.ports;

import co.com.devsu.movements.domain.models.Account;
import io.vavr.control.Option;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountRepository {
    Mono<Account> registerAccount(Account account);
    Mono<Option<Account>> findByClientAndNumberAccount(String clientId, String numberAccount);
    Mono<Option<Account>> findByNumberAccount(String numberAccount);
    Flux<Account> findByClient(String clientId);
    Mono<Account> update(Account account);
    Mono<Account> delete(Account account);
}
