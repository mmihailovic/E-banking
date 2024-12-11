package rs.edu.raf.service;

import rs.edu.raf.dto.CountryCreateDTO;
import rs.edu.raf.dto.CountryDTO;

import java.util.List;

public interface CountryService {
    /**
     * Creates country
     * @param countryCreateDTO DTO which contains information about country
     * @return {@link CountryDTO} object representing created country
     */
    CountryDTO createCountry(CountryCreateDTO countryCreateDTO);

    /**
     * Gets all countries
     * @return List of {@link CountryDTO} representing countries
     */
    List<CountryDTO> getAllCountries();

    /**
     * Deletes country
     * @param id the id of the country to be deleted
     */
    void deleteCountry(Long id);
}
