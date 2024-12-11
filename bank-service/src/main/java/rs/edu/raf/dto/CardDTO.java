package rs.edu.raf.dto;

import java.math.BigDecimal;
public record CardDTO(
    Long id,
    String number,
    String type,
    CardIssuerDTO cardIssuer,
    String name,
    Long creationDate,
    Long expirationDate,
    BankAccountDTO bankAccount,
    int cvv,
    BigDecimal cardLimit,
    String status
) { }
