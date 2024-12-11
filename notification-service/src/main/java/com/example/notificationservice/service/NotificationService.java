package com.example.notificationservice.service;

import com.example.notificationservice.dto.NotificationDTO;

public interface NotificationService {
    /**
     * Sends notification to user
     * @param notificationDTO information about notification
     */
    void sendNotification(NotificationDTO notificationDTO);
}
