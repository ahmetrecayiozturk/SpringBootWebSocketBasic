# Spring Boot WebSocket (STOMP) Messaging Demo

![Spring Boot](https://img.shields.io/badge/spring--boot-3.x-brightgreen) ![WebSocket](https://img.shields.io/badge/websocket-STOMP-blue)

## Overview

This project is a minimal, professional example of a **WebSocket server** using **Spring Boot** and the **STOMP protocol** for real-time, topic-based messaging.

The repository demonstrates:
- **STOMP over WebSocket** integration with Spring Boot
- A simple message broadcast flow (`/topic/messages`)
- A ready-to-use HTML client for testing (no JS build step or extra setup!)

---

## Features

- **STOMP-based WebSocket** backend
- Topic-based broadcasting (`/topic/messages`)
- Simple extensible message model (`Message`)
- **Testable instantly** via provided HTML client (`index.html`)
- Open CORS for development & demo (all origins allowed)

---

## Directory Structure

```plaintext
src/main/java/org/example/
  ├── App.java                    # Main Spring Boot application
  ├── controller/
  │     └── MessageController.java
  ├── model/
  │     └── Message.java
  └── websocket/
        └── WebSocketConfig.java
src/main/resources/static/
  └── index.html                  # Ready-to-use STOMP WebSocket tester UI
```

---

## How to Run

> **Java 17+** is recommended.

1. **Clone & Build:**

   ```bash
   git clone https://github.com/ahmetrecayiozturk/SpringBootWebSocketBasic.git
   cd SpringBootWebSocketBasic
   ./gradlew clean build
   ```

2. **Start the server:**

   ```bash
   ./gradlew bootRun
   ```

3. **Open the test client:**  
   Open your browser and go to:

   ```
   http://localhost:8080
   ```

   > _You must open this URL manually in your browser after starting the server._

---

## How to Test (Step by Step)

### Using the Provided HTML Client

1. When you visit `http://localhost:8080`, a WebSocket tester page (`index.html`) will load.
2. Use the UI:
    - **Connect** to the WebSocket server
    - **Subscribe** to `/topic/messages`
    - **Send** a message to `/app/sendMessage`
    - **See** broadcasted messages in real-time

#### Example Message

```json
{
  "content": "Merhaba!",
  "senderName": "Ahmet"
}
```

### Using External Tools (Optional)

- **WebSocket URL:** `ws://localhost:8080/ws`
- **Subscribe:** `/topic/messages`
- **Send to:** `/app/sendMessage`
- **Payload:** JSON as above

You can use tools like [WebSocket King](https://websocketking.com/) or Postman (with STOMP plugin).

---

## Code Overview

### MessageController.java

Handles `/app/sendMessage` and broadcasts to `/topic/messages`:

```java
@Controller
public class MessageController {
    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public Message sendMessage(Message message) {
        System.out.println("Received message: " + message);
        return message;
    }
}
```

### Message.java

Simple message model:

```java
@Data
public class Message {
    private String content;
    private String senderName = "defaultSender";
    // Constructors...
}
```

### WebSocketConfig.java

Configures STOMP endpoints and broker:

```java
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*");
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }
}
```

---

## Professional Notes

- **Security:** For production, restrict allowed origins and consider authentication.
- **Extensibility:** You can extend the `Message` model for more complex use cases.
- **Testing:** Add unit/integration tests for robust development.
- **Deployment:** For production, serve via HTTPS and consider scaling the broker.

---

## License

MIT © [ahmetrecayiozturk](https://github.com/ahmetrecayiozturk)
