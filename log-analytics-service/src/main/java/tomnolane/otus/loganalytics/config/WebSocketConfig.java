package tomnolane.otus.loganalytics.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import tomnolane.otus.loganalytics.websocket.LogWebSocketHandler;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class WebSocketConfig {
    private final LogWebSocketHandler logWebSocketHandler;

    @Bean
    public SimpleUrlHandlerMapping webSocketMapping() {
        return new SimpleUrlHandlerMapping(Map.of("/ws/logs", logWebSocketHandler), 1);
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}
