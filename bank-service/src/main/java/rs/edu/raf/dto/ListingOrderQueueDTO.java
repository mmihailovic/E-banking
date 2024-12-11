package rs.edu.raf.dto;

import java.math.BigDecimal;

public record ListingOrderQueueDTO(Long buyerId, Long sellerId, BigDecimal buyPrice, BigDecimal sellPrice) {
}
