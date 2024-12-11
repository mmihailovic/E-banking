package rs.edu.raf.model.user;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Company extends BankAccountHolder {
    private String name;
    private String accountNumbers;
    private String faxNumber;
    private Integer TIN; // tax identification number
    private Integer registrationNumber;
    private Integer businessActivityCode;
    private Integer registryNumber;
}
