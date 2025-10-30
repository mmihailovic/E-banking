package rs.edu.raf.service;

import rs.edu.raf.dto.CompanyCreateDTO;
import rs.edu.raf.dto.CompanyDTO;

import java.util.List;

public interface CompanyService {
    /**
     * Creates company
     * @param companyCreateDTO DTO object contains information about company
     * @return {@link CompanyDTO} object representing created company
     */
    CompanyDTO createCompany(CompanyCreateDTO companyCreateDTO);

    /**
     * Gets company by ID
     * @param id the id of the company
     * @return {@link CompanyDTO} object representing company
     */
    CompanyDTO findCompanyById(Long id);

    /**
     * Gets all companies
     * @return List of {@link CompanyDTO} objects which represents companies
     */
    List<CompanyDTO> getAllCompanies();

    /**
     * Adds bank account with specified account number to specified company
     * @param TIN TIN of the company
     * @param bankAccountNumber the number of the bank account
     * @return Company ID if adding bank account to company is successful, otherwise null
     */
    Long addBankAccountToCompany(Integer TIN, Long bankAccountNumber);
}
