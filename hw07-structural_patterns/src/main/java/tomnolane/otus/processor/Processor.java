package tomnolane.otus.processor;

import tomnolane.otus.model.Message;

@SuppressWarnings("java:S1135")
public interface Processor {
    Message process(Message message);
}
