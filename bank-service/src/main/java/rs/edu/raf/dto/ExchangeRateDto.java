package rs.edu.raf.dto;

import java.math.BigDecimal;

public record ExchangeRateDto(

    String currencyCode,
    BigDecimal rate

) { }
