package rs.edu.raf.service.impl;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rs.edu.raf.dto.ExchangeRateDto;
import rs.edu.raf.exceptions.ExchangeRateNotFoundException;
import rs.edu.raf.model.ExchangeRate;
import rs.edu.raf.mapper.ExchangeRateMapper;
import rs.edu.raf.repository.ExchangeRateRepository;
import rs.edu.raf.service.ExchangeRateService;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

@Service
    public class ExchangeRateServiceImpl implements ExchangeRateService {

    @Value("${exchange.api.url}")
    private String EXCHANGE_API_URL;
    private final ExchangeRateRepository exchangeRateRepository;
    private final ExchangeRateMapper exchangeRateMapper;

    public ExchangeRateServiceImpl(ExchangeRateRepository exchangeRateRepository, ExchangeRateMapper exchangeRateMapper) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.exchangeRateMapper = exchangeRateMapper;
    }

    @PostConstruct
    public void saveExchangeRates(){
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> exchangeRatesResponse = restTemplate.getForObject(EXCHANGE_API_URL, Map.class);

        if(exchangeRatesResponse != null && exchangeRatesResponse.containsKey("conversion_rates")){
            Map<String, Object> conversionRates = (Map<String, Object>) exchangeRatesResponse.get("conversion_rates");
            saveRates(conversionRates);
        }
    }

    private void saveRates(Map<String, Object> conversionRates){
    
        exchangeRateRepository.deleteAll();
        conversionRates.forEach((currencyCode, rate) -> {
            ExchangeRate exchangeRate = new ExchangeRate();
            exchangeRate.setCurrencyCode(currencyCode);
            exchangeRate.setRate(new BigDecimal(rate.toString()));
            exchangeRateRepository.save(exchangeRate);
        });
    }

    @Override
    public List<ExchangeRateDto> getAllExchangeRates(){
        return exchangeRateRepository.findAll()
                .stream()
                .map(exchangeRateMapper::exchangeRateToExchangeRateResponseDto)
                .toList();
    }

    @Override
    public BigDecimal convertToCurrency(String oldCurrencyCode, String newCurrencyCode, BigDecimal amount){

        ExchangeRate oldExchangeRate = exchangeRateRepository.findByCurrencyCode(oldCurrencyCode)
                .orElseThrow(()->new ExchangeRateNotFoundException(oldCurrencyCode));

        ExchangeRate newExchangeRate = exchangeRateRepository.findByCurrencyCode(newCurrencyCode)
                .orElseThrow(()->new ExchangeRateNotFoundException(newCurrencyCode));

        BigDecimal oldAmount = oldExchangeRate.getRate();
        BigDecimal newAmount = newExchangeRate.getRate();

        BigDecimal provision = BigDecimal.valueOf(0.995);

        return amount.divide(oldAmount, new MathContext(2, RoundingMode.HALF_DOWN)).multiply(newAmount).multiply(provision);
    }

    @Override
    public BigDecimal exchangeRate(String oldCurrencyCode, String newCurrencyCode){

        ExchangeRate oldExchangeRate = exchangeRateRepository.findByCurrencyCode(oldCurrencyCode)
                .orElseThrow(()->new ExchangeRateNotFoundException(oldCurrencyCode));

        ExchangeRate newExchangeRate = exchangeRateRepository.findByCurrencyCode(newCurrencyCode)
                .orElseThrow(()->new ExchangeRateNotFoundException(newCurrencyCode));

        BigDecimal oldAmount = oldExchangeRate.getRate();
        BigDecimal newAmount = newExchangeRate.getRate();

        return new BigDecimal("1.0").divide(oldAmount,new MathContext(2, RoundingMode.HALF_DOWN)).multiply(newAmount);
    }
}
