package rs.edu.raf.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import rs.edu.raf.dto.CurrencyCreateDTO;
import rs.edu.raf.dto.CurrencyDTO;
import rs.edu.raf.model.Currency;
import rs.edu.raf.repository.CountryRepository;

@Component
@AllArgsConstructor
public class CurrencyMapper {
    private CountryMapper countryMapper;
    private CountryRepository countryRepository;

    public Currency currencyCreateDTOtoCurrency(CurrencyCreateDTO currencyCreateDTO) {
        Currency currency = new Currency();

        currency.setCode(currencyCreateDTO.code());
        currency.setName(currencyCreateDTO.name());
        currency.setSymbol(currencyCreateDTO.symbol());
        currency.setCountry(countryRepository.findById(currencyCreateDTO.countryId()).orElseThrow());

        return currency;
    }
    public CurrencyDTO currencyToCurrencyDTO(Currency currency) {
        return new CurrencyDTO(currency.getId(), currency.getName(), currency.getCode(), currency.getSymbol(),
                countryMapper.countryToCountryDTO(currency.getCountry()));
    }
}
