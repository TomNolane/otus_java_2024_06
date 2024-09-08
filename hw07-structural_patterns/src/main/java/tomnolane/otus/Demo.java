package tomnolane.otus;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tomnolane.otus.handler.ComplexProcessor;
import tomnolane.otus.listener.ListenerPrinterConsole;
import tomnolane.otus.model.Message;
import tomnolane.otus.processor.LoggerProcessor;
import tomnolane.otus.processor.ProcessorConcatFields;
import tomnolane.otus.processor.ProcessorUpperField10;

public class Demo {
    private static final Logger logger = LoggerFactory.getLogger(Demo.class);

    public static void main(String[] args) {
        var processors = List.of(new ProcessorConcatFields(), new LoggerProcessor(new ProcessorUpperField10()));

        var complexProcessor = new ComplexProcessor(processors, ex -> {});
        var listenerPrinter = new ListenerPrinterConsole();
        complexProcessor.addListener(listenerPrinter);

        var message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .build();

        var result = complexProcessor.handle(message);
        logger.info("result:{}", result);

        complexProcessor.removeListener(listenerPrinter);
    }
}