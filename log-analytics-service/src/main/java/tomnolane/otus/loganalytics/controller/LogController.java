package tomnolane.otus.loganalytics.controller;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tomnolane.otus.loganalytics.model.LogEntry;
import tomnolane.otus.loganalytics.service.LogService;

@RestController
@RequestMapping("/logs")
public class LogController {
    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping
    public Mono<LogEntry> createLog(
            @RequestParam String source, @RequestParam String level, @RequestParam String message) {
        return logService.saveLog(source, level, message);
    }

    @GetMapping
    public Flux<LogEntry> getAllLogs() {
        return logService.getAllLogs();
    }

    @GetMapping("/level/{level}")
    public Flux<LogEntry> getLogsByLevel(@PathVariable String level) {
        return logService.getLogsByLevel(level);
    }
}
