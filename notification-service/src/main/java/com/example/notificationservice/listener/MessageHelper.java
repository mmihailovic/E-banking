package com.example.notificationservice.listener;

import com.google.gson.Gson;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageHelper {
    @Autowired
    private Gson gson;

    public <T> T convertMessageToObject(Message message, Class<T> classObject) throws JMSException {
        TextMessage textMessage = (TextMessage) message;
        return gson.fromJson(textMessage.getText(), classObject);
    }
}
