package rs.edu.raf.model.code;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.edu.raf.model.user.User;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @Column(unique = true)
    private String code;
    private Long expirationDate;

    @Enumerated(value = EnumType.STRING)
    private CodeType codeType;

    public Code(User user, String code, Long expirationDate, CodeType codeType) {
        this.user = user;
        this.code = code;
        this.expirationDate = expirationDate;
        this.codeType = codeType;
    }
}
