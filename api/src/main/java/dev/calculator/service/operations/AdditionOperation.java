package dev.calculator.service.operations;

import dev.calculator.model.ServiceEntry;
import dev.calculator.model.ServiceType;
import org.springframework.stereotype.Component;

@Component
public class AdditionOperation implements Operation {

    @Override
    public Double execute(ServiceEntry serviceEntry) {
        return serviceEntry.getNumbers().stream()
                .map(Number::doubleValue)
                .reduce(0.0, Double::sum);
    }

    @Override
    public ServiceType getServiceType() {
        return ServiceType.ADDITION;
    }
}
