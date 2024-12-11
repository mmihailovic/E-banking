package rs.edu.raf.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import rs.edu.raf.service.CreditService;

@Component
@AllArgsConstructor
public class CreditScheduler {
    private CreditService creditService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void processCreditInstallments() {
        creditService.processCreditInstallment();
    }
}
