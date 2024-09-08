import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import tomnolane.otus.model.Message;
import tomnolane.otus.processor.homework.DateTimeProvider;
import tomnolane.otus.processor.homework.EvenSecondException;
import tomnolane.otus.processor.homework.ProcessorImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.*;

public class ExceptionEvenSecondTest {
    private final DateTimeProvider timeProviderMock = Mockito.mock(DateTimeProvider.class);
    private final ProcessorImpl processor = new ProcessorImpl(timeProviderMock);
    private final Message emptyMsg = new Message.Builder(1L).build();
    private final LocalDate date = LocalDate.MIN;

    @Test
    void correctExceptionThrowingTest() {
        Mockito.when(timeProviderMock.getDateTime())
            .thenReturn(LocalDateTime.of(date, LocalTime.of(12, 35, 12)));

        assertThatThrownBy(() -> processor.process(emptyMsg)).isInstanceOf(EvenSecondException.class);
    }

    @MethodSource("evenSeconds")
    @ParameterizedTest(name = "Exception on {0} second")
    void processorThrowExceptionOnEvenSecond(int second) {
        Mockito.when(timeProviderMock.getDateTime()).thenReturn(LocalDateTime.of(date, LocalTime.of(1, 1, second)));

        assertThatThrownBy(() -> processor.process(emptyMsg)).isInstanceOf(EvenSecondException.class);
    }

    @MethodSource("oddSeconds")
    @ParameterizedTest(name = "Normal work on {0} second")
    void processorNotThrowExceptionOnOddSecond(int second) {
        Mockito.when(timeProviderMock.getDateTime()).thenReturn(LocalDateTime.of(date, LocalTime.of(1, 1, second)));

        assertThatNoException().isThrownBy(() -> processor.process(emptyMsg));
    }

    private static IntStream evenSeconds() {
        return IntStream.range(0, 60).filter(i -> i % 2 == 0);
    }

    private static IntStream oddSeconds() {
        return IntStream.range(0, 60).filter(i -> i % 2 == 1);
    }
}
