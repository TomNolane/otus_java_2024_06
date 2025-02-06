package tomnolane.otus.loganalytics.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQLogProducer {
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQLogProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendLog(String source, String level, String message) {
        String formattedMessage = source + ";" + level + ";" + message;
        rabbitTemplate.convertAndSend("logs-queue", formattedMessage);
    }
}
