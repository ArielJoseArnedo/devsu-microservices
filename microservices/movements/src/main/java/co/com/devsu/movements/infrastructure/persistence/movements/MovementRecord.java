package co.com.devsu.movements.infrastructure.persistence.movements;

import co.com.devsu.movements.infrastructure.persistence.accounts.AccountRecord;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "movements")
public class MovementRecord {

    @EmbeddedId
    MovementKey movementKey;

    @Column(name = "movement_type")
    private String movementType;

    @Column(name = "amount")
    private double amount;

    @Column(name = "balance")
    private double balance;

    @Column(name = "registration_date")
    private Timestamp registrationDate;

    @ManyToOne
    @JoinColumns({
      @JoinColumn(name = "client_id", referencedColumnName = "client_id", insertable = false, updatable = false),
      @JoinColumn(name = "number_account", referencedColumnName = "number_account", insertable = false, updatable = false),
    })
    private AccountRecord accountRecord;
}
