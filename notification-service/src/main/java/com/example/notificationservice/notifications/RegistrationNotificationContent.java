package com.example.notificationservice.notifications;

import com.example.notificationservice.dto.NotificationDTO;
import org.springframework.stereotype.Component;

@Component("registration")
public class RegistrationNotificationContent implements NotificationContent{

    @Override
    public String getContent(NotificationDTO notificationDTO) {
        return "Dear Sir/Madam " + notificationDTO.lastName() + "\n\n"
            + "You have successfully created your account!\n"
            + "We are sending you the activation code for your account.\n"
            + "Activation code: " + notificationDTO.code() + "\n\n"
            + "Your Bank";
    }

    @Override
    public String getSubject(NotificationDTO notificationDTO) {
        return "Activation code";
    }
}
