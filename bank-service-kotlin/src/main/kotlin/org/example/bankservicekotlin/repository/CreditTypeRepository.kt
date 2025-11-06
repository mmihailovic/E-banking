package org.example.bankservicekotlin.repository

import org.example.bankservicekotlin.model.credit.CreditType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CreditTypeRepository:JpaRepository<CreditType, Long> {

}