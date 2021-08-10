package dev.calculator.controller.v1.services;

import dev.calculator.model.v1.network.ModifyServiceRequest;
import dev.calculator.model.v1.network.ModifyServiceResponse;
import dev.calculator.model.v1.network.PageResponse;
import dev.calculator.model.v1.network.ServiceEntityResponse;
import dev.calculator.service.v1.ServiceEntityService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/services/")
@Secured({"ROLE_ADMIN"})
public class AdminServiceRestController {

    private final ServiceEntityService serviceEntityService;

    public AdminServiceRestController(ServiceEntityService serviceEntityService) {
        this.serviceEntityService = serviceEntityService;
    }

    @PostMapping("/create")
    public ResponseEntity<ModifyServiceResponse> createServiceRequest(
            @RequestBody ModifyServiceRequest modifyServiceRequest
    ) {
        // TODO validations
        if (modifyServiceRequest.getId() > 0) {
            modifyServiceRequest.setId(0);
        }

        ModifyServiceResponse modifyServiceResponse = serviceEntityService.modifyService(modifyServiceRequest);
        return ResponseEntity.ok(modifyServiceResponse);
    }

    @PutMapping("/update")
    public ResponseEntity<ModifyServiceResponse> modifyServiceRequest(
            @RequestBody ModifyServiceRequest modifyServiceRequest
    ) {
        // TODO validations
        ModifyServiceResponse modifyServiceResponse = serviceEntityService.modifyService(modifyServiceRequest);
        return ResponseEntity.ok(modifyServiceResponse);
    }

    @DeleteMapping("/delete/{serviceId}")
    public ResponseEntity<Long> deleteService(@PathVariable long serviceId) {
        serviceEntityService.deleteService(serviceId);
        return ResponseEntity.ok(serviceId);
    }

    @GetMapping("/")
    public ResponseEntity<PageResponse> getServices(
            @RequestParam(required = false, defaultValue = "") String serviceType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        PageResponse pageResponse = serviceEntityService.getServices(serviceType, pageRequest);

        return ResponseEntity.ok(pageResponse);
    }

}
