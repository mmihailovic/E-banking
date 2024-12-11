package rs.edu.raf.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rs.edu.raf.dto.CountryCreateDTO;
import rs.edu.raf.dto.CountryDTO;
import rs.edu.raf.exceptions.CountryNotFoundException;
import rs.edu.raf.mapper.CountryMapper;
import rs.edu.raf.model.Country;
import rs.edu.raf.repository.CountryRepository;
import rs.edu.raf.service.CountryService;

import java.util.List;

@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {
    private CountryRepository countryRepository;
    private CountryMapper countryMapper;

    @Override
    public CountryDTO createCountry(CountryCreateDTO countryCreateDTO) {
        return countryMapper.countryToCountryDTO(countryRepository.save(countryMapper.countryCreateDTOtoCountry(countryCreateDTO)));
    }

    @Override
    public List<CountryDTO> getAllCountries() {
        return countryRepository.findAll().stream().map(countryMapper::countryToCountryDTO).toList();
    }

    @Override
    public void deleteCountry(Long id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(()->new CountryNotFoundException(id));

        countryRepository.delete(country);
    }
}
