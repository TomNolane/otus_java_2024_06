package tomnolane.otus.dataprocessor;

import java.util.*;
import java.util.stream.Collectors;

import tomnolane.otus.model.Measurement;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        // группирует выходящий список по name, при этом суммирует поля value
        return data.stream()
                .collect(Collectors.groupingBy(Measurement::name,TreeMap::new, Collectors.summingDouble(Measurement::value)));
    }
}
