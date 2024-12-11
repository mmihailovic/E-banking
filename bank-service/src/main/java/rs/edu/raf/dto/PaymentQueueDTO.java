package rs.edu.raf.dto;

import java.math.BigDecimal;

public record PaymentQueueDTO(Long id,
                              String email,
                              Long senderAccountNumber,
                              Long receiverAccountNumber,
                              BigDecimal amount,
                              Long userId,
                              String type,
                              String paymentCode) {
}
