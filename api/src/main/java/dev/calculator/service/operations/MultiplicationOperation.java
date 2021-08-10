package dev.calculator.service.operations;

import dev.calculator.model.ServiceEntry;
import dev.calculator.model.ServiceType;
import org.springframework.stereotype.Component;

@Component
public class MultiplicationOperation implements Operation {

    @Override
    public Double execute(ServiceEntry serviceEntry) {
        return serviceEntry.getNumbers().stream()
                .map(Number::doubleValue)
                .reduce((num1, num2) -> num1 * num2)
                .orElse(0.0);
    }

    @Override
    public ServiceType getServiceType() {
        return ServiceType.MULTIPLICATION;
    }
}
