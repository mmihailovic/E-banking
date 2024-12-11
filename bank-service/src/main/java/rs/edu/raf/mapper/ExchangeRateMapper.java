package rs.edu.raf.mapper;


import org.springframework.stereotype.Component;
import rs.edu.raf.dto.ExchangeRateDto;
import rs.edu.raf.model.ExchangeRate;

@Component
public class ExchangeRateMapper {

    public ExchangeRateDto exchangeRateToExchangeRateResponseDto(ExchangeRate exchangeRate){
        return new ExchangeRateDto(exchangeRate.getCurrencyCode(), exchangeRate.getRate());
    }
}
