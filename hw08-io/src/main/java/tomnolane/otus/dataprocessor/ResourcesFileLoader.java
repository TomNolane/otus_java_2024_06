package tomnolane.otus.dataprocessor;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tomnolane.otus.model.Measurement;

public class ResourcesFileLoader implements Loader {
    private final String outputDataFileName;
    private static final Logger logger = LoggerFactory.getLogger(ResourcesFileLoader.class);
    private final ObjectMapper mapper;

    public ResourcesFileLoader(String fileName) {
        outputDataFileName = fileName;
        mapper = new ObjectMapper();
    }

    @Override
    public List<Measurement> load() {
        // читает файл, парсит и возвращает результат
        final File file = new File(
                Objects.requireNonNull(this.getClass().getClassLoader().getResource(outputDataFileName)).getFile());

        List<Measurement> measurement;
        try {
            measurement = mapper.readValue(file, new TypeReference<>() {});
        } catch (IOException e) {
            throw new FileProcessException("File: " + file.getName()  + ", error: " + e.getMessage());
        }

        logger.info("Loaded from the file:{}, measurement:{}", file.getAbsolutePath(), measurement);

        return measurement;
    }
}
