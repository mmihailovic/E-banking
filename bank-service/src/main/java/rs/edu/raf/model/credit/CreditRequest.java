package rs.edu.raf.model.credit;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.edu.raf.model.Currency;
import rs.edu.raf.model.accounts.BankAccount;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class CreditRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long clientId;
    @ManyToOne
    private CreditType creditType;
    private BigDecimal loanAmount;
    @ManyToOne
    private Currency currency;
    private String loanPurpose;
    private BigDecimal salary;
    private String phoneNumber;
    private boolean permanentEmployee;
    private int currentEmploymentPeriod;
    private int loanTerm;
    @ManyToOne
    private BankAccount bankAccount;
    @Enumerated
    private CreditRequestStatus creditRequestStatus;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "creditRequest")
    private List<Credit> credits;
}
