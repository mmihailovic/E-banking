package com.example.notificationservice.notifications;

import com.example.notificationservice.dto.NotificationDTO;
import org.springframework.stereotype.Component;

@Component("paymentCode")
public class PaymentCodeNotificationContent implements NotificationContent{
    @Override
    public String getContent(NotificationDTO notificationDTO) {
        return "Dear Sir/Madam " + notificationDTO.lastName() + "\n\n"
                + "We are sending you the code for your payment.\n"
                + "Code: " + notificationDTO.code() + "\n\n"
                + "If you did not initiate this payment, please contact your bank directly and refrain from providing "
                + "any personal or payment information.\n\n"
                + "Your Bank";
    }

    @Override
    public String getSubject(NotificationDTO notificationDTO) {
        return "Payment code";
    }
}
