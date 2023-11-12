package co.com.devsu.movements.infrastructure.persistence.accounts;

import co.com.devsu.movements.infrastructure.persistence.movements.MovementRecord;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "accounts")
public class AccountRecord {

    @EmbeddedId
    private AccountKey accountKey;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "balance")
    private double balance;

    @Column(name = "state")
    private String state;

    @OneToMany(
      fetch = FetchType.EAGER,
      cascade = {CascadeType.ALL},
      mappedBy = "accountRecord"
    )
    private List<MovementRecord> movementRecords;
}
