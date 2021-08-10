package dev.calculator.model;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Data
@Builder
public class ServiceEntry {
    ServiceType serviceType;

    @Singular
    List<Number> numbers;
    String freeForm;

    public Number getFirstNumber() {
        return numbers.stream().findFirst().orElse(0.0);
    }
}
