package rs.edu.raf.mapper;

import org.springframework.stereotype.Component;
import rs.edu.raf.dto.CountryCreateDTO;
import rs.edu.raf.dto.CountryDTO;
import rs.edu.raf.model.Country;

@Component
public class CountryMapper {

    public Country countryCreateDTOtoCountry(CountryCreateDTO countryCreateDTO) {
        Country country = new Country();

        country.setName(countryCreateDTO.name());

        return country;
    }

    public CountryDTO countryToCountryDTO(Country country) {
        return new CountryDTO(country.getId(), country.getName());
    }
}
