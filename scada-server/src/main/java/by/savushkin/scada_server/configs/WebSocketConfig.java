package by.savushkin.scada_server.configs;

import by.savushkin.scada_server.handlers.MyWebSocketHandler;
import by.savushkin.scada_server.services.TagService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final TagService tagService;

    // Внедрение зависимости через конструктор
    public WebSocketConfig(TagService tagService) {
        this.tagService = tagService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new MyWebSocketHandler(tagService), "")
                .setAllowedOriginPatterns("*"); // Разрешить все источники
    }
}
