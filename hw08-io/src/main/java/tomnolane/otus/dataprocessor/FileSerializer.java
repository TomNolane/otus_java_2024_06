package tomnolane.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileSerializer implements Serializer {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(FileSerializer.class);
    private final String inputDataFileName;

    public FileSerializer(String fileName) {
        inputDataFileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        // формирует результирующий json и сохраняет его в файл
        ObjectMapper mapper = new ObjectMapper();
        try {
            final File file = new File(inputDataFileName);
            mapper.writeValue(file, data);
            logger.info("Saved to the file:{}", file.getAbsolutePath());
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}