package co.com.devsu.clients.infrastructure.persistence.persons;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "persons")
public class PersonRecord {

    @Id
    private String identification;
    private String name;
    private String gender;
    private int age;
    private String address;
    private String phone;
}
