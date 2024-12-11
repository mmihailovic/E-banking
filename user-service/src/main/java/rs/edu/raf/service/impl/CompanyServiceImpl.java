package rs.edu.raf.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rs.edu.raf.dto.CompanyCreateDTO;
import rs.edu.raf.dto.CompanyDTO;
import rs.edu.raf.exceptions.CompanyNotFoundException;
import rs.edu.raf.mapper.CompanyMapper;
import rs.edu.raf.model.user.Company;
import rs.edu.raf.repository.CompanyRepository;
import rs.edu.raf.service.CompanyService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private CompanyRepository companyRepository;
    private CompanyMapper companyMapper;

    @Override
    public CompanyDTO createCompany(CompanyCreateDTO companyCreateDTO) {
        return companyMapper.companyToCompanyDTO(companyRepository.save(companyMapper.companyCreateDTOtoCompany(companyCreateDTO)));
    }

    @Override
    public CompanyDTO findCompanyById(Long id) {
        return companyMapper.companyToCompanyDTO(companyRepository.findById(id)
                .orElseThrow(()->new CompanyNotFoundException("Company with ID " + id + " not found!")));
    }

    @Override
    public List<CompanyDTO> getAllCompanies() {
        return companyRepository.findAll().stream().map(companyMapper::companyToCompanyDTO).toList();
    }

    @Override
    public boolean addBankAccountToCompany(Long id, Long bankAccountNumber) {
        Optional<Company> optionalCompany = companyRepository.findById(id);

        if(optionalCompany.isEmpty()) {
            return false;
        }

        Company company = optionalCompany.get();

        if(company.getAccountNumbers() == null){
            company.setAccountNumbers(bankAccountNumber.toString());
            companyRepository.save(company);
            return true;
        }

        if(company.getAccountNumbers().contains(bankAccountNumber.toString())){
            return false;
        }

        company.setAccountNumbers(company.getAccountNumbers() + "," + bankAccountNumber);
        companyRepository.save(company);
        return true;
    }
}
