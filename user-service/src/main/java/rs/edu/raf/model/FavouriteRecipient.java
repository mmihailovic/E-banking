package rs.edu.raf.model;

import jakarta.persistence.*;
import lombok.*;
import rs.edu.raf.model.user.Client;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavouriteRecipient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Client client;
    private String recipientName;
    private String recipientAccountNumber;

    public FavouriteRecipient(Client client, String recipientName, String recipientAccountNumber) {
        this.client = client;
        this.recipientName = recipientName;
        this.recipientAccountNumber = recipientAccountNumber;
    }
}
