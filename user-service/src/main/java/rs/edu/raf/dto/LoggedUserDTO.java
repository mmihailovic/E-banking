package rs.edu.raf.dto;

import java.util.List;

public record LoggedUserDTO(Long id, String username, Long companyId, List<String> roles) {
}
