package co.com.devsu.clients.infrastructure.persistence.clients;

import co.com.devsu.clients.domain.models.Client;
import co.com.devsu.clients.domain.models.ClientState;
import co.com.devsu.clients.domain.models.Gender;
import co.com.devsu.clients.infrastructure.persistence.persons.PersonMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClientMapper extends PersonMapper {

    default ClientRecord toRecord(Client client) {
        return ClientRecord.builder()
          .clientId(client.getClientId())
          .password(client.getPassword())
          .state(client.getState().name())
          .personRecord(toRecordPerson(client))
          .build();
    }

    default Client toDomain(ClientRecord clientRecord) {
        return Client.builder()
          .clientId(clientRecord.getClientId())
          .password(clientRecord.getPassword())
          .state(ClientState.get(clientRecord.getState()))
          .identification(clientRecord.getPersonRecord().getIdentification())
          .name(clientRecord.getPersonRecord().getName())
          .gender(Gender.get(clientRecord.getPersonRecord().getGender()))
          .age(clientRecord.getPersonRecord().getAge())
          .address(clientRecord.getPersonRecord().getAddress())
          .phone(clientRecord.getPersonRecord().getPhone())
          .build();
    }
}
