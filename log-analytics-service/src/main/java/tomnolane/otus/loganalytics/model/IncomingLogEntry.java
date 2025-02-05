package tomnolane.otus.loganalytics.model;

import lombok.Data;

@Data
public class IncomingLogEntry {
    private String source;
    private String level;
    private String message;
    private long timestamp;
}
