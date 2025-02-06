package tomnolane.otus.loganalytics.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tomnolane.otus.loganalytics.model.LogEntry;
import tomnolane.otus.loganalytics.service.LogService;

@Service
@Slf4j
public class RabbitMQLogListener {
    private final LogService logService;

    public RabbitMQLogListener(LogService logService) {
        this.logService = logService;
    }

    @RabbitListener(queues = "logs-queue")
    public void receiveLogMessage(String message) {
        log.info("Received log message: {}", message);

        // Парсим сообщение (ожидается JSON)
        String[] parts = message.split(";", 3);
        if (parts.length == 3) {
            String source = parts[0];
            String level = parts[1];
            String logMessage = parts[2];

            Mono<LogEntry> savedLog = logService.saveLog(source, level, logMessage);
            savedLog.subscribe(mylog -> log.info("Saved to DB: {}", mylog));
        } else {
            log.error("Invalid log format: {}", message);
        }
    }
}
