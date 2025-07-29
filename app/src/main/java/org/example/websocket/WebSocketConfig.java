package org.example.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;


//Burasını direkt websocket in java yazınca çıkan şey direkt bu, bunu kullanıyoruz yani direkt, bu arada biz raw websocket değil STOMP protokolü üzerinden websocketi kullanıyoruz
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    //burada mesaj brokeri config ediyoruz
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //burada broker'i /topic uzantılı tüm endpointlere gelen mesakları broadcast etmesi için ayarlıyoruz
        config.enableSimpleBroker("/topic");
        //burada ise client tarafında mesaj göndermek için kullanılacak prefix'i ayarlıyoruz
        config.setApplicationDestinationPrefixes("/app");
    }
    //burada websocket endpoint'ini tanımlıyoruz
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*");
        //burada STOMP protokolü üzerinden websocket bağlantısı için kullanılacak endpoint'i tanımlıyoruz
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }
}
