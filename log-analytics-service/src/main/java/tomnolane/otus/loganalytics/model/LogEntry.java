package tomnolane.otus.loganalytics.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("logs")
public class LogEntry {
    @Id
    private Long id;

    private String source;
    private String level;
    private String message;
    private LocalDateTime timestamp;
}
