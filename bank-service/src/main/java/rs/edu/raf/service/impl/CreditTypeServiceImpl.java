package rs.edu.raf.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rs.edu.raf.dto.CreditTypeCreateDTO;
import rs.edu.raf.dto.CreditTypeDTO;
import rs.edu.raf.exceptions.CreditTypeNotFoundException;
import rs.edu.raf.mapper.CreditTypeMapper;
import rs.edu.raf.model.credit.CreditType;
import rs.edu.raf.repository.CreditTypeRepository;
import rs.edu.raf.service.CreditTypeService;

import java.util.List;

@Service
@AllArgsConstructor
public class CreditTypeServiceImpl implements CreditTypeService {
    private CreditTypeRepository creditTypeRepository;
    private CreditTypeMapper creditTypeMapper;


    @Override
    public CreditTypeDTO createCreditType(CreditTypeCreateDTO creditTypeCreateDTO) {
        return creditTypeMapper.creditTypeToCreditTypeDTO(creditTypeRepository.save(creditTypeMapper.creditTypeCreateDTOtoCreditType(creditTypeCreateDTO)));
    }

    @Override
    public List<CreditTypeDTO> getAllCreditTypes() {
        return creditTypeRepository.findAll().stream().map(creditTypeMapper::creditTypeToCreditTypeDTO).toList();
    }

    @Override
    public void deleteCreditType(Long id) {
        CreditType creditType = creditTypeRepository.findById(id)
                .orElseThrow(()->new CreditTypeNotFoundException(id));

        creditTypeRepository.delete(creditType);
    }
}
