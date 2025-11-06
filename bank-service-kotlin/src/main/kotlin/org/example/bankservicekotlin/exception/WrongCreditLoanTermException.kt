package org.example.bankservicekotlin.exception

import org.springframework.http.HttpStatus

data class WrongCreditLoanTermException(val minLoanTerm: Int, val maxLoanTerm: Int) :
    CustomException("Credit loan must be between $minLoanTerm and $maxLoanTerm", HttpStatus.BAD_REQUEST)
