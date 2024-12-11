package rs.edu.raf.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rs.edu.raf.dto.CurrencyCreateDTO;
import rs.edu.raf.dto.CurrencyDTO;
import rs.edu.raf.exceptions.CurrencyNotFoundException;
import rs.edu.raf.mapper.CurrencyMapper;
import rs.edu.raf.model.Currency;
import rs.edu.raf.repository.CurrencyRepository;
import rs.edu.raf.service.CurrencyService;

import java.util.List;

@Service
@AllArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {
    private CurrencyRepository currencyRepository;
    private CurrencyMapper currencyMapper;

    @Override
    public CurrencyDTO createCurrency(CurrencyCreateDTO currencyCreateDTO) {
        return currencyMapper.currencyToCurrencyDTO(currencyRepository.save(currencyMapper.currencyCreateDTOtoCurrency(currencyCreateDTO)));
    }

    @Override
    public List<CurrencyDTO> getAllCurrencies() {
        return currencyRepository.findAll().stream().map(currencyMapper::currencyToCurrencyDTO).toList();
    }

    @Override
    public void deleteCurrency(Long id) {
        Currency currency = currencyRepository.findById(id)
                .orElseThrow(()->new CurrencyNotFoundException(id));

        currencyRepository.delete(currency);
    }
}
