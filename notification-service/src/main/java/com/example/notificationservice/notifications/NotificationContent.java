package com.example.notificationservice.notifications;

import com.example.notificationservice.dto.NotificationDTO;

public interface NotificationContent {
    /**
     * Notification content
     * @param notificationDTO DTO object contains information about notification
     * @return Notification content
     */
    String getContent(NotificationDTO notificationDTO);

    /**
     * Notification subject
     * @param notificationDTO DTO object contains information about notification
     * @return Notification subject
     */
    String getSubject(NotificationDTO notificationDTO);
}
