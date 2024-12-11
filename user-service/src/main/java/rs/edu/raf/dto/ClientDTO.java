package rs.edu.raf.dto;

public record ClientDTO(Long id, String firstName, String lastName, String JMBG, Long dateOfBirth, String gender,
                        String email, String phoneNumber, String address, String bankAccounts ) {
}
