package tomnolane.otus.loganalytics.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tomnolane.otus.loganalytics.model.LogEntry;
import tomnolane.otus.loganalytics.repository.LogRepository;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogService {
    private final LogRepository logRepository;

    public Mono<LogEntry> saveLog(String source, String level, String message) {
        LogEntry logEntry = new LogEntry(null, source, level, message, LocalDateTime.now());
        return logRepository.save(logEntry);
    }

    public Flux<LogEntry> getLogsByLevel(String level) {
        return logRepository.findByLevel(level);
    }

    public Flux<LogEntry> getAllLogs() {
        return logRepository.findAll();
    }

    public Mono<Void> saveLog(LogEntry logEntry) {
        return logRepository
                .save(logEntry)
                .doOnSuccess(saved -> log.info("Log saved: {}", saved))
                .then();
    }

    public Mono<Long> countLogs() {
        return logRepository.count();
    }

    public Mono<Long> countLogsByLevel(String level) {
        return logRepository.countByLevel(level);
    }
}
