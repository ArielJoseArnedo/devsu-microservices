package co.com.devsu.movements.infrastructure.acl.transformer;

import co.com.devsu.movements.domain.models.Account;
import co.com.devsu.movements.infrastructure.acl.dtos.request.RegisterAccountRequest;
import co.com.devsu.movements.infrastructure.acl.dtos.request.UpdateAccountRequest;
import io.vavr.collection.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountTransformer {

    default Account toDomain(RegisterAccountRequest registerAccountRequest) {
        return Account.builder()
          .clientId(registerAccountRequest.getClientId())
          .numberAccount(registerAccountRequest.getNumberAccount())
          .accountType(registerAccountRequest.getAccountType())
          .balance(registerAccountRequest.getOpeningBalance())
          .state("ACTIVE")
          .movements(List.empty())
          .build();
    }

    default Account toDomain(UpdateAccountRequest updateAccountRequest) {
        return Account.builder()
          .clientId(updateAccountRequest.getClientId())
          .numberAccount(updateAccountRequest.getNumberAccount())
          .accountType(updateAccountRequest.getAccountType())
          .state(updateAccountRequest.getState())
          .movements(List.empty())
          .build();
    }
}
