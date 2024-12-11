package rs.edu.raf.dto;

public record UpdateBalanceDTO(Long buyerId, Long sellerId, Double buyPrice, Double sellPrice) {
}
