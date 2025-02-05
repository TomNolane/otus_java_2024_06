package tomnolane.otus.loganalytics.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RabbitMQConfig {
    @Bean
    public Queue logsQueue() {
        return new Queue("logs-queue", true);
    }
}
