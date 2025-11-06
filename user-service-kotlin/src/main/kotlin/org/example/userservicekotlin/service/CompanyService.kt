package org.example.userservicekotlin.service

import org.example.userservicekotlin.dto.CompanyCreateDTO
import org.example.userservicekotlin.dto.CompanyDTO

interface CompanyService {
    /**
     * Creates company
     * @param companyCreateDTO DTO object contains information about company
     * @return [CompanyDTO] object representing created company
     */
    fun createCompany(companyCreateDTO: CompanyCreateDTO): CompanyDTO?

    /**
     * Gets company by ID
     * @param id the id of the company
     * @return [CompanyDTO] object representing company
     */
    fun findCompanyById(id: Long): CompanyDTO

    /**
     * Gets all companies
     * @return List of [CompanyDTO] objects which represents companies
     */
    fun getAllCompanies(): List<CompanyDTO>

    /**
     * Adds bank account with specified account number to specified company
     * @param TIN TIN of the company
     * @param bankAccountNumber the number of the bank account
     * @return Company ID if adding bank account to company is successful, otherwise null
     */
    fun addBankAccountToCompany(TIN: Int, bankAccountNumber: Long): Long?
}