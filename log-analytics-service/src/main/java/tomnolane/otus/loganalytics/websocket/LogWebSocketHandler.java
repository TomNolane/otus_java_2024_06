package tomnolane.otus.loganalytics.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import tomnolane.otus.loganalytics.model.IncomingLogEntry;
import tomnolane.otus.loganalytics.model.LogEntry;
import tomnolane.otus.loganalytics.service.LogService;
import tomnolane.otus.loganalytics.utils.LogEntryMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogWebSocketHandler implements WebSocketHandler {

    private final LogService logService;
    private final ObjectMapper objectMapper;

    @NotNull
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .flatMap(payload -> {
                    try {
                        IncomingLogEntry incomingLog = objectMapper.readValue(payload, IncomingLogEntry.class);
                        LogEntry logEntry = LogEntryMapper.mapToLogEntry(incomingLog);
                        return logService.saveLog(logEntry);
                    } catch (Exception e) {
                        log.error("Ошибка обработки WebSocket-сообщения: {}", payload, e);
                        return Mono.empty();
                    }
                })
                .then();
    }
}
