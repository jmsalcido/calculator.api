package dev.calculator.service.operations;

import dev.calculator.model.ServiceEntry;
import dev.calculator.model.ServiceType;

public interface Operation {

    Object execute(ServiceEntry serviceEntry);

    ServiceType getServiceType();

}
