package co.com.devsu.movements.infrastructure.acl.transformer;

import co.com.devsu.movements.domain.models.Account;
import co.com.devsu.movements.domain.models.AccountState;
import co.com.devsu.movements.domain.models.AccountType;
import co.com.devsu.movements.infrastructure.acl.dtos.request.RegisterAccountRequest;
import co.com.devsu.movements.infrastructure.acl.dtos.request.UpdateAccountRequest;
import co.com.devsu.movements.infrastructure.acl.dtos.response.DeleteAccountResponse;
import co.com.devsu.movements.infrastructure.acl.dtos.response.GetAccountResponse;
import io.vavr.collection.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.stream.DoubleStream;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountTransformer {

    default Account toDomain(RegisterAccountRequest registerAccountRequest) {
        return Account.builder()
          .clientId(registerAccountRequest.getClientId())
          .clientName(registerAccountRequest.getClientName())
          .numberAccount(registerAccountRequest.getNumberAccount())
          .accountType(AccountType.get(registerAccountRequest.getAccountType()))
          .generalBalance(registerAccountRequest.getOpeningBalance())
          .state(AccountState.ACTIVE)
          .movements(List.empty())
          .build();
    }

    default Account toDomain(UpdateAccountRequest updateAccountRequest) {
        return Account.builder()
          .clientId(updateAccountRequest.getClientId())
          .clientName(updateAccountRequest.getClientName())
          .numberAccount(updateAccountRequest.getNumberAccount())
          .accountType(AccountType.get(updateAccountRequest.getAccountType()))
          .state(AccountState.get(updateAccountRequest.getState()))
          .movements(List.empty())
          .build();
    }

    default GetAccountResponse toDtoGetAccountResponse(Account account) {
        return GetAccountResponse.builder()
          .clientId(account.getClientId())
          .clientName(account.getClientName())
          .numberAccount(account.getNumberAccount())
          .accountType(account.getAccountType().name())
          .generalBalance(account.getGeneralBalance())
          .state(account.getState().name())
          .build();
    }

    default DeleteAccountResponse toDtoDeleteAccountResponse(Account account) {
        return DeleteAccountResponse.builder()
          .clientId(account.getClientId())
          .numberAccount(account.getNumberAccount())
          .build();
    }
}
