package com.example.notificationservice.notifications;

import com.example.notificationservice.dto.NotificationDTO;
import org.springframework.stereotype.Component;

@Component("resetPassword")
public class ResetPasswordNotificationContent implements NotificationContent{
    @Override
    public String getContent(NotificationDTO notificationDTO) {
        return "Dear Sir/Madam " + notificationDTO.lastName() + "\n\n"
             + "We are sending you a code to change your password.\n"
             + "Verification code: " + notificationDTO.code() + "\n\n"
             + "Your Bank.";
    }

    @Override
    public String getSubject(NotificationDTO notificationDTO) {
        return "Verification code";
    }
}
