package tomnolane.otus.dataprocessor;

import java.io.IOException;
import java.util.List;
import tomnolane.otus.model.Measurement;

public interface Loader {

    List<Measurement> load() throws IOException;
}
