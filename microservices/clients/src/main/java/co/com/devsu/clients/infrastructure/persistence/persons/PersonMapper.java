package co.com.devsu.clients.infrastructure.persistence.persons;

import co.com.devsu.clients.domain.models.Client;
import org.springframework.stereotype.Component;

public interface PersonMapper {

    default PersonRecord toRecordPerson(Client client) {
        return PersonRecord.builder()
          .identification(client.getIdentification())
          .name(client.getName())
          .gender(client.getGender().name())
          .age(client.getAge())
          .address(client.getAddress())
          .phone(client.getPhone())
          .build();
    }
}
