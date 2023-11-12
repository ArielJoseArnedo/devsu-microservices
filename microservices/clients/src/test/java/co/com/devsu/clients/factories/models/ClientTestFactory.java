package co.com.devsu.clients.factories.models;

import co.com.devsu.clients.domain.models.Client;
import co.com.devsu.clients.domain.models.ClientState;
import co.com.devsu.clients.domain.models.Gender;
import io.vavr.collection.List;
import lombok.Builder;
import net.datafaker.Faker;


import java.util.UUID;

public class ClientTestFactory {
    private String clientId;
    private String password;
    private ClientState state;
    private String name;
    private String identification;
    private Gender gender;
    private int age;
    private String address;
    private String phone;

    public ClientTestFactory() {
        final Faker faker = new Faker();
        this.clientId = UUID.randomUUID().toString();
        this.state = List.of(ClientState.values()).shuffle().get();
        this.name = faker.name().fullName();
        this.identification = faker.idNumber().peselNumber();
        this.gender = List.of(Gender.values()).shuffle().get();
        this.age = faker.random().nextInt(18, 100);
        this.address = faker.address().fullAddress();
        this.phone = faker.phoneNumber().phoneNumber();
    }

    public Client get() {
        return Client.builder()
          .clientId(clientId)
          .password(password)
          .state(state)
          .name(name)
          .identification(identification)
          .gender(gender)
          .age(age)
          .address(address)
          .phone(phone)
          .build();
    }

    public ClientTestFactory setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public ClientTestFactory setPassword(String password) {
        this.password = password;
        return this;
    }

    public ClientTestFactory setState(ClientState state) {
        this.state = state;
        return this;
    }

    public ClientTestFactory setName(String name) {
        this.name = name;
        return this;
    }

    public ClientTestFactory setIdentification(String identification) {
        this.identification = identification;
        return this;
    }

    public ClientTestFactory setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public ClientTestFactory setAge(int age) {
        this.age = age;
        return this;
    }

    public ClientTestFactory setAddress(String address) {
        this.address = address;
        return this;
    }

    public ClientTestFactory setPhone(String phone) {
        this.phone = phone;
        return this;
    }
}
