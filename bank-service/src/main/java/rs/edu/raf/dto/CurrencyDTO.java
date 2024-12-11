package rs.edu.raf.dto;

public record CurrencyDTO (
        Long id, String name, String code, String symbol, CountryDTO countryDTO
) {
}
