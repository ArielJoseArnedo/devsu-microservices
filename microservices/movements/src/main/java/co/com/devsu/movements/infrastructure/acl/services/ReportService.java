package co.com.devsu.movements.infrastructure.acl.services;

import co.com.devsu.movements.domain.services.AccountService;
import co.com.devsu.movements.infrastructure.acl.transformer.ReportTransformer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class ReportService {

    private final AccountService accountService;
    private final ReportTransformer reportTransformer;

    private final ObjectMapper objectMapper;

    @Autowired
    public ReportService(AccountService accountService, ReportTransformer reportTransformer, ObjectMapper objectMapper) {
        this.accountService = accountService;
        this.reportTransformer = reportTransformer;
        this.objectMapper = objectMapper;
    }

    public Flux<JsonNode> getReport(String clientId, Option<String> numberAccountOpt) {
        return numberAccountOpt
          .map(numberAccount -> getReportMovements(clientId, numberAccount))
          .getOrElse(() -> getReportAccounts(clientId));
    }

    public Flux<JsonNode> getReportMovements(String clientId, String numberAccount) {
        return accountService.getAccountByClient(clientId, numberAccount)
          .flatMapIterable(accountOpt -> accountOpt
            .map(account -> account.getMovements()
              .map(movement -> reportTransformer.toReportMovements(account, movement))
            ).getOrElse(List.empty())
          ).map(objectMapper::valueToTree);
    }

    public Flux<JsonNode> getReportAccounts(String clientId) {
        return accountService.getAccountsByClient(clientId)
          .map(reportTransformer::toReportAccount)
          .map(objectMapper::valueToTree);
    }
}
