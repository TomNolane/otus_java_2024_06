package tomnolane.otus.lib;

import tomnolane.otus.api.model.SensorData;

import java.util.List;

public interface SensorDataBufferedWriter {
    void writeBufferedData(List<SensorData> bufferedData);
}
