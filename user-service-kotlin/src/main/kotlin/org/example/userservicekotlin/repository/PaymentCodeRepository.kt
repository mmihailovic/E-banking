package org.example.userservicekotlin.repository

import org.example.userservicekotlin.model.PaymentCode
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentCodeRepository : JpaRepository<PaymentCode, Long> {
}