package org.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MessageController {

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public Message sendMessage(Message message) {
        // Burada mesajı işleyebiliriz, örneğin loglayabiliriz
        System.out.println("Received message: " + message);
        // Mesajı topic'e yayınlıyoruz
        return message; // Bu mesajı /topic/messages'e yayınlayacak
    }
}
