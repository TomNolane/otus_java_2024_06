package tomnolane.otus.loganalytics.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tomnolane.otus.loganalytics.model.LogEntry;

public interface LogRepository extends ReactiveCrudRepository<LogEntry, Long> {
    Flux<LogEntry> findByLevel(String level);

    Mono<Long> countByLevel(String level);
}
