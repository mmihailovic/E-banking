package org.example.bankservicekotlin.service.impl

import org.example.bankservicekotlin.dto.CreditTypeCreateDTO
import org.example.bankservicekotlin.dto.CreditTypeDTO
import org.example.bankservicekotlin.exception.CreditTypeNotFoundException
import org.example.bankservicekotlin.mapper.CreditTypeMapper
import org.example.bankservicekotlin.model.credit.CreditType
import org.example.bankservicekotlin.repository.CreditTypeRepository
import org.example.bankservicekotlin.service.CreditTypeService
import org.springframework.stereotype.Service

@Service
class CreditTypeServiceImpl(
    private val creditTypeRepository: CreditTypeRepository,
    private val creditTypeMapper: CreditTypeMapper
):CreditTypeService {
    override fun createCreditType(creditTypeCreateDTO: CreditTypeCreateDTO): CreditTypeDTO {
        return creditTypeMapper.creditTypeToCreditTypeDTO(
            creditTypeRepository.save(
                creditTypeMapper.creditTypeCreateDTOtoCreditType(
                    creditTypeCreateDTO
                )
            )
        )
    }

    override fun getAllCreditTypes(): List<CreditTypeDTO> {
        return creditTypeRepository.findAll().stream().map(creditTypeMapper::creditTypeToCreditTypeDTO).toList()
    }

    override fun deleteCreditType(id: Long) {
        val creditType: CreditType = creditTypeRepository.findById(id)
            .orElseThrow { CreditTypeNotFoundException(id) }

        creditTypeRepository.delete(creditType)
    }
}