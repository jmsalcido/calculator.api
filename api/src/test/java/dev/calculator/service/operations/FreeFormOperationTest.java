package dev.calculator.service.operations;

import dev.calculator.model.ServiceEntry;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FreeFormOperationTest {
    @SuppressWarnings("unused")
    private static Stream<Arguments> testExecute() {
        return Stream.of(
                Arguments.of("5 + 5", 10.0),
                Arguments.of("5 * 5", 25.0),
                Arguments.of("", 0.0)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testExecute(String freeForm, Number expected) {
        ServiceEntry serviceEntry = ServiceEntry.builder()
                .freeForm(freeForm)
                .build();

        var subject = new FreeFormOperation();
        Number result = subject.execute(serviceEntry);

        assertEquals(expected, result);
    }
}