package com.example.notificationservice.service.impl;

import com.example.notificationservice.dto.NotificationDTO;
import com.example.notificationservice.notifications.NotificationContent;
import com.example.notificationservice.notifications.NotificationProvider;
import com.example.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private NotificationProvider notificationProvider;
    @Value("${sender.email}")
    private String SENDER_EMAIL;
    @Override
    public void sendNotification(NotificationDTO notificationDTO) {
        try {
            NotificationContent notificationContent = notificationProvider.getNotificationContent(notificationDTO);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(SENDER_EMAIL);
            message.setTo(notificationDTO.email());
            message.setSubject(notificationContent.getSubject(notificationDTO));
            message.setText(notificationContent.getContent(notificationDTO));
            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }
}
