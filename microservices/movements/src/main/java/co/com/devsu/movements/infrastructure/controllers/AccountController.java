package co.com.devsu.movements.infrastructure.controllers;


import co.com.devsu.movements.domain.services.AccountService;
import co.com.devsu.movements.infrastructure.acl.dtos.request.DeleteAccountRequest;
import co.com.devsu.movements.infrastructure.acl.dtos.request.RegisterAccountRequest;
import co.com.devsu.movements.infrastructure.acl.dtos.request.UpdateAccountRequest;
import co.com.devsu.movements.infrastructure.acl.dtos.response.DeleteAccountResponse;
import co.com.devsu.movements.infrastructure.acl.dtos.response.GetAccountResponse;
import co.com.devsu.movements.infrastructure.acl.dtos.response.RegisterAccountResponse;
import co.com.devsu.movements.infrastructure.acl.transformer.AccountTransformer;
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
public class AccountController {

    private final AccountService accountService;
    private final AccountTransformer accountTransformer;

    @Autowired
    public AccountController(AccountService accountService, AccountTransformer accountTransformer) {
        this.accountService = accountService;
        this.accountTransformer = accountTransformer;
    }

    @PostMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<RegisterAccountResponse>> registerAccount(@RequestBody RegisterAccountRequest registerAccountRequest) {
        return Mono.just(accountTransformer.toDomain(registerAccountRequest))
          .flatMap(accountService::createAccount)
          .map(account -> new RegisterAccountResponse(account.getClientId()))
          .map(ResponseEntity::ok)
          .defaultIfEmpty(ResponseEntity.notFound().build())
          .log();
    }

    @PutMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<GetAccountResponse>> updateAccount(@RequestBody UpdateAccountRequest updateAccountRequest) {
        return Mono.just(accountTransformer.toDomain(updateAccountRequest))
          .flatMap(accountService::updateAccount)
          .map(accountTransformer::toDtoGetAccountResponse)
          .map(ResponseEntity::ok)
          .defaultIfEmpty(ResponseEntity.notFound().build())
          .log();
    }

    @DeleteMapping(value = "/accounts",  produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DeleteAccountResponse>> deleteAccount(@RequestBody DeleteAccountRequest deleteAccountRequest) {
        return accountService.deleteAccount(deleteAccountRequest.getClientId(), deleteAccountRequest.getNumberAccount())
          .map(accountTransformer::toDtoDeleteAccountResponse)
          .map(ResponseEntity::ok)
          .defaultIfEmpty(ResponseEntity.notFound().build())
          .log();
    }

    @GetMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<GetAccountResponse> getAccounts(@RequestParam(name = "clientId", required = false) Optional<String> clientOpt) {
        return accountService.getOneOrAccounts(Option.ofOptional(clientOpt))
          .map(accountTransformer::toDtoGetAccountResponse);
    }
}
