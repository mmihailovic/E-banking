package rs.edu.raf.model.credit;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private CreditRequest creditRequest;
    private Long contractDate;
    private Long loanMaturityDate;
    private BigDecimal installmentAmount;
    private BigDecimal remainingDebt;
    private Long nextInstallmentDate;

}
