package by.savushkin.scada_server.handlers;
import by.savushkin.scada_server.models.Tag;
import by.savushkin.scada_server.services.TagService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final TagService tagService;
    private final ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper

    public MyWebSocketHandler(TagService tagService) {
        this.tagService = tagService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("New WebSocket connection: " + session.getId());
        // Отправка начальных данных
        sendTagsToClient(session);
    }

//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        // Обработка входящих сообщений (если нужно)
//        System.out.println("Received message: " + message.getPayload());
//        // Пример обработки входящего JSON (если нужно)
//        try {
//            // Десериализация JSON в объект (если нужно)
//            // Ваш код для обработки входящего сообщения
//        } catch (JsonProcessingException e) {
//            System.err.println("Failed to parse incoming message: " + e.getMessage());
//        }
//    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("WebSocket connection closed: " + session.getId());
    }

    // Метод для отправки тегов клиенту
    private void sendTagsToClient(WebSocketSession session) throws IOException {
        List<Tag> tags = tagService.getTags();
        String jsonTags = convertTagsToJson(tags); // Преобразуйте теги в JSON
        session.sendMessage(new TextMessage(jsonTags));
    }

    // Метод для отправки тегов всем подключенным клиентам
    public void sendTagsToAllClients() {
        List<Tag> tags = tagService.getTags();
        String jsonTags = convertTagsToJson(tags); // Преобразуйте теги в JSON
        System.out.println(sessions.size());
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage(jsonTags));
            } catch (IOException e) {
                System.err.println("Failed to send message: " + e.getMessage());
            }
        }
    }

    // Преобразование списка тегов в JSON с использованием Jackson
    private String convertTagsToJson(List<Tag> tags) {
        try {
            // Сериализация списка тегов в JSON
            return "{\"tags\": " + objectMapper.writeValueAsString(tags) + "}";
        } catch (JsonProcessingException e) {
            System.err.println("Failed to convert tags to JSON: " + e.getMessage());
            return "[]"; // Возвращаем пустой массив в случае ошибки
        }
    }
}