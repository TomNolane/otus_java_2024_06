package tomnolane.otus.processor.homework;

import tomnolane.otus.model.Message;
import tomnolane.otus.processor.Processor;

public class Swap11to12 implements Processor {
    @Override
    public Message process(Message message) {
        return message.toBuilder()
                .field11(message.getField12())
                .field12(message.getField11())
                .build();
    }
}