package rs.edu.raf.mapper;

import org.springframework.stereotype.Component;
import rs.edu.raf.dto.CompanyCreateDTO;
import rs.edu.raf.dto.CompanyDTO;
import rs.edu.raf.model.user.Company;

@Component
public class CompanyMapper {

    public Company companyCreateDTOtoCompany(CompanyCreateDTO companyCreateDTO) {
        Company company = new Company();

        company.setPhoneNumber(companyCreateDTO.phoneNumber());
        company.setAddress(companyCreateDTO.address());
        company.setName(companyCreateDTO.name());
        company.setTIN(companyCreateDTO.TIN());
        company.setBusinessActivityCode(companyCreateDTO.businessActivityCode());
        company.setFaxNumber(companyCreateDTO.faxNumber());
        company.setRegistrationNumber(companyCreateDTO.registrationNumber());
        company.setRegistryNumber(companyCreateDTO.registryNumber());

        return company;
    }

    public CompanyDTO companyToCompanyDTO(Company company) {
        return new CompanyDTO(company.getId(), company.getPhoneNumber(), company.getAddress(), company.getName(),
                company.getAccountNumbers(), company.getFaxNumber(), company.getTIN(), company.getRegistrationNumber(),
                company.getBusinessActivityCode(), company.getRegistryNumber());
    }
}
