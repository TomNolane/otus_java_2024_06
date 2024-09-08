package tomnolane.otus.dataprocessor;

import java.util.*;
import java.util.stream.Collectors;

import tomnolane.otus.model.Measurement;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        // группирует выходящий список по name, при этом суммирует поля value
        final Map<String, Double> groupedData = data.stream()
                .collect(Collectors.groupingBy(Measurement::name,
                        Collectors.summingDouble(Measurement::value)));

        final Map<String, Double> reversedData = new LinkedHashMap<>();

        final List<String> keysInReverseOrder = new ArrayList<>(groupedData.keySet());
        Collections.reverse(keysInReverseOrder);
        
        for (String key : keysInReverseOrder) {
            reversedData.put(key, groupedData.get(key));
        }

        return reversedData;
    }
}
