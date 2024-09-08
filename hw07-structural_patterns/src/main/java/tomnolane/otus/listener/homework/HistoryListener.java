package tomnolane.otus.listener.homework;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tomnolane.otus.listener.Listener;
import tomnolane.otus.model.Message;
import tomnolane.otus.model.ObjectForMessage;
import tomnolane.otus.processor.homework.DateTimeProvider;

public class HistoryListener implements Listener, HistoryReader {
    private static final Logger logger = LoggerFactory.getLogger(HistoryListener.class);
    private final Map<Long, Message> history = new TreeMap<>(Collections.reverseOrder());
    private final DateTimeProvider dateTimeProvider;

    public HistoryListener(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public void onUpdated(Message msg) {
        final Message copiedMsg = copy(msg);
        history.put(msg.getId(), copiedMsg);
        printHistory();
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(history.get(id));
    }

    private static Message copy(Message msg) {
        final ObjectForMessage newOfm = new ObjectForMessage();
        newOfm.setData(List.copyOf(msg.getField13().getData()));

        return msg.toBuilder().field13(newOfm).build();
    }

    private void printHistory() {
        for (Map.Entry<Long, Message> entry : history.entrySet()) {
            logger.info("History {}: {}", entry.getKey(), entry.getValue());
        }
    }
}
