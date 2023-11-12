package co.com.devsu.movements.infrastructure.controllers;


import co.com.devsu.movements.domain.services.AccountService;
import co.com.devsu.movements.infrastructure.acl.dtos.request.RegisterAccountRequest;
import co.com.devsu.movements.infrastructure.acl.dtos.request.UpdateAccountRequest;
import co.com.devsu.movements.infrastructure.acl.dtos.response.RegisterAccountResponse;
import co.com.devsu.movements.infrastructure.acl.transformer.AccountTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AccountController {

    private final AccountService accountService;
    private final AccountTransformer accountTransformer;

    @Autowired
    public AccountController(AccountService accountService, AccountTransformer accountTransformer) {
        this.accountService = accountService;
        this.accountTransformer = accountTransformer;
    }

    @PostMapping(value = "/cuentas", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<RegisterAccountResponse>> registerAccount(@RequestBody RegisterAccountRequest registerAccountRequest) {
        return Mono.just(accountTransformer.toDomain(registerAccountRequest))
          .flatMap(accountService::createAccount)
//          .doOnSuccess(client -> publisher.publish(new ClientRegisted(client)))
          .map(account -> new RegisterAccountResponse(account.getClientId()))
          .map(ResponseEntity::ok)
          .defaultIfEmpty(ResponseEntity.notFound().build())
          .log();
    }

    @PutMapping(value = "/cuentas", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<UpdateAccountRequest>> updateAccount(@RequestBody UpdateAccountRequest updateAccountRequest) {
        return Mono.just(accountTransformer.toDomain(updateAccountRequest))
          .flatMap(accountService::updateAccount)
//          .doOnSuccess(client -> publisher.publish(new ClientRegisted(client)))
          .map(account -> updateAccountRequest)
          .map(ResponseEntity::ok)
          .defaultIfEmpty(ResponseEntity.notFound().build())
          .log();
    }
}
