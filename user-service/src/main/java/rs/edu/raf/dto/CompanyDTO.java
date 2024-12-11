package rs.edu.raf.dto;

public record CompanyDTO(
        Long id,
        String phoneNumber,
        String address,
        String name,
        String accountNumbers,
        String faxNumber,
        Integer TIN,
        Integer registrationNumber,
        Integer businessActivityCode,
        Integer registryNumber) {
}
