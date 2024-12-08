package tomnolane.otus.api;

import tomnolane.otus.api.model.SensorData;

public interface SensorsDataServer {
    void onReceive(SensorData sensorData);
}
