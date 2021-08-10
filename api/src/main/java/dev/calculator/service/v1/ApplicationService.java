package dev.calculator.service.v1;

import dev.calculator.controller.v1.error.ApplicationRuntimeException;
import dev.calculator.model.Service;
import dev.calculator.model.ServiceType;
import dev.calculator.model.User;
import dev.calculator.model.v1.network.ServiceRequest;
import dev.calculator.model.v1.network.ServiceResponse;
import dev.calculator.repository.ServiceRepository;
import dev.calculator.service.OperationService;
import org.springframework.http.HttpStatus;

@org.springframework.stereotype.Service
public class ApplicationService {

    private final OperationService operationService;
    private final ServiceEntityService serviceEntityService;
    private final UserService userService;
    private final RecordService recordService;

    private final ServiceRepository serviceRepository;

    public ApplicationService(OperationService operationService,
                              ServiceEntityService serviceEntityService,
                              UserService userService,
                              RecordService recordService,
                              ServiceRepository serviceRepository) {
        this.operationService = operationService;
        this.serviceEntityService = serviceEntityService;
        this.userService = userService;
        this.recordService = recordService;
        this.serviceRepository = serviceRepository;
    }

    // TODO test this method with unit tests to verify the scenario.
    public ServiceResponse processServiceRequest(User user, ServiceRequest serviceRequest) {

        ServiceType serviceType = ServiceType.valueOf(serviceRequest.getServiceType());

        Service service = serviceRepository.findByType(serviceType);
        if (service == null) {
            throw new ApplicationRuntimeException("Service not available for execution.");
        }

        boolean canExecuteService = userService.canExecuteService(user, service);

        if (!canExecuteService) {
            throw new ApplicationRuntimeException("User balance is not enough to execute this service",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }

        var serviceEntry = serviceEntityService.createServiceEntry(serviceRequest);
        var result = operationService.executeService(serviceEntry);
        var serviceResponse = serviceEntityService.createServiceResponse(serviceEntry, result);

        recordService.createRecord(user, service, serviceResponse.getResultString());

        // TODO TRANSACTIONAL.
        userService.removeBalance(user, service.getCost());

        return serviceResponse;
    }

}
