package tomnolane.otus.processor.homework;

import tomnolane.otus.model.Message;
import tomnolane.otus.processor.Processor;

public class ProcessorImpl implements Processor {
    private final DateTimeProvider timeProvider;

    public ProcessorImpl(DateTimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

    @Override
    public Message process(Message message) {
        if (timeProvider.getDateTime().getSecond() % 2 == 0) {
            throw new EvenSecondException("Even second throw exception: " + timeProvider.getDateTime());
        }

        return message;
    }
}
