package dev.calculator.controller.v1.services;

import dev.calculator.model.v1.network.ServiceRequest;
import dev.calculator.model.v1.network.ServiceResponse;
import dev.calculator.service.v1.ApplicationService;
import dev.calculator.service.v1.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/services/")
public class ServiceRestController {

    private final ApplicationService applicationService;
    private final UserService userService;

    public ServiceRestController(ApplicationService applicationService, UserService userService) {
        this.applicationService = applicationService;
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<ServiceResponse> createOperationService(@RequestBody ServiceRequest serviceRequest,
                                                                  Authentication authentication) {
        // TODO validateRequest
        var user = userService.findUserByUsername(authentication.getName());
        var serviceResponse = applicationService.processServiceRequest(user, serviceRequest);

        return ResponseEntity.ok(serviceResponse);
    }


}
