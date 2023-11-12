package co.com.devsu.movements.infrastructure.acl.services;

import co.com.devsu.movements.domain.services.AccountService;
import co.com.devsu.movements.infrastructure.acl.dtos.response.MovementReportReponse;
import co.com.devsu.movements.infrastructure.acl.dtos.response.ReportResponse;
import co.com.devsu.movements.infrastructure.acl.transformer.ReportTransformer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

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

    public Flux<JsonNode> getReport(String clientId,
                                    Option<String> numberAccountOpt,
                                    Option<LocalDate> dateStartOpt,
                                    Option<LocalDate> dateEndOpt
    ) {
        return numberAccountOpt
          .map(numberAccount -> getReportMovements(clientId, numberAccount,  dateStartOpt, dateEndOpt))
          .getOrElse(() -> getReportAccounts(clientId))
          .map(objectMapper::valueToTree);
    }

    public Flux<ReportResponse> getReportMovements(String clientId, String numberAccount, Option<LocalDate> dateStartOpt, Option<LocalDate> dateEndOpt) {
        return accountService.getAccountByClient(clientId, numberAccount)
          .flatMapIterable(accountOpt -> accountOpt
            .map(account -> account.getMovements()
              .filter(movement -> filferDownward(dateStartOpt, movement.getRegistrationDate().toLocalDate()))
              .filter(movement -> filferUpward(dateEndOpt, movement.getRegistrationDate().toLocalDate()))
              .map(movement -> reportTransformer.toReportMovements(account, movement))
            ).getOrElse(List.empty())
          );
    }

    public Flux<ReportResponse> getReportAccounts(String clientId) {
        return accountService.getAccountsByClient(clientId)
          .map(reportTransformer::toReportAccount);
    }

    private boolean filferDownward(Option<LocalDate> localDateOpt, LocalDate value) {
        return localDateOpt
          .map(localDate -> value.compareTo(localDate) >= 0)
          .getOrElse(true);
    }

    private boolean filferUpward(Option<LocalDate> localDateOpt, LocalDate value) {
        return localDateOpt
          .map(localDate -> value.compareTo(localDate) <= 0)
          .getOrElse(true);
    }
}
