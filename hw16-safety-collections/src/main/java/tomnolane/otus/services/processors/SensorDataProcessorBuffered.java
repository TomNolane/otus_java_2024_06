package tomnolane.otus.services.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tomnolane.otus.api.SensorDataProcessor;
import tomnolane.otus.api.model.SensorData;
import tomnolane.otus.lib.SensorDataBufferedWriter;

// Этот класс нужно реализовать
@SuppressWarnings({"java:S1068", "java:S125"})
public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);

    private final int bufferSize;
    private final SensorDataBufferedWriter writer;

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
    }

    @Override
    public void process(SensorData data) {
        /*
            if (dataBuffer.size() >= bufferSize) {
                flush();
            }
        */
    }

    public void flush() {
        try {
            // writer.writeBufferedData(bufferedData);
        } catch (Exception e) {
            log.error("Ошибка в процессе записи буфера", e);
        }
    }

    @Override
    public void onProcessingEnd() {
        flush();
    }
}
