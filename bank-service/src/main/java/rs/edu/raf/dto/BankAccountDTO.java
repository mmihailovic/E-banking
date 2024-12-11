package rs.edu.raf.dto;

import java.math.BigDecimal;

public record BankAccountDTO (
        Long id,
        String accountNumber,
        Long owner,
        BigDecimal balance,
        BigDecimal availableBalance,
        Long creator,
        Long creationDate,
        CurrencyDTO currencyDTO,
        Boolean active
) { }
