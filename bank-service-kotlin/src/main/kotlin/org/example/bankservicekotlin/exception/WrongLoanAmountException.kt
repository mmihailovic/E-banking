package org.example.bankservicekotlin.exception

import org.springframework.http.HttpStatus
import java.math.BigDecimal

data class WrongLoanAmountException(val maxLoanAmount: BigDecimal) :
    CustomException("Loan amount can't be greater than $maxLoanAmount", HttpStatus.BAD_REQUEST)
