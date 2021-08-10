package dev.calculator.service.operations;

import dev.calculator.model.ServiceEntry;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MultiplicationOperationTest {
    @SuppressWarnings("unused")
    private static Stream<Arguments> testExecute() {
        return Stream.of(
                Arguments.of(List.of(2, 3, 5), 30.0),
                Arguments.of(List.of(1, 1), 1.0),
                Arguments.of(List.of(2, 2), 4.0),
                Arguments.of(List.of(), 0.0)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testExecute(List<Integer> numbers, Number expected) {
        ServiceEntry serviceEntry = ServiceEntry.builder()
                .numbers(numbers)
                .build();

        var subject = new MultiplicationOperation();
        Number result = subject.execute(serviceEntry);

        assertEquals(expected, result);
    }
}