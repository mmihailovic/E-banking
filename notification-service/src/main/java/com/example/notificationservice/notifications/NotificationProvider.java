package com.example.notificationservice.notifications;

import com.example.notificationservice.dto.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class NotificationProvider {
    @Qualifier("registration")
    @Autowired
    private NotificationContent registrationNotificationContent;
    @Qualifier("resetPassword")
    @Autowired
    private NotificationContent resetPasswordNotificationContent;
    @Qualifier("paymentCode")
    @Autowired
    private NotificationContent paymentCodeNotificationContent;

    public NotificationContent getNotificationContent(NotificationDTO notificationDTO) {
        switch (notificationDTO.notificationType()) {
            case "REGISTRATION":
                return registrationNotificationContent;
            case "PASSWORD_RESET":
                return resetPasswordNotificationContent;
            case "PAYMENT_CODE":
                return paymentCodeNotificationContent;
            default:
                throw new UnsupportedOperationException();
        }
    }
}
