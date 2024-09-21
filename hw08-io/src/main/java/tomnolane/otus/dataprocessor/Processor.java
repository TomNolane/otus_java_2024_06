package tomnolane.otus.dataprocessor;

import java.util.List;
import java.util.Map;
import tomnolane.otus.model.Measurement;

public interface Processor {

    Map<String, Double> process(List<Measurement> data);
}
