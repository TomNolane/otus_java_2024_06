package tomnolane.otus.handler;

import tomnolane.otus.listener.Listener;
import tomnolane.otus.model.Message;

public interface Handler {
    Message handle(Message msg);

    void addListener(Listener listener);

    void removeListener(Listener listener);
}
