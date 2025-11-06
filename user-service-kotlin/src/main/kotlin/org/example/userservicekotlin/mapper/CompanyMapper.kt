package org.example.userservicekotlin.mapper

import org.example.userservicekotlin.dto.CompanyCreateDTO
import org.example.userservicekotlin.model.user.Company
import org.example.userservicekotlin.dto.CompanyDTO
import org.springframework.stereotype.Component

@Component
class CompanyMapper {
    fun companyCreateDTOtoCompany(companyCreateDTO: CompanyCreateDTO): Company {
        return Company(
            null,
            companyCreateDTO.phoneNumber,
            companyCreateDTO.address,
            companyCreateDTO.name,
            null,
            companyCreateDTO.faxNumber,
            companyCreateDTO.TIN,
            companyCreateDTO.registrationNumber,
            companyCreateDTO.businessActivityCode,
            companyCreateDTO.registryNumber
        )
    }

    fun companyToCompanyDTO(company: Company): CompanyDTO {
        return CompanyDTO(
            company.id,
            company.phoneNumber,
            company.address,
            company.name,
            company.accountNumbers,
            company.faxNumber,
            company.TIN,
            company.registrationNumber,
            company.businessActivityCode,
            company.registryNumber
        )
    }
}