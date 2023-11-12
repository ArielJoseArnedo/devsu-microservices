package co.com.devsu.movements.infrastructure.acl.transformer;

import co.com.devsu.movements.domain.models.Account;
import co.com.devsu.movements.domain.models.Movement;
import co.com.devsu.movements.infrastructure.acl.dtos.response.AccountReportResponse;
import co.com.devsu.movements.infrastructure.acl.dtos.response.MovementReportReponse;
import co.com.devsu.movements.infrastructure.acl.dtos.response.ReportResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.time.format.DateTimeFormatter;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReportTransformer {

    default ReportResponse toReportAccount(Account account) {
        return AccountReportResponse.builder()
          .numberAccount(account.getNumberAccount())
          .accountType(account.getAccountType())
          .balance(account.getBalance())
          .state(account.getState())
          .client("")
          .build();
    }

    default ReportResponse toReportMovements(Account account, Movement movement) {
        return MovementReportReponse.builder()
          .date(movement.getRegistrationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
          .client("")
          .numberAccount(account.getNumberAccount())
          .accountType(account.getAccountType())
          .openingBalance(0.0)
          .state(account.getState())
          .movement(movement.getAmount())
          .balance(account.getBalance())
          .build();
    }
}
