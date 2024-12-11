package rs.edu.raf.model.card;

import jakarta.persistence.*;
import lombok.*;
import rs.edu.raf.model.accounts.BankAccount;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    @Enumerated(value = EnumType.STRING)
    private CardType type;
    @ManyToOne
    private CardIssuer cardIssuer;
    private String name;
    private Long creationDate;
    private Long expirationDate;
    @ManyToOne
    private BankAccount bankAccount;
    private int cvv;
    private BigDecimal cardLimit;
    @Enumerated(value = EnumType.STRING)
    private CardStatus cardStatus;
}
