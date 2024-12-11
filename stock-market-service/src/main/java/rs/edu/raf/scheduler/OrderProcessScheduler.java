package rs.edu.raf.scheduler;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import rs.edu.raf.service.OrderService;

@Component
@AllArgsConstructor
public class OrderProcessScheduler {
    private OrderService orderService;

    @Scheduled(fixedDelay = 60000)
    public void processOrders() {
        orderService.processOrders();
    }
}
