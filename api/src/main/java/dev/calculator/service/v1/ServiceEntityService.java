package dev.calculator.service.v1;

import dev.calculator.model.Service;
import dev.calculator.model.ServiceEntry;
import dev.calculator.model.ServiceType;
import dev.calculator.model.Status;
import dev.calculator.model.v1.network.*;
import dev.calculator.repository.ServiceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServiceEntityService {

    private final ServiceRepository serviceRepository;

    public ServiceEntityService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public ModifyServiceResponse modifyService(ModifyServiceRequest request) {
        Service service;

        long serviceId = request.getId();
        boolean shouldUpdateResource = serviceId > 0;

        if (shouldUpdateResource) {
            service = serviceRepository.findById(serviceId).orElseThrow();
            service.setUuid(UUID.fromString(request.getUuid()));
            service.setType(ServiceType.valueOf(request.getType()));
            service.setCost(request.getCost());
            service.setStatus(Status.valueOf(request.getStatus()));
        } else {
            service = Service.builder()
                    .uuid(UUID.randomUUID())
                    .type(ServiceType.valueOf(request.getType()))
                    .cost(request.getCost())
                    .status(Status.valueOf(request.getStatus()))
                    .build();
        }

        service = serviceRepository.save(service);
        return ModifyServiceResponse.fromService(service);
    }

    public ServiceEntry createServiceEntry(ServiceRequest serviceRequest) {
        return ServiceEntry.builder()
                .serviceType(ServiceType.valueOf(serviceRequest.getServiceType()))
                .numbers(serviceRequest.getNumbers())
                .number(serviceRequest.getNumber())
                .freeForm(serviceRequest.getFreeForm())
                .build();
    }

    public ServiceResponse createServiceResponse(ServiceEntry serviceEntry, Object result) {
        double resultValue = result instanceof Double ? ((Double) result) : 0.0;

        return ServiceResponse.builder()
                .result(resultValue)
                .serviceType(serviceEntry.getServiceType())
                .resultString(result.toString())
                .build();
    }

    public PageResponse getServices(String serviceType, PageRequest pageRequest) {

        List<ServiceType> possibleServiceTypes = ServiceType.possibleFromName(serviceType);

        Page<Service> services;
        if (ObjectUtils.isEmpty(serviceType)) {
            services = serviceRepository.findAll(pageRequest);
        } else {
            services = serviceRepository.findByTypeIsIn(possibleServiceTypes, pageRequest);
        }

        List<Object> result = services.get()
                .map(ServiceEntityResponse::fromService)
                .collect(Collectors.toList());

        return PageResponse.builder()
                .totalPages(services.getTotalPages())
                .pageSize(result.size())
                .currentPage(pageRequest.getPageNumber())
                .result(result)
                .build();
    }

    public void deleteService(long serviceId) {
        serviceRepository.deleteById(serviceId);
    }

}
