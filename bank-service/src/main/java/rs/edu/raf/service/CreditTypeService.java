package rs.edu.raf.service;

import rs.edu.raf.dto.CreditTypeCreateDTO;
import rs.edu.raf.dto.CreditTypeDTO;

import java.util.List;

public interface CreditTypeService {
    /**
     * Creates credit type
     * @param creditTypeCreateDTO DTO which contains information about credit type
     * @return {@link CreditTypeDTO} object representing created credit type
     */
    CreditTypeDTO createCreditType(CreditTypeCreateDTO creditTypeCreateDTO);

    /**
     * Gets all credit types
     * @return List of {@link CreditTypeDTO} representing credit types
     */
    List<CreditTypeDTO> getAllCreditTypes();

    /**
     * Deletes credit type
     * @param id the id of the credit type to be deleted
     */
    void deleteCreditType(Long id);
}
