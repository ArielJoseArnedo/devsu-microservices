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
          .accountType(account.getAccountType().name())
          .balance(account.getGeneralBalance())
          .state(account.getState().name())
          .client(account.getClientName())
          .build();
    }

    default ReportResponse toReportMovements(Account account, Movement movement) {
        return MovementReportReponse.builder()
          .date(movement.getRegistrationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
          .client(account.getClientName())
          .numberAccount(account.getNumberAccount())
          .accountType(account.getAccountType().name())
          .balance(account.getGeneralBalance())
          .state(account.getState().name())
          .movementType(movement.getMovementType().name())
          .movement(movement.getAmount())
          .balance(movement.getBalance())
          .build();
    }
}
