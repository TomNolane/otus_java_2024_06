package tomnolane.otus.listener;

import tomnolane.otus.model.Message;

@SuppressWarnings("java:S1135")
public interface Listener {

    void onUpdated(Message msg);
}
