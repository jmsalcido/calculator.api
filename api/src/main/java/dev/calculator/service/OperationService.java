package dev.calculator.service;

import dev.calculator.model.ServiceEntry;
import dev.calculator.service.operations.Operation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationService {

    private final List<Operation> operations;

    public OperationService(List<Operation> operations) {
        this.operations = operations;
    }

    public Object executeService(ServiceEntry serviceEntry) {
        Operation operation = operations.stream()
                .filter(o -> o.getServiceType() == serviceEntry.getServiceType())
                .findFirst()
                .orElseThrow();

        return operation.execute(serviceEntry);
    }
}
