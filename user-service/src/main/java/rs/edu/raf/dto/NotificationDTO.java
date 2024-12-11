package rs.edu.raf.dto;

import java.io.Serializable;

public record NotificationDTO(String email, String code, String lastName, String notificationType) implements Serializable {
}
