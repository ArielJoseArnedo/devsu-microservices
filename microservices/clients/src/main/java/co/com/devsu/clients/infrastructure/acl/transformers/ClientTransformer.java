package co.com.devsu.clients.infrastructure.acl.transformers;

import co.com.devsu.clients.domain.models.Client;
import co.com.devsu.clients.domain.models.ClientState;
import co.com.devsu.clients.domain.models.Gender;
import co.com.devsu.clients.infrastructure.acl.dtos.requests.RegisterClientRequest;
import co.com.devsu.clients.infrastructure.acl.dtos.requests.UpdateClientRequest;
import co.com.devsu.clients.infrastructure.acl.dtos.responses.GetClientResonse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClientTransformer {

    default Client toDomain(RegisterClientRequest registerClientRequest) {
        return Client.builder()
          .clientId(registerClientRequest.getClientId())
          .password(registerClientRequest.getPassword())
          .state(ClientState.get(registerClientRequest.getState()))
          .name(registerClientRequest.getName())
          .identification(registerClientRequest.getIdentification())
          .gender(Gender.get(registerClientRequest.getGender()))
          .age(registerClientRequest.getAge())
          .address(registerClientRequest.getAddress())
          .phone(registerClientRequest.getPhone())
          .build();
    }

    default Client toDomain(UpdateClientRequest updateClientRequest) {
        return Client.builder()
          .clientId(updateClientRequest.getClientId())
          .password(updateClientRequest.getPassword())
          .state(ClientState.get(updateClientRequest.getState()))
          .name(updateClientRequest.getName())
          .gender(Gender.get(updateClientRequest.getGender()))
          .age(updateClientRequest.getAge())
          .address(updateClientRequest.getAddress())
          .phone(updateClientRequest.getPhone())
          .build();
    }

    default GetClientResonse toDto(Client client) {
        return GetClientResonse.builder()
          .clientId(client.getClientId())
          .state(client.getState().name())
          .name(client.getName())
          .identification(client.getIdentification())
          .gender(client.getGender().name())
          .age(client.getAge())
          .address(client.getAddress())
          .phone(client.getPhone())
          .build();
    }
}
