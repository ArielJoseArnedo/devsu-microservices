package co.com.devsu.clients.factories.resquests;

import co.com.devsu.clients.domain.models.Client;
import co.com.devsu.clients.domain.models.ClientState;
import co.com.devsu.clients.domain.models.Gender;
import co.com.devsu.clients.infrastructure.acl.dtos.requests.RegisterClientRequest;
import co.com.devsu.clients.infrastructure.acl.transformers.ClientTransformer;
import io.vavr.collection.List;
import net.datafaker.Faker;

import java.util.UUID;

public class RegisterClientTestFactory implements ClientTransformer {

    private String clientId;
    private String password;
    private String state;
    protected String name;
    protected String identification;
    protected String gender;
    protected int age;
    protected String address;
    protected String phone;

    public RegisterClientTestFactory() {
        final Faker faker = new Faker();
        this.clientId = UUID.randomUUID().toString();
        this.password = faker.lorem().characters(12, 20);
        this.state = List.of(ClientState.values()).shuffle().get().name();
        this.name = faker.name().fullName();
        this.identification = faker.idNumber().peselNumber();
        this.gender = List.of(Gender.values()).shuffle().get().name();
        this.age = faker.random().nextInt(18, 100);
        this.address = faker.address().fullAddress();
        this.phone = faker.phoneNumber().phoneNumber();
    }

    public RegisterClientTestFactory(Client client) {
        this.clientId = client.getClientId();
        this.password = client.getPassword();
        this.state = client.getPassword();
        this.name = client.getName();
        this.identification = client.getIdentification();
        this.gender = client.getGender().name();
        this.age = client.getAge();
        this.address = client.getAddress();
        this.phone = client.getPhone();
    }

    public RegisterClientRequest get() {
        return RegisterClientRequest.builder()
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
}
