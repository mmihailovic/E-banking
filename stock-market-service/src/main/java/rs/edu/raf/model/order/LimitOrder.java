package rs.edu.raf.model.order;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class LimitOrder extends Order {
    private Double limitAmount;
}
