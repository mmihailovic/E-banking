package org.example.bankservicekotlin.listener

import com.google.gson.Gson
import org.example.bankservicekotlin.dto.ListingOrderQueueDTO
import org.example.bankservicekotlin.dto.PaymentQueueDTO
import org.example.bankservicekotlin.service.BankAccountService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class RabbitMQListener(
    val gson: Gson,
    val bankAccountService: BankAccountService) {
    @RabbitListener(queues = ["\${transactions.required.queue}"])
    fun transaction(message: String?) {
        val paymentQueueDTO: PaymentQueueDTO = gson.fromJson(message, PaymentQueueDTO::class.java)
        bankAccountService.processPayment(paymentQueueDTO)
    }

    @RabbitListener(queues = ["\${update.balance.queue}"])
    fun listingOrderTransaction(message: String?) {
        val listingOrderQueueDTO: ListingOrderQueueDTO = gson.fromJson(message, ListingOrderQueueDTO::class.java)

        bankAccountService.processListingOrder(
            listingOrderQueueDTO.buyerId, listingOrderQueueDTO.sellerId,
            listingOrderQueueDTO.buyPrice, listingOrderQueueDTO.sellPrice
        )
    }
}