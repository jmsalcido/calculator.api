package dev.calculator.service.v1;


import dev.calculator.controller.v1.error.ApplicationRuntimeException;
import dev.calculator.model.Record;
import dev.calculator.model.Service;
import dev.calculator.model.ServiceType;
import dev.calculator.model.User;
import dev.calculator.model.v1.network.ModifyRecordRequest;
import dev.calculator.model.v1.network.PageResponse;
import dev.calculator.model.v1.network.RecordResponse;
import dev.calculator.repository.RecordRepository;
import dev.calculator.repository.ServiceRepository;
import dev.calculator.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class RecordService {

    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;
    private final RecordRepository recordRepository;

    public RecordService(UserRepository userRepository,
                         ServiceRepository serviceRepository,
                         RecordRepository recordRepository)
    {
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;
        this.recordRepository = recordRepository;
    }

    public PageResponse findByServiceTypeContaining(String serviceType, PageRequest pageRequest) {
        Page<Record> records;

        if (serviceType != null) {
            List<ServiceType> possibleServiceTypes = ServiceType.possibleFromName(serviceType);
            records = recordRepository.findByServiceTypeIn(possibleServiceTypes, pageRequest);
        } else {
            records = recordRepository.findAll(pageRequest);
        }

        List<Object> result = records.get()
                .map(RecordResponse::fromRecord)
                .collect(Collectors.toList());

        return PageResponse.builder()
                .totalPages(records.getTotalPages())
                .pageSize(records.getPageable().getPageSize())
                .currentPage(pageRequest.getPageNumber())
                .result(result)
                .build();
    }

    public PageResponse findForUser(String username, String serviceType, PageRequest pageRequest) {
        Page<Record> records;

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ApplicationRuntimeException("User not found", HttpStatus.BAD_REQUEST);
        }

        if (serviceType != null) {
            List<ServiceType> possibleServiceTypes = ServiceType.possibleFromName(serviceType);
            records = recordRepository.findByUserIdAndServiceTypeIn(user.getId(), possibleServiceTypes, pageRequest);
        } else {
            records = recordRepository.findByUserId(user.getId(), pageRequest);
        }

        List<Object> result = records.get()
                .map(RecordResponse::fromRecord)
                .collect(Collectors.toList());

        return PageResponse.builder()
                .totalPages(records.getTotalPages())
                .pageSize(result.size())
                .currentPage(pageRequest.getPageNumber())
                .result(result)
                .build();
    }

    public RecordResponse modifyRecord(ModifyRecordRequest request) {
        Record record;
        if (request.getId() > 0) {
            record = recordRepository.findById(request.getId()).orElseThrow();
            record.setUuid(UUID.fromString(request.getUuid()));

            if (record.getService().getId() != request.getServiceId()) {
                Service service = serviceRepository.findById(request.getServiceId()).orElseThrow();
                record.setService(service);
            }

            if (record.getUser().getId() != request.getUserId()) {
                User user = userRepository.findById(request.getUserId()).orElseThrow();
                record.setUser(user);
            }

            record.setCost(request.getCost());
            record.setUserBalance(request.getUserBalance());
            record.setServiceResponse(request.getServiceResponse());

            if (request.getDate() != null) {
                record.setDate(request.getDate());
            }
        } else {
            record = Record.builder()
                    .uuid(UUID.randomUUID())
                    .service(serviceRepository.findById(request.getServiceId()).orElseThrow())
                    .user(userRepository.findById(request.getUserId()).orElseThrow())
                    .cost(request.getCost())
                    .userBalance(request.getUserBalance())
                    .serviceResponse(request.getServiceResponse())
                    .date(LocalDateTime.now())
                    .build();
        }

        record = recordRepository.save(record);

        return RecordResponse.fromRecord(record);
    }

    public Record createRecord(User user, Service service, String result) {
        Record record = Record.builder()
                .uuid(UUID.randomUUID())
                .service(service)
                .serviceResponse(result)
                .user(user)
                .userBalance(user.getUserBalance())
                .cost(service.getCost())
                .date(LocalDateTime.now())
                .build();

        return recordRepository.save(record);
    }

    public void deleteRecord(long recordId) {
        recordRepository.deleteById(recordId);
    }

}
