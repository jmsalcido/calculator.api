package dev.calculator.repository;

import dev.calculator.model.Service;
import dev.calculator.model.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ServiceRepository extends PagingAndSortingRepository<Service, Long> {

    Service findByType(ServiceType type);

    Page<Service> findByTypeIsIn(List<ServiceType> serviceTypes, Pageable pageable);

}
