package rs.edu.raf.model.accounts;


import jakarta.persistence.*;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ForeignCurrencyBankAccount extends BankAccount {
    private BigDecimal maintenancePrice;
}
