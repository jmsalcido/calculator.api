package dev.calculator.service.operations;

import dev.calculator.model.ServiceEntry;
import dev.calculator.model.ServiceType;
import org.springframework.stereotype.Component;

@Component
public class SquareRootOperation implements Operation {

    @Override
    public Double execute(ServiceEntry serviceEntry) {
        return Math.sqrt(serviceEntry.getFirstNumber().doubleValue());
    }

    @Override
    public ServiceType getServiceType() {
        return ServiceType.SQUARE_ROOT;
    }
}
