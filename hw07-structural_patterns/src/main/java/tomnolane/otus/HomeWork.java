package tomnolane.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tomnolane.otus.handler.ComplexProcessor;
import tomnolane.otus.listener.homework.HistoryListener;
import tomnolane.otus.model.Message;
import tomnolane.otus.model.ObjectForMessage;
import tomnolane.otus.processor.Processor;
import tomnolane.otus.processor.homework.EvenSecondException;
import tomnolane.otus.processor.homework.Swap11to12;
import tomnolane.otus.processor.homework.ProcessorImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class HomeWork {
    private static final Logger logger = LoggerFactory.getLogger(HomeWork.class);

    public static void main(String[] args) {
        final ObjectForMessage obj = new ObjectForMessage();
        obj.setData(List.of("Otus", "Homework", "42"));

        final Message message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field4("field4")
                .field5("field5")
                .field6("field6")
                .field7("field7")
                .field8("field8")
                .field9("field9")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .field13(obj)
                .build();

        final List<Processor> processors = List.of(new Swap11to12(), new ProcessorImpl(LocalDateTime::now));
        final Consumer<Exception> exceptionHandler = exception -> {
            if (exception instanceof EvenSecondException) {
                logger.error("Even second exception was happened: ", exception);
            }
        };

        final var mainProcessor = new ComplexProcessor(processors, exceptionHandler);
        final var historyListener = new HistoryListener(LocalDateTime::now);
        mainProcessor.addListener(historyListener);

        // waitEvenSecond(); // for guarantee throw exception

        Message msg = mainProcessor.handle(message);

        for (int i = 0; i < 5; i++) {
            msg = mainProcessor.handle(msg);
            System.out.println();
        }

        logger.info("Message: {}", msg);
        mainProcessor.removeListener(historyListener);
    }

    private static void waitEvenSecond() {
        final int currentSecond = LocalDateTime.now().getSecond();
        if (currentSecond % 2 == 1) {
            try {
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
