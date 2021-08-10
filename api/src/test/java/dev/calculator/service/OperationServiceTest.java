package dev.calculator.service;

import dev.calculator.model.ServiceEntry;
import dev.calculator.model.ServiceType;
import dev.calculator.service.operations.FreeFormOperation;
import dev.calculator.service.operations.Operation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OperationServiceTest {

    private static class TestOperation implements Operation {

        private final ServiceEntry originalServiceEntry;

        private TestOperation(ServiceEntry originalServiceEntry) {
            this.originalServiceEntry = originalServiceEntry;
        }

        @Override
        public Object execute(ServiceEntry serviceEntry) {
            assertEquals(originalServiceEntry, serviceEntry, "this gets called only");
            return true;
        }

        @Override
        public ServiceType getServiceType() {
            return ServiceType.ADDITION;
        }
    }

    @Test
    public void test__executeCorrectOperation() {
        var serviceEntry = ServiceEntry.builder()
                .serviceType(ServiceType.ADDITION)
                .build();

        List<Operation> operationList = new ArrayList<>();
        operationList.add(new TestOperation(serviceEntry));
        operationList.add(new FreeFormOperation());
        var subject = new OperationService(operationList);

        assertTrue((Boolean) subject.executeService(serviceEntry),
                "the result from the operation should match");
    }

}