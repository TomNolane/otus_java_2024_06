package tomnolane.otus.api;

import tomnolane.otus.api.model.SensorData;

public interface SensorDataProcessor {
    void process(SensorData data);

    default void onProcessingEnd() {}
}

