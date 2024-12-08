package tomnolane.otus.services.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tomnolane.otus.api.SensorDataProcessor;
import tomnolane.otus.api.model.SensorData;
import tomnolane.otus.lib.SensorDataBufferedWriter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

@SuppressWarnings({"java:S1068", "java:S125"})
public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);

    private final int bufferSize;
    private final SensorDataBufferedWriter writer;

    private final PriorityBlockingQueue<SensorData> dataBuffer;

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
        this.dataBuffer = new PriorityBlockingQueue<>(bufferSize, Comparator.comparing(SensorData::getMeasurementTime));
    }

    @Override
    public void process(SensorData data) {
        if (dataBuffer.size() >= bufferSize) {
            flush();
        }

        dataBuffer.add(data);
    }

    public synchronized void flush() {
        var result = new ArrayList<SensorData>();
        try {
            dataBuffer.drainTo(result);
            if (!result.isEmpty()) {
                writer.writeBufferedData(result);
            }
        } catch (Exception e) {
            log.error("Ошибка в процессе записи буфера", e);
        }
    }

    @Override
    public void onProcessingEnd() {
        flush();
    }
}
