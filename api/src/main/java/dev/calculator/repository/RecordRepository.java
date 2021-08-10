package dev.calculator.repository;

import dev.calculator.model.Record;
import dev.calculator.model.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RecordRepository extends PagingAndSortingRepository<Record, Long> {

    Page<Record> findByServiceTypeIn(List<ServiceType> serviceTypes, Pageable pageRequest);

    Page<Record> findByUserIdAndServiceTypeIn(long userId, List<ServiceType> serviceTypes, Pageable pageRequest);

    Page<Record> findByUserId(long userId, Pageable pageRequest);

}
