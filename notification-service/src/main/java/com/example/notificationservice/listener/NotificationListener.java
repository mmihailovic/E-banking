package com.example.notificationservice.listener;

import com.example.notificationservice.dto.NotificationDTO;
import com.example.notificationservice.service.NotificationService;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {
    private NotificationService notificationService;
    private MessageHelper messageHelper;

    public NotificationListener(NotificationService notificationService, MessageHelper messageHelper) {
        this.notificationService = notificationService;
        this.messageHelper = messageHelper;
    }

    @JmsListener(destination = "${notifications.destination}")
    public void handleNotifications(Message message) throws JMSException {
        NotificationDTO notificationDTO = messageHelper.convertMessageToObject(message, NotificationDTO.class);

        notificationService.sendNotification(notificationDTO);
    }
}
