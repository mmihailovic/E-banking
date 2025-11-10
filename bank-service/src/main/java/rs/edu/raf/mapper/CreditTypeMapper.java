package rs.edu.raf.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import rs.edu.raf.dto.CreditTypeCreateDTO;
import rs.edu.raf.dto.CreditTypeDTO;
import rs.edu.raf.model.credit.CreditType;
import rs.edu.raf.repository.CurrencyRepository;

@Component
@AllArgsConstructor
public class CreditTypeMapper {
    private CurrencyRepository currencyRepository;
    private CurrencyMapper currencyMapper;

    public CreditType creditTypeCreateDTOtoCreditType(CreditTypeCreateDTO creditTypeCreateDTO) {
        CreditType creditType = new CreditType();

        creditType.setName(creditTypeCreateDTO.name());
        creditType.setPrepayment(creditTypeCreateDTO.prepayment());
        creditType.setMinLoanTerm(creditTypeCreateDTO.minLoanTerm());
        creditType.setMaxLoanTerm(creditTypeCreateDTO.maxLoanTerm());
        creditType.setMaxLoanAmount(creditTypeCreateDTO.maxLoanAmount());
        creditType.setNominalInterestRate(creditTypeCreateDTO.nominalInterestRate());
        creditType.setCurrency(currencyRepository.findById(creditTypeCreateDTO.currencyId()).orElseThrow());

        return creditType;
    }

    public CreditTypeDTO creditTypeToCreditTypeDTO(CreditType creditType) {
        return new CreditTypeDTO(creditType.getId(), creditType.getName(), creditType.getNominalInterestRate(),
                creditType.getMinLoanTerm(), creditType.getMaxLoanTerm(), creditType.getMaxLoanAmount(),
                creditType.getPrepayment(), currencyMapper.currencyToCurrencyDTO(creditType.getCurrency()));
    }
}
