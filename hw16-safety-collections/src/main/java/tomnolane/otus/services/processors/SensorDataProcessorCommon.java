package tomnolane.otus.services.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tomnolane.otus.api.SensorDataProcessor;
import tomnolane.otus.api.model.SensorData;

public class SensorDataProcessorCommon implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorCommon.class);

    @Override
    public void process(SensorData data) {
        if (data.getValue() == null || data.getValue().isNaN()) {
            return;
        }
        log.info("Обработка данных: {}", data);
    }
}
