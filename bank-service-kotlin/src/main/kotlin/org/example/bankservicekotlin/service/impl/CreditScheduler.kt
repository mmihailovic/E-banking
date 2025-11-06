package org.example.bankservicekotlin.service.impl

import org.example.bankservicekotlin.service.CreditService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class CreditScheduler(private val creditService: CreditService) {
    @Scheduled(cron = "0 0 0 * * ?")
    fun processCreditInstallments() {
        creditService.processCreditInstallment()
    }

}