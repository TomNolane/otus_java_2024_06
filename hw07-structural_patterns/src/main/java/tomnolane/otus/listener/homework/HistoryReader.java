package tomnolane.otus.listener.homework;

import java.util.Optional;
import tomnolane.otus.model.Message;

public interface HistoryReader {

    Optional<Message> findMessageById(long id);
}
