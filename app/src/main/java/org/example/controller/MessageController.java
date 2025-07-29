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

    // burada messsagemapptin aslında app/** gibi endpointleri belirlememizi sağlar yani ben burada app/sendMessage
    //endpointine gelen mesajları farklı bir topic'e yönlendirmek istiyorum o farklı topic de aslında üye olduğum channel
    //yani ben /app/sendMessage endpointine gelen mesajları /topic/sendMessage'a üye
    //yerlere broadcast ediyorum
    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public Message sendMessage(Message message) {
        // Burada mesajı işleyebiliriz, örneğin loglayabiliriz
        System.out.println("Received message: " + message);
        // Mesajı topic'e yayınlıyoruz
        return message; // Bu mesajı /topic/messages'e yayınlayacak
    }
    // burada messsagemapptin aslında app/** gibi endpointleri belirlememizi sağlar yani ben burada app/sendMessageTıDifferentChannel
    //endpointine gelen mesajları farklı bir topic'e yönlendirmek istiyorum o farklı topic de aslında üye olduğum channel
    //yani ben /app/sendMessageTıDifferentChannel endpointine gelen mesajları /topic/differentChannel'a üye
    //yerlere broadcast ediyorum
    @MessageMapping("/sendMessageTıDifferentChannel")
    @SendTo("/topic/differentChannel")
    public Message sendMessageDifferentChannel(Message message) {
        // Burada mesajı işleyebiliriz, örneğin loglayabiliriz
        System.out.println("Received message to different channel: " + message);
        // Mesajı topic'e yayınlıyoruz
        return message; // Bu mesajı /topic/messages'e yayınlayacak
    }
}
