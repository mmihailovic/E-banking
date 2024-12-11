package rs.edu.raf.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ListingOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long owner;
    private String ticker;
    private int quantity;

    public ListingOwner(Long owner, String ticker, int quantity) {
        this.owner = owner;
        this.ticker = ticker;
        this.quantity = quantity;
    }
}
