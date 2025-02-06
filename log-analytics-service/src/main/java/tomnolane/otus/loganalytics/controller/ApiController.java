package tomnolane.otus.loganalytics.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tomnolane.otus.loganalytics.service.RabbitMQLogProducer;

@RestController
@RequestMapping("/api/logs") // Для теста реббит MQ
public class ApiController {
    private final RabbitMQLogProducer logProducer;

    public ApiController(RabbitMQLogProducer logProducer) {
        this.logProducer = logProducer;
    }

    @PostMapping("/send")
    public void sendLog(@RequestParam String source, @RequestParam String level, @RequestParam String message) {
        logProducer.sendLog(source, level, message);
    }
}
