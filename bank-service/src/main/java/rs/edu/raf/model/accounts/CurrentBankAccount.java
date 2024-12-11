package rs.edu.raf.model.accounts;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CurrentBankAccount extends BankAccount {
    private CurrentBankAccountType type;
    private BigDecimal maintenancePrice;
}

