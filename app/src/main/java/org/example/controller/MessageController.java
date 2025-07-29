package org.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * MessageController sınıfı, WebSocket üzerinden gelen mesajları
 * handle eden ve gerekli topic'lere yayın yapan bir controller'dır.
 *
 * Bu controller'da iki farklı endpoint bulunmakta;
 * - /app/sendMessage      => /topic/messages'a yayın yapar
 * - /app/sendMessageToDifferentChannel => /topic/differentChannel'a yayın yapar
 *
 * @Controller anotasyonu ile Spring'e bu sınıfın bir Controller olduğunu bildiriyoruz.
 */
@Controller
public class MessageController {

    /**
     * Bu metot, WebSocket client'larından "/app/sendMessage" endpointine
     * gönderilen mesajları handle eder.
     *
     * @MessageMapping("/sendMessage")
     *  - STOMP protokolünde, client tarafında "/app/sendMessage" endpointine
     *    gönderilen mesajlar, bu metoda yönlendirilir.
     *  - Yani "/app/sendMessage" adresine mesaj POST eden her client'ın mesajı burada alınır.
     *
     * @SendTo("/topic/messages")
     *  - Bu anotasyon, metottan dönen mesajın "/topic/messages" kanalına abone
     *    olan tüm client'lara broadcast edilmesini sağlar.
     *  - Yani bu metoda gelen her mesaj, "/topic/messages" kanalına publish edilir.
     *
     * Not:
     * - "/app" prefix'i, WebSocketConfig'de setApplicationDestinationPrefixes ile belirlenir.
     * - "/topic/messages" ise basit bir broker ile (enableSimpleBroker("/topic")) broadcast edilir.
     *
     * @param message Client tarafından gönderilen mesaj nesnesidir.
     * @return Alınan mesaj, doğrudan tüm abone client'lara yayınlanır.
     */
    // burada messsagemapptin aslında app/** gibi endpointleri belirlememizi sağlar yani ben burada app/sendMessage
    //endpointine gelen mesajları farklı bir topic'e yönlendirmek istiyorum o farklı topic de aslında üye olduğum channel
    //yani ben /app/sendMessage endpointine gelen mesajları /topic/sendMessage'a üye yerlere broadcast ediyorum
    // Bu endpoint'e gelen mesajlar /topic/messages'a yayınlanacak
    @MessageMapping("/sendMessage")
    //Subscribe olunacak channel'ı belirtiyoruz
    @SendTo("/topic/messages")
    public Message sendMessage(Message message) {
        // Gelen mesajı sunucu tarafında logluyoruz.
        // Burada ek validasyon, veritabanı kaydı veya başka işlemler yapılabilir.
        System.out.println("Received message: " + message);

        // Mesajı /topic/messages abonelerine gönderiyoruz (broadcast)
        return message;
    }

    /**
     * Bu metot, WebSocket client'larından "/app/sendMessageToDifferentChannel" endpointine
     * gönderilen mesajları handle eder.
     *
     * @MessageMapping("/sendMessageToDifferentChannel")
     *  - STOMP protokolünde, client tarafında "/app/sendMessageToDifferentChannel" endpointine
     *    gönderilen mesajlar, bu metoda yönlendirilir.
     *  - Yani "/app/sendMessageToDifferentChannel" adresine mesaj POST eden her client'ın mesajı burada alınır.
     *
     * @SendTo("/topic/differentChannel")
     *  - Bu anotasyon, metottan dönen mesajın "/topic/differentChannel" kanalına abone
     *    olan tüm client'lara broadcast edilmesini sağlar.
     *  - Yani bu metoda gelen her mesaj, "/topic/differentChannel" kanalına publish edilir.
     *
     * Not:
     * - Bu yöntem, birden fazla farklı topic'e yayın yapmak istediğinizde faydalıdır.
     * - Farklı chat odaları, bildirim kanalları gibi senaryolarda kullanılabilir.
     *
     * @param message Client tarafından gönderilen mesaj nesnesidir.
     * @return Alınan mesaj, doğrudan /topic/differentChannel kanalındaki tüm abone client'lara yayınlanır.
     */
    // burada messsagemapptin aslında app/** gibi endpointleri belirlememizi sağlar yani ben burada app/sendMessageTıDifferentChannel
    //endpointine gelen mesajları farklı bir topic'e yönlendirmek istiyorum o farklı topic de aslında üye olduğum channel
    //yani ben /app/sendMessageTıDifferentChannel endpointine gelen mesajları /topic/differentChannel'a üye
    //yerlere broadcast ediyorum
    // Bu endpoint'e gelen mesajlar /topic/differentChannel'a yayınlanacak
    @MessageMapping("/sendMessageToDifferentChannel")
    //Subscribe olunacak channel'ı belirtiyoruz
    @SendTo("/topic/differentChannel")// Bu mesajı yer /topic/differentChannel'a yayınlayacak yani
    public Message sendMessageDifferentChannel(Message message) {
        // Gelen mesajı sunucu tarafında logluyoruz.
        // Burada da ek validasyon, iş kuralları uygulanabilir.
        System.out.println("Received message to different channel: " + message);

        // Mesajı /topic/differentChannel abonelerine gönderiyoruz (broadcast)
        return message;
    }
}
/*
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
    // Bu endpoint'e gelen mesajlar /topic/differentChannel'a yayınlanacak
    @MessageMapping("/sendMessageToDifferentChannel")
    //Subscribe olunacak channel'ı belirtiyoruz
    @SendTo("/topic/differentChannel")// Bu mesajı yer /topic/differentChannel'a yayınlayacak yani
    public Message sendMessageDifferentChannel(Message message) {
        // Burada mesajı işleyebiliriz, örneğin loglayabiliriz
        System.out.println("Received message to different channel: " + message);
        // Mesajı topic'e yayınlıyoruz
        return message; // Bu mesajı /topic/messages'e yayınlayacak
    }
}
*/