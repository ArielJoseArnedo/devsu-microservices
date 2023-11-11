package co.com.devsu.clients.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public sealed class Person permits Client {
    protected String name;
    protected String identification;
    protected Gender gender;
    protected int age;
    protected String address;
    protected String phone;
}
