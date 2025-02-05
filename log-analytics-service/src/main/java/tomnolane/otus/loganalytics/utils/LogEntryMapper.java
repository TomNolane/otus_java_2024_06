package tomnolane.otus.loganalytics.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.experimental.UtilityClass;
import tomnolane.otus.loganalytics.model.IncomingLogEntry;
import tomnolane.otus.loganalytics.model.LogEntry;

@UtilityClass
public class LogEntryMapper {
    public static LogEntry mapToLogEntry(IncomingLogEntry incomingLogEntry) {
        LogEntry logEntry = new LogEntry();
        logEntry.setSource(incomingLogEntry.getSource());
        logEntry.setLevel(incomingLogEntry.getLevel());
        logEntry.setMessage(incomingLogEntry.getMessage());
        logEntry.setTimestamp(LocalDateTime.ofEpochSecond(incomingLogEntry.getTimestamp() / 1000, 0, ZoneOffset.UTC));
        return logEntry;
    }
}
