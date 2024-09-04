import org.junit.jupiter.api.Test;
import tomnolane.otus.model.Message;
import tomnolane.otus.processor.homework.Swap11to12;

import static org.assertj.core.api.AssertionsForClassTypes.*;

public class Swap11to12Test {
    @Test
    void swapTest() {
        final Swap11to12 processorSwap11to12 = new Swap11to12();
        final Message message = new Message.Builder(1L).field11("field11").field12("field12").build();
        final Message expected = new Message.Builder(1L).field11("field12").field12("field11").build();
        final Message processed = processorSwap11to12.process(message);

        assertThat(processed).usingRecursiveComparison().isEqualTo(expected);
    }
}
