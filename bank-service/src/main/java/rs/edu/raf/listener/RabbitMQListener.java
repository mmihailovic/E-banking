package rs.edu.raf.listener;

import com.google.gson.Gson;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.edu.raf.dto.ListingOrderQueueDTO;
import rs.edu.raf.dto.PaymentQueueDTO;
import rs.edu.raf.service.BankAccountService;

@Component
public class RabbitMQListener {
    @Autowired
    private Gson gson;

    @Autowired
    private BankAccountService bankAccountService;

    @RabbitListener(queues = "${transactions.required.queue}")
    public void transaction(String message) {
        PaymentQueueDTO paymentQueueDTO = gson.fromJson(message, PaymentQueueDTO.class);
        bankAccountService.processPayment(paymentQueueDTO);
    }

    @RabbitListener(queues = "${update.balance.queue}")
    public void listingOrderTransaction(String message) {
        ListingOrderQueueDTO listingOrderQueueDTO = gson.fromJson(message, ListingOrderQueueDTO.class);

        bankAccountService.processListingOrder(listingOrderQueueDTO.buyerId(), listingOrderQueueDTO.sellerId(),
                listingOrderQueueDTO.buyPrice(), listingOrderQueueDTO.sellPrice());
    }
}
